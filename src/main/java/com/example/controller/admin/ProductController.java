package com.example.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductDto;
import com.example.exception.StorageFileNotFoundException;
import com.example.model.Category;
import com.example.model.ImageProduct;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.CategoryService;
import com.example.service.ImageProductService;
import com.example.service.ProductService;
import com.example.service.SessionService;
import com.example.service.StorageService;

@Controller
@RequestMapping(value = "products")
public class ProductController {

	@Autowired
	StorageService storageService;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ImageProductService imageProductService;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SessionService session;

	@ModelAttribute(value = "categories")
	public List<Category> categories() {
		return categoryService.findAll();
	}

	@GetMapping(value = "")
	public String list(Model model, @RequestParam(name = "field") Optional<String> field,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "keywords", defaultValue = "") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords"));
		session.set("keywords", keyword);
		Sort sort = Sort.by(Direction.DESC, field.orElse("id"));
		Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
		Page<Product> resultPage = productRepository.findAllByNameLike("%" + keyword + "%", pageable);
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
		return "admin/products/product-list";
	}

	@GetMapping(value = "/view/{id}")
	public ResponseEntity<Product> viewApi(@PathVariable(name = "id") Long id) {
		Optional<Product> product = productService.findById(id);
		return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
	}

	@GetMapping(value = "/view-image/{id}")
	public ResponseEntity<List<ImageProduct>> viewImageApi(@PathVariable(name = "id") Long id) {
		List<ImageProduct> list = imageProductService.findAllByProductId(id);
		return new ResponseEntity<List<ImageProduct>>(list, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteApi(@PathVariable(name = "id") Long id) throws IOException {
		List<ImageProduct> list = imageProductService.findAllByProductId(id);
		for (ImageProduct imageProduct : list) {
			storageService.delete(imageProduct.getName());
		}
		Optional<Product> product = productService.findById(id);
		if (product.get().getBanner() != null)
			storageService.delete(product.get().getBanner());
		productService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(Model model, @ModelAttribute("productDto") ProductDto productDto) {
		return "admin/products/product-form";
	}

	@GetMapping(value = "/edit")
	public String saveOrUpdateId(Model model, @RequestParam(value = "id") Long id) {
		Product product = productService.findById(id).get();
		ProductDto productDto = new ProductDto();
		productDto.setId(id);
		productDto.setName(product.getName());
		productDto.setQuantity(product.getQuantity());
		productDto.setColor(product.getColor());
		productDto.setPrice(product.getPrice());
		productDto.setDiscount(product.getDiscount());
		productDto.setStartDayDiscount(product.getStartDayDiscount());
		productDto.setEndDayDiscount(product.getEndDayDiscount());
		productDto.setDescription(product.getDescription());
		// productDto.setInfo(product.getInfo());
		productDto.setCategoryId(product.getCategory().getId());

		model.addAttribute("productDto", productDto);
		return "admin/products/product-form";
	}

	@PostMapping(value = "/saveOrUpdate/submit")
	public String saveOrUpdate(Model model, @Valid @ModelAttribute("productDto") ProductDto productDto,
			BindingResult result, @RequestParam(name = "files") MultipartFile[] files) throws IOException {
		System.out.println(files.length);
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "admin/products/product-form";
		}
		Category category = categoryService.findById(productDto.getCategoryId()).get();
		Product product;
		if (productDto.getId() != null) {
			product = productService.findById(productDto.getId()).get();
			storageService.delete(product.getBanner());
			if (files.length > 1) {
				List<ImageProduct> list = imageProductService.findAllByProductId(product.getId());
				for (ImageProduct imageProduct : list) {
					storageService.delete(imageProduct.getName());
				}
				imageProductService.deleteByProductId(productDto.getId());
			}
		} else {
			product = new Product();
		}
		product.setName(productDto.getName());
		product.setQuantity(productDto.getQuantity());
		product.setColor(productDto.getColor());
		product.setPrice(productDto.getPrice());
		product.setDiscount(productDto.getDiscount());
		product.setStartDayDiscount(productDto.getStartDayDiscount());
		product.setEndDayDiscount(productDto.getEndDayDiscount());
		product.setDescription(productDto.getDescription());
		// product.setInfo(productDto.getInfo());
		if (!files[0].isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			product.setBanner(storageService.getStorageFilename(files[0], uuidString));
			storageService.store(files[0], product.getBanner());
		}

		product.setCategory(category);
		Product productOld = productService.saveOrUpdate(product).get();
		if (files.length > 1) {
			for (MultipartFile multipartFile : files) {
				UUID uuid = UUID.randomUUID();
				String uuidString = uuid.toString();
				ImageProduct imageProduct = new ImageProduct();
				imageProduct.setName(storageService.getStorageFilename(multipartFile, uuidString));
				storageService.store(multipartFile, imageProduct.getName());
				imageProduct.setProduct(productOld);
				imageProductService.saveOrUpdate(imageProduct);
			}
		}
		return "redirect:/products";
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
