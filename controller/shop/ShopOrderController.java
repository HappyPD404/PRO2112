package com.example.controller.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.Ship;
import com.example.dto.OrderHistory;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.User;
import com.example.service.OrderDetailService;
import com.example.service.OrderService;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "beauty/order")
public class ShopOrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping(value = "")
	public String orderHistory(Model model) {
		if (securityService.isAuthenticated()) {
			String username = securityService.username();
			User user = userService.findByUsername(username).get();
			List<Order> listOrder = orderService.findAllByOrderAddressEmail(user.getEmail());
			List<OrderHistory> list = new ArrayList<>();
			for (Order order : listOrder) {
				OrderHistory orderHistory = new OrderHistory();
				orderHistory.setId(order.getId());
				orderHistory.setPay(order.getPay());
				orderHistory.setShip(order.getShip());
				orderHistory.setStatus(order.getStatus());
				int quantity = 0;
				float totail = 0;
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					quantity += orderDetail.getQuantity();
					if (orderDetail.getDiscount() > 0) {
						totail += (orderDetail.getPrice() - (orderDetail.getPrice() * orderDetail.getDiscount() * 0.01))
								* orderDetail.getQuantity();
					} else {
						totail += orderDetail.getPrice() * orderDetail.getQuantity();
					}
				}
				if (order.getShip() == Ship.STANDART)
					totail += 10000;
				else if (order.getShip() == Ship.EXPRESS)
					totail += 20000;
				orderHistory.setQuantity(quantity);
				orderHistory.setTotail(totail);
				list.add(orderHistory);
			}
			model.addAttribute("list", list);
		} else {
			model.addAttribute("error", "Bạn chưa có đơn hàng nào.");
		}
		return "shop/order/orderlist";
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

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		orderService.deleteById(id);
		return new ResponseEntity<String>("Đã sản phẩm ra khỏi danh dách đơn hàng.", HttpStatus.OK);
	}

}
