package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findAllByNameLike(String keyword, Pageable pageable);

	Page<Product> findAllByNameLikeAndColorLike(String keyword, String color, Pageable pageable);

	Page<Product> findAllByNameLikeAndCategoryId(String keyword, Long id, Pageable pageable);

	List<Product> findAllByCategoryId(Long id);

	@Query(value = "SELECT "
			+ "c.name AS name, "
			+ "SUM(p.views) AS views "
			+ "FROM products p "
			+ "JOIN categories c ON p.category_id = c.id "
			+ "GROUP BY c.name", nativeQuery = true)
	List<Object[]> statisticsViewCategory();

}
