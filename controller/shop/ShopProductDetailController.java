package com.example.controller.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.exception.StorageFileNotFoundException;
import com.example.model.ImageProduct;
import com.example.model.Product;
import com.example.service.ImageProductService;
import com.example.service.ProductService;
import com.example.service.StorageService;

@Controller
@RequestMapping(value = "beauty/product-detail")
public class ShopProductDetailController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ImageProductService imageProductService;
	
	@Autowired
	StorageService storageService;

	@GetMapping(value = "/quick-view/{id}")
	public ResponseEntity<Product> viewApi(@PathVariable(name = "id") Long id) {
		Optional<Product> product = productService.findById(id);
		return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/quick-view/view-image/{id}")
	public ResponseEntity<List<ImageProduct>> viewImageApi(@PathVariable(name = "id") Long id) {
		List<ImageProduct> list = imageProductService.findAllByProductId(id);
		return new ResponseEntity<List<ImageProduct>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "")
	public String productDetailPage(Model model, @RequestParam(name = "id") Long id) {
		Product product = productService.findById(id).get();
		product.setViews(product.getViews() + 1);
		productService.saveOrUpdate(product);
		
		List<ImageProduct> list = imageProductService.findAllByProductId(id);
		model.addAttribute("product", product);
		model.addAttribute("listImage", list);
		return "shop/productDetail/product-detail";
	}
	
	@GetMapping(value = "/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
