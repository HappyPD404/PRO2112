package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAllByOrderAddressEmailOrderByCreatedDateAsc(String email);
	
	Page<Order> findAllByOrderAddressEmailLike(String keyword, Pageable pageable);
	
	@Query(value = "SELECT YEAR(o.created_date) AS year "
			+ "FROM orders o "
			+ "GROUP BY YEAR(o.created_date) "
			+ "ORDER BY YEAR(o.created_date) ASC", nativeQuery = true)
	List<Integer> getYearOrder();
	
	@Query(value = "SELECT "
			+ "c.name AS name, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM orders o "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "INNER JOIN products p ON od.product_id = p.id "
			+ "INNER JOIN categories c ON p.category_id = c.id "
			+ "WHERE YEAR(o.created_date)=:year "
			+ "GROUP BY c.name", nativeQuery = true)
	List<Object[]> statisticsRevenueCategoryByYear(@Param("year")int year);
	
	@Query(value = "SELECT "
			+ "COUNT(o.id) AS orders "
			+ "FROM orders o "
			+ "WHERE o.created_date >= :dateNow AND o.created_date < :dateTo", nativeQuery = true)
	Integer getCountOrderByDate(@Param("dateNow")String dateNow, @Param("dateTo")String dateTo);
	// parttern yyyy-mm-dd
	@Query(value = "SELECT "
			+ "COUNT(o.id) AS orders "
			+ "FROM orders o "
			+ "WHERE MONTH(o.created_date) = :month", nativeQuery = true)
	Integer getCountOrderByMonth(@Param("month")int month);
	
	@Query(value = "SELECT "
			+ "COUNT(o.id) AS orders "
			+ "FROM orders o "
			+ "WHERE YEAR(o.created_date) = :year", nativeQuery = true)
	Integer getCountOrderByYear(@Param("year")int year);
	
	@Query(value = "SELECT "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM orders o "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "WHERE o.created_date >= :dateNow AND o.created_date < :dateTo", nativeQuery = true)
	Float getRevenueByDate(@Param("dateNow")String dateNow, @Param("dateTo")String dateTo);
	
	@Query(value = "SELECT "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM orders o "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "WHERE MONTH(o.created_date) = :month", nativeQuery = true)
	Float getRevenueByMonth(@Param("month")int month);
	
	@Query(value = "SELECT "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM orders o "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "WHERE YEAR(o.created_date) = :year", nativeQuery = true)
	Float getRevenueByYear(@Param("year")int year);
	
}
