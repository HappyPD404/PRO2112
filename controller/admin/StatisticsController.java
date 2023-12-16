package com.example.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.LikeService;
import com.example.service.OrderDetailService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "statistics")
public class StatisticsController {

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	LikeService likeService;

	@GetMapping(value = "/revenue")
	public String revenue(Model model) {
		model.addAttribute("years", orderService.getYearOrder());
		return "admin/statistics/revenue-statistics";
	}

	@GetMapping(value = "/like")
	public String like(Model model) {
		model.addAttribute("years", orderService.getYearOrder());
		return "admin/statistics/like-statistics";
	}

	@GetMapping(value = "/view")
	public String view(Model model) {
		model.addAttribute("years", orderService.getYearOrder());
		return "admin/statistics/view-statistics";
	}

	@GetMapping(value = "/revenue/revenue-year/{year}")
	public ResponseEntity<List<Object[]>> revenueStatisticsYear(@PathVariable(name = "year") int year) {
		List<Object[]> list = orderDetailService.statisticsRevenueMonthByYear(year);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}

	@GetMapping(value = "/revenue/revenue-category-year/{year}")
	public ResponseEntity<List<Object[]>> revenueCategoryStatisticsYear(@PathVariable(name = "year") int year) {
		List<Object[]> list = orderService.statisticsRevenueCategoryByYear(year);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/like/like-year/{year}")
	public ResponseEntity<List<Object[]>> likeStatisticsYear(@PathVariable(name = "year") int year) {
		List<Object[]> list = likeService.statisticsLikeMonthByYear(year);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/like/like-category")
	public ResponseEntity<List<Object[]>> likeCategoryStatistics() {
		List<Object[]> list = likeService.statisticsLikeCategory();
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/view/view-year/{year}")
	public ResponseEntity<List<Object[]>> viewStatisticsYear(@PathVariable(name = "year") int year) {
		List<Object[]> list = userService.statisticsViewMonthByYear(year);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/view/view-category")
	public ResponseEntity<List<Object[]>> viewCategoryStatistics() {
		List<Object[]> list = productService.statisticsViewCategory();
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
}
