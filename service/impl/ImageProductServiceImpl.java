package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ImageProduct;
import com.example.repository.ImageProductRepository;
import com.example.service.ImageProductService;

@Service
public class ImageProductServiceImpl implements ImageProductService {

	@Autowired
	ImageProductRepository imageProductRepository;

	@Override
	public Optional<ImageProduct> saveOrUpdate(ImageProduct entity) {
		ImageProduct imageProduct = imageProductRepository.save(entity);
		return Optional.of(imageProduct);
	}

	@Override
	public void deleteByProductId(Long productId) {
		imageProductRepository.deleteByProductId(productId);
	}

	@Override
	public List<ImageProduct> findAllByProductId(Long id) {
		return imageProductRepository.findAllByProductId(id);
	}
}
