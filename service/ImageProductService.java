package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.ImageProduct;

public interface ImageProductService {

	Optional<ImageProduct> saveOrUpdate(ImageProduct entity);
	
	void deleteByProductId(Long productId);
	
	List<ImageProduct> findAllByProductId(Long id);

}
