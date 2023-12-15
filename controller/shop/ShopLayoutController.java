package com.example.controller.shop;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dto.Item;
import com.example.model.Category;

import com.example.service.CartService;
import com.example.service.CategoryService;

@Controller
@RequestMapping(value = "beauty/layout")
public class ShopLayoutController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CartService cart;

	@GetMapping(value = "/category")
	public ResponseEntity<List<Category>> categoryApi() {
		List<Category> list = categoryService.findAll();
		return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/get-count-cart")
	public ResponseEntity<Integer> getCountApi() {
		return new ResponseEntity<Integer>(cart.getCount(), HttpStatus.OK);
	}

	@GetMapping(value = "/get-cart-items")
	public ResponseEntity<Collection<Item>> getItemApi() {
		return new ResponseEntity<Collection<Item>>(cart.getItems(), HttpStatus.OK);
	}
}
