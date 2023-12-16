package com.example.controller.shop;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Category;
import com.example.model.Product;

import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.service.SessionService;

@Controller
@RequestMapping(value = "beauty")
public class ShopController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@Autowired
	SessionService session;

	@ModelAttribute(value = "categories")
	public List<Category> categories() {
		return categoryService.findAll();
	}

	@GetMapping(value = "/shop")
	public String shop(Model model, @RequestParam(name = "field") Optional<String> field,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "keywords", defaultValue = "") Optional<String> keywords,
			@RequestParam(name = "category_id") Optional<String> categoryId,
			@RequestParam(name = "color") Optional<String> color,
			@RequestParam(name = "brand_id") Optional<String> brandId) {
		String keyword = keywords.orElse(session.get("keywords"));
		session.set("keywords", keyword);
		Sort sort = Sort.by(Direction.DESC, field.orElse("id"));
		Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(12), sort);
		Page<Product> resultPage;
		if (!categoryId.isEmpty() && !categoryId.get().equals(""))
			resultPage = productService.findAllByNameLikeAndCategoryId("%" + keyword + "%",
					Long.valueOf(categoryId.get()), pageable);
		else if (!color.isEmpty() && !color.get().equals(""))
			resultPage = productService.findAllByNameLikeAndColorLike("%" + keyword + "%", color.get(), pageable);
		else
			resultPage = productService.findAllByNameLike("%" + keyword + "%", pageable);

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
		model.addAttribute("size", size.orElse(10));
		model.addAttribute("keywords", keyword);
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("categoryId", categoryId.orElse(""));
		model.addAttribute("color", color.orElse(""));
		return "shop/shop/shop";
	}
}
