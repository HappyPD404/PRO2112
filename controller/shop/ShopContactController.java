package com.example.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "beauty/contact")
public class ShopContactController {

	@GetMapping(value = "")
	public String contact() {
		return "shop/contact/contact";
	}
}
