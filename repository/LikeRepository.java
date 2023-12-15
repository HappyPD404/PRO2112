package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
	
	List<Like> findAllByUserId(String id);
	
	@Query(value = "SELECT "
			+ "MONTH(l.created_date) AS month, "
			+ "COUNT(l.product_id) AS likes "
			+ "FROM likes l "
			+ "WHERE YEAR(l.created_date)=2023 "
			+ "GROUP BY MONTH(l.created_date) "
			+ "ORDER BY MONTH(l.created_date) ASC", nativeQuery = true)
	List<Object[]> statisticsLikeMonthByYear(@Param("year")int year);
	
	@Query(value = "SELECT "
			+ "c.name AS name, "
			+ "COUNT(l.id) AS likes "
			+ "FROM likes l "
			+ "JOIN products p ON l.product_id = p.id "
			+ "JOIN categories c ON p.category_id = c.id "
			+ "GROUP BY c.name", nativeQuery = true)
	List<Object[]> statisticsLikeCategory();
	
}
