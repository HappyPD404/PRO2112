package com.example.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.common.Role;
import com.example.dto.UserDto;
import com.example.exception.StorageFileNotFoundException;
import com.example.model.User;
import com.example.service.SessionService;
import com.example.service.StorageService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	StorageService storageService;

	@Autowired
	SessionService session;

	@GetMapping(value = "")
	public String list(Model model, @RequestParam(name = "field") Optional<String> field,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "keywords", defaultValue = "") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords"));
		session.set("keywords", keyword);
		Sort sort = Sort.by(Direction.DESC, field.orElse("id"));
		Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
		Page<User> resultPage = userService.findAllByUsernameLike("%" + keyword + "%", pageable);
		int totalPages = resultPage.getTotalPages();
		int startPage = Math.max(1, page.orElse(1) - 2);
		int endPage = Math.min(page.orElse(1) + 2, totalPages);
		if (totalPages > 5) {
			if (endPage == totalPages)
				startPage = endPage - 5;
			else if (startPage == 1)
				endPage = startPage + 5;
		}
		List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
		model.addAttribute("pageNumbers", pageNumbers);
		model.addAttribute("field", field.orElse("id"));
		model.addAttribute("size", size.orElse(5));
		model.addAttribute("keywords", keyword);
		model.addAttribute("resultPage", resultPage);
		return "admin/users/user-list";
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteApi(@PathVariable(name = "id") String id) throws IOException {
		Optional<User> user = userService.findById(id);
		if (user.get().getAvatar() != null)
			storageService.delete(user.get().getAvatar());
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value = "/view/{id}")
	public ResponseEntity<User> viewApi(@PathVariable(name = "id") String id) {
		Optional<User> user = userService.findById(id);
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}

	@GetMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(Model model, @ModelAttribute("userDto") UserDto userDto) {
		return "admin/users/user-form";
	}

	@GetMapping(value = "/edit")
	public String saveOrUpdateId(Model model, @RequestParam(value = "id") String id) {
		User user = userService.findById(id).get();
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setUsername(user.getUsername());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setAddress(user.getAddress());
		userDto.setBirthDay(user.getBirthDay());
		userDto.setGender(user.getGender());
		userDto.setLogin(user.getLogin());
		Boolean role = true;
		if (user.getRole() == Role.ROLE_USER)
			role = false;
		userDto.setRole(role);
		model.addAttribute("userDto", userDto);
		return "admin/users/user-form";
	}

	@PostMapping(value = "/saveOrUpdate/submit")
	public String saveOrUpdate(Model model, @Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result,
			@RequestParam(name = "file") MultipartFile file) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "admin/users/user-form";
		}
		User user;
		if (userDto.getId() != null || !userDto.getId().equals("")) {
			user = userService.findById(userDto.getId()).get();
			if (!file.isEmpty()) {
				if(user.getAvatar() != null) {
					storageService.delete(user.getAvatar());
				}
			}
		} else {
			if (userService.existsByUsername(userDto.getUsername())) {
				model.addAttribute("error", "Tên đăng nhập đã được sử dụng");
				return "admin/users/user-form";
			}
			if (userService.existsByEmail(userDto.getEmail())) {
				model.addAttribute("error", "Email đã được sử dụng");
				return "admin/users/user-form";
			}
			user = new User();
			user.setId(userDto.getId());
			user.setPassword(userDto.getUsername());
		}
		user.setUsername(userDto.getUsername());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setAddress(userDto.getAddress());
		user.setBirthDay(userDto.getBirthDay());
		user.setGender(userDto.getGender());
		user.setLogin(userDto.getLogin());
		Role role = Role.ROLE_USER;
		if (userDto.getRole())
			role = Role.ROLE_ADMIN;
		user.setRole(role);
		if (!file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			user.setAvatar(storageService.getStorageFilename(file, uuidString));
			storageService.store(file, user.getAvatar());
		}
		userService.saveOrUpdate(user);
		return "redirect:/users";
	}

	@GetMapping(value = "/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
