package com.example.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Product;
import com.example.service.ProductService;

@Controller
@RequestMapping(value = "beauty/home")
public class ShopHomeController {
	
	@Autowired
	ProductService productService;

	@GetMapping(value = "")
	public String home(Model model) {
		List<Product> listLapTop = productService.findAllByCategoryId(Long.valueOf(12));
		model.addAttribute("listLapTop", listLapTop);
		
		List<Product> listCook = productService.findAllByCategoryId(Long.valueOf(8));
		model.addAttribute("listCook", listCook);
		return "shop/home/index";
	}
}
