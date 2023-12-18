package com.example.controller.shop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.Role;
import com.example.component.GoogleUtils;
import com.example.dto.GoogleDto;
import com.example.dto.Login;
import com.example.dto.Register;
import com.example.model.User;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "beauty/auth")
public class ShopAuthController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@Autowired
	GoogleUtils googleUtils;

	@PostMapping(value = "/login")
	@ResponseBody
	public ResponseEntity<String> loginSubmit(@RequestBody Login login) {
		if (!userService.existsByUsername(login.getUsername()))
			return new ResponseEntity<String>("Tên đăng nhập không tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
		securityService.autoLogin(login.getUsername(), login.getPassword());
		return new ResponseEntity<String>("Chúc mừng bạn đã đăng nhập thành công.", HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	@ResponseBody
	public ResponseEntity<String> registerSubmit(@RequestBody Register register) {
		if (userService.existsByUsername(register.getUsername()))
			return new ResponseEntity<String>("Tên đăng nhập đã được sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
		if (userService.existsByEmail(register.getEmail()))
			return new ResponseEntity<String>("Email đã được sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
		User user = new User();
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(register.getPassword());
		user.setLogin(true);
		user.setRole(Role.ROLE_USER);
		userService.saveOrUpdate(user);
		return new ResponseEntity<String>("Chúc mừng bạn đã đăng ký thành công.", HttpStatus.OK);
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		securityService.logout(request, response);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping("/login-google")
	public String loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException{
		String code = request.getParameter("code");
		if (code == null || code.isEmpty()) {
			return "redirect:/beauty/home?google=error";
		}
		String accessToken = googleUtils.getToken(code);
		GoogleDto google = googleUtils.getUserInfo(accessToken);
		if (!userService.existsByEmail(google.getEmail())) {
			User user = new User();
			user.setUsername(google.getId());
			user.setEmail(google.getEmail());
			user.setPassword(google.getId());
			user.setLogin(true);
			user.setRole(Role.ROLE_USER);
			userService.saveOrUpdate(user);
		}
		securityService.autoLogin(google.getId(), google.getId());
		return "redirect:/beauty/home";
	}
}
