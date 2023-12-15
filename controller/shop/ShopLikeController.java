package com.example.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Like;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.LikeService;
import com.example.service.ProductService;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "beauty/like")
public class ShopLikeController {
	
	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	LikeService likeService;

	@GetMapping(value = "")
	public String like(Model model) {
		if (securityService.isAuthenticated()) {
			String username = securityService.username();
			User user = userService.findByUsername(username).get();
			List<Like> list = likeService.findAllByUserId(user.getId());
			model.addAttribute("list", list);
		}else {
			model.addAttribute("error", "Bạn chưa thích sản phẩm nào.");
		}
		return "shop/like/wishlist";
	}

	@PostMapping(value = "/add/{id}")
	public ResponseEntity<String> add(@PathVariable(name = "id") Long id) {
		if (securityService.isAuthenticated()) {
			String username = securityService.username();
			User user = userService.findByUsername(username).get();
			
			Product product = productService.findById(id).get();
			
			Like like = new Like();
			like.setUser(user);
			like.setProduct(product);
			likeService.saveOrUpdate(like);
		}else {
			return new ResponseEntity<String>("Đăng nhập để thực hiện chức năng này.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Đã thêm sản phẩm vào danh sách yêu thích thành công.", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		likeService.deleteById(id);
		return new ResponseEntity<String>("Đã xóa sản phẩm ra khỏi danh dách yêu thích.", HttpStatus.OK);
	}
}
