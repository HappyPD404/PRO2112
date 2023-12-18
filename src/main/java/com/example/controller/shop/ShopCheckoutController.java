package com.example.controller.shop;

import java.io.IOException;
import java.util.Collection;

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

import com.example.common.Pay;
import com.example.common.Ship;
import com.example.common.Status;
import com.example.dto.Item;
import com.example.dto.OrderAddressDto;
import com.example.model.Order;
import com.example.model.OrderAddress;
import com.example.model.OrderDetail;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.OrderAddressService;
import com.example.service.OrderDetailService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.SecurityService;
import com.example.service.SessionService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "beauty/check-out")
public class ShopCheckoutController {

	@Autowired
	CartService cartService;

	@Autowired
	SessionService session;

	@Autowired
	SecurityService securityService;

	@Autowired
	UserService userService;

	@Autowired
	OrderAddressService orderAddressService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	ProductService productService;

	@GetMapping(value = "")
	public String checkOut(Model model) {
		Collection<Item> items = cartService.getItems();
		model.addAttribute("carts", items);

		float totail = cartService.getTotail();
		if (session.get("shipping") != null) {
			model.addAttribute("shipping", session.get("shipping").toString());
			if (session.get("shipping").toString().equals("1"))
				totail = totail + 10000;
			else if (session.get("shipping").toString().equals("2"))
				totail = totail + 20000;
		}
		model.addAttribute("totalCart", totail);

		OrderAddressDto orderAddressDto = new OrderAddressDto();
		if (securityService.isAuthenticated()) {
			String username = securityService.username();
			User user = userService.findByUsername(username).get();
			if (orderAddressService.existsByEmail(user.getEmail())) {
				OrderAddress orderAddress = orderAddressService.findByEmail(user.getEmail()).get();
				orderAddressDto.setId(orderAddress.getId());
				orderAddressDto.setFirstName(orderAddress.getFirstName());
				orderAddressDto.setLastName(orderAddress.getLastName());
				orderAddressDto.setAddress(orderAddress.getAddress());
				orderAddressDto.setPhone(orderAddress.getPhone());
				orderAddressDto.setEmail(orderAddress.getEmail());
			} else {
				orderAddressDto.setFirstName(user.getFirstName());
				orderAddressDto.setLastName(user.getLastName());
				orderAddressDto.setAddress(user.getAddress());
				orderAddressDto.setEmail(user.getEmail());
			}
		}
		model.addAttribute("orderAddressDto", orderAddressDto);
		return "shop/cart/check-out";
	}

	@PostMapping(value = "/submit")
	public String checkOutSubmit(Model model, @Valid @ModelAttribute("orderAddressDto") OrderAddressDto orderAddressDto,
			BindingResult result, @RequestParam(name = "note") String note)
			throws IOException {
		Collection<Item> itemsCart = cartService.getItems();
		model.addAttribute("carts", itemsCart);

		float totail = cartService.getTotail();
		if (session.get("shipping") != null) {
			model.addAttribute("shipping", session.get("shipping").toString());
			if (session.get("shipping").toString().equals("1"))
				totail = totail + 10000;
			else if (session.get("shipping").toString().equals("2"))
				totail = totail + 20000;
		}
		model.addAttribute("totalCart", totail);
		//
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "shop/cart/check-out";
		}
		if (!securityService.isAuthenticated()) {
			if (userService.existsByEmail(orderAddressDto.getEmail())) {
				model.addAttribute("error", "Email đã được sử dụng");
				return "shop/cart/check-out";
			}
		}
		OrderAddress orderAddress = new OrderAddress();
		orderAddress.setId(orderAddressDto.getId());
		orderAddress.setFirstName(orderAddressDto.getFirstName());
		orderAddress.setLastName(orderAddressDto.getLastName());
		orderAddress.setAddress(orderAddressDto.getAddress());
		orderAddress.setPhone(orderAddressDto.getPhone());
		orderAddress.setEmail(orderAddressDto.getEmail());
		OrderAddress orderAddressOld = orderAddressService.saveOrUpdate(orderAddress).get();
		
		Order order = new Order();
		order.setNote(note);
		order.setStatus(Status.AWAITING_CONFIRMATION);
		if (session.get("shipping") != null) {
			if (session.get("shipping").toString().equals("1"))
				order.setShip(Ship.STANDART);
			else if (session.get("shipping").toString().equals("2"))
				order.setShip(Ship.EXPRESS);
			else 
				order.setShip(Ship.FREE);
		}
		order.setPay(Pay.UNPAID);
		order.setOrderAddress(orderAddressOld);
		Order orderOld = orderService.saveOrUpdate(order).get();
		
		Collection<Item> items = cartService.getItems();
		items.forEach(item -> {
			Product product = productService.findById(item.getId()).get();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setName(item.getName());
			orderDetail.setBanner(item.getImage());
			orderDetail.setSize(item.getSize());
			orderDetail.setPrice(item.getPrice());
			orderDetail.setDiscount(item.getDiscount());
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setProduct(product);
			orderDetail.setOrder(orderOld);
			orderDetailService.saveOrUpdate(orderDetail);
			product.setQuantity(product.getQuantity() - item.getQuantity());
			productService.saveOrUpdate(product);
		});
		cartService.clear();
		session.remove("shipping");
		return "redirect:/beauty/home";
	}
}
