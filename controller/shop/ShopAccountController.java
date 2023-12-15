package com.example.controller.shop;

import java.io.IOException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.OrderAddressDto;
import com.example.dto.UserDto;
import com.example.model.OrderAddress;
import com.example.model.User;
import com.example.service.OrderAddressService;
import com.example.service.SecurityService;
import com.example.service.StorageService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "beauty/account")
public class ShopAccountController {

	@Autowired
	SecurityService securityService;

	@Autowired
	OrderAddressService orderAddressService;

	@Autowired
	UserService userService;
	
	@Autowired
	StorageService storageService;

	@GetMapping(value = "/info-account")
	public String infoAccount(Model model) {
		OrderAddressDto orderAddressDto = new OrderAddressDto();
		if (securityService.isAuthenticated()) {
			String username = securityService.username();
			User user = userService.findByUsername(username).get();
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setUsername(user.getUsername());
			userDto.setFirstName(user.getFirstName());
			userDto.setLastName(user.getLastName());
			userDto.setEmail(user.getEmail());
			userDto.setAddress(user.getAddress());
			userDto.setBirthDay(user.getBirthDay());
			userDto.setGender(user.getGender());
			userDto.setLogin(user.getLogin());
			model.addAttribute("userDto", userDto);
			if (orderAddressService.existsByEmail(user.getEmail())) {
				OrderAddress orderAddress = orderAddressService.findByEmail(user.getEmail()).get();
				model.addAttribute("orderAddress", orderAddress);
				orderAddressDto.setId(orderAddress.getId());
				orderAddressDto.setFirstName(orderAddress.getFirstName());
				orderAddressDto.setLastName(orderAddress.getLastName());
				orderAddressDto.setAddress(orderAddress.getAddress());
				orderAddressDto.setPhone(orderAddress.getPhone());
				orderAddressDto.setEmail(orderAddress.getEmail());
			} else {
				model.addAttribute("orderAddress", null);
				orderAddressDto.setFirstName(user.getFirstName());
				orderAddressDto.setLastName(user.getLastName());
				orderAddressDto.setAddress(user.getAddress());
				orderAddressDto.setEmail(user.getEmail());
			}
		}
		model.addAttribute("orderAddressDto", orderAddressDto);
		return "shop/account/info-account";
	}

	@PostMapping(value = "/info-account/order-address")
	public String orderAddressSubmit(Model model,
			@Valid @ModelAttribute("orderAddressDto") OrderAddressDto orderAddressDto, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "shop/account/info-account";
		}
		OrderAddress orderAddress = new OrderAddress();
		orderAddress.setId(orderAddressDto.getId());
		orderAddress.setFirstName(orderAddressDto.getFirstName());
		orderAddress.setLastName(orderAddressDto.getLastName());
		orderAddress.setAddress(orderAddressDto.getAddress());
		orderAddress.setPhone(orderAddressDto.getPhone());
		orderAddress.setEmail(orderAddressDto.getEmail());
		orderAddressService.saveOrUpdate(orderAddress);
		return "redirect:/beauty/account/info-account";
	}
	
	@PostMapping(value = "/info-account/change-info")
	public String changeInfoSubmit(Model model,
			@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result,
			@RequestParam(name = "file") MultipartFile file) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "shop/account/info-account";
		}
		User user = userService.findById(userDto.getId()).get();
		user.setUsername(userDto.getUsername());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setAddress(userDto.getAddress());
		user.setBirthDay(userDto.getBirthDay());
		user.setGender(userDto.getGender());
		if (!file.isEmpty()) {
			if(user.getAvatar() != null) {
				storageService.delete(user.getAvatar());
			}
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			user.setAvatar(storageService.getStorageFilename(file, uuidString));
			storageService.store(file, user.getAvatar());
		}
		userService.saveOrUpdate(user);
		model.addAttribute("message", "Đã cập nhật thông tin tài khoản thành công.");
		return "redirect:/beauty/account/info-account";
	}
}
