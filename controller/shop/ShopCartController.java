package com.example.controller.shop;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.Item;
import com.example.dto.OrderItem;
import com.example.model.Product;
import com.example.service.CartService;
import com.example.service.ProductService;
import com.example.service.SessionService;

@Controller
@RequestMapping(value = "beauty/cart")
public class ShopCartController {

	@Autowired
	ProductService productService;

	@Autowired
	CartService cartService;

	@Autowired
	SessionService session;

	@GetMapping(value = "")
	public String cartPage(Model model) {
		Collection<Item> items = cartService.getItems();
		model.addAttribute("carts", items);
		model.addAttribute("totalCart", cartService.getTotail());
		return "shop/cart/view-cart";
	}

	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseEntity<String> add(@RequestBody OrderItem item) {
		cartService.add(item.getId(), item.getSize(), item.getQty());
		return new ResponseEntity<String>("Đã thêm sản phẩm vào giỏ hàng thành công.", HttpStatus.OK);
	}

	@DeleteMapping(value = "/remove/{id}")
	public ResponseEntity<String> remove(@PathVariable(name = "id") Long id) {
		cartService.remove(id);
		return new ResponseEntity<String>("Đã xoá sản phẩm ra khỏi giỏ hàng.", HttpStatus.OK);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<Item> update(@RequestParam("id") Long id, @RequestParam("sst") Integer quantity) {
		cartService.update(id, quantity);
		return new ResponseEntity<Item>(cartService.getItem(id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/shipping")
	public ResponseEntity<?> shipping(@RequestParam("ship") int ship) {
		session.set("shipping", ship);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value = "/find-qty")
	public ResponseEntity<Integer> maxQty(Model model, @RequestParam(name = "id") Long id) {
		Product product = productService.findById(id).get();
		return new ResponseEntity<Integer>(product.getQuantity(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/totail-cart")
	public ResponseEntity<Float> totailCart() {
		return new ResponseEntity<Float>(cartService.getTotail(), HttpStatus.OK);
	}
}
