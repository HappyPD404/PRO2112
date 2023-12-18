package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.ImageProduct;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {

	@Transactional
	@Modifying
	@Query("delete from ImageProduct i where i.product.id=:productId")
	void deleteByProductId(@Param("productId") Long productId);

	List<ImageProduct> findAllByProductId(Long id);
}
