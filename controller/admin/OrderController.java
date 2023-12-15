package com.example.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.UpdateOrder;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.service.OrderDetailService;
import com.example.service.OrderService;
import com.example.service.SessionService;

@Controller
@RequestMapping(value = "orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	SessionService session;

	@GetMapping(value = "")
	public String list(Model model, 
			@RequestParam(name = "field") Optional<String> field,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "keywords", defaultValue = "") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords"));
		session.set("keywords", keyword);
		Sort sort = Sort.by(Direction.DESC, field.orElse("id"));
		Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
		Page<Order> resultPage = orderService.findAllByOrderAddressEmailLike("%" + keyword + "%", pageable);
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
		return "admin/orders/order-list";
	}
	
	@GetMapping(value = "/view/{id}")
	public ResponseEntity<Order> viewByOrderId(@PathVariable(name = "id") Long id) {
		Order order = orderService.findById(id).get();
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
	
	@GetMapping(value = "/view-order-detail/{id}")
	public ResponseEntity<List<OrderDetail>> viewOrderDetailByOrderId(@PathVariable(name = "id") Long id) {
		List<OrderDetail> list = orderDetailService.findAllByOrderId(id);
		return new ResponseEntity<List<OrderDetail>>(list, HttpStatus.OK);
	}
	
	@PutMapping(value = "/update")
	@ResponseBody
	public ResponseEntity<?> updateApi(@RequestBody UpdateOrder item) {
		Order order = orderService.findById(item.getId()).get();
		order.setStatus(item.getStatus());
		order.setPay(item.getPay());
		orderService.saveOrUpdate(order);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
