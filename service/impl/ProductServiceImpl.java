package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public Optional<Product> saveOrUpdate(Product entity) {
		Product product = productRepository.save(entity);
		return Optional.of(product);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Page<Product> findAllByNameLike(String keyword, Pageable pageable) {
		return productRepository.findAllByNameLike(keyword, pageable);
	}

	@Override
	public Page<Product> findAllByNameLikeAndColorLike(String keyword, String color, Pageable pageable) {
		return productRepository.findAllByNameLikeAndColorLike(keyword, color, pageable);
	}

	@Override
	public Page<Product> findAllByNameLikeAndCategoryId(String keyword, Long id, Pageable pageable) {
		return productRepository.findAllByNameLikeAndCategoryId(keyword, id, pageable);
	}

	@Override
	public List<Object[]> statisticsViewCategory() {
		return productRepository.statisticsViewCategory();
	}

	@Override
	public List<Product> findAllByCategoryId(Long id) {
		return productRepository.findAllByCategoryId(id);
	}
}
