package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	List<OrderDetail> findAllByOrderId(Long id);
	
	@Query(value = "SELECT MONTH(o.created_date) AS month,"
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_details od "
			+ "JOIN orders o ON od.order_id = o.id "
			+ "WHERE YEAR(o.created_date)=:year "
			+ "GROUP BY MONTH(o.created_date) "
			+ "ORDER BY MONTH(o.created_date) ASC", nativeQuery = true)
	List<Object[]> statisticsRevenueMonthByYear(@Param("year")int year);
	
	@Query(value = "SELECT TOP 5 "
			+ "od.banner AS banner, "
			+ "od.name AS names, "
			+ "SUM(od.quantity) AS qty, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_details od "
			+ "JOIN orders o ON od.order_id = o.id "
			+ "WHERE o.created_date >= :dateNow AND o.created_date < :dateTo "
			+ "GROUP BY od.product_id, od.banner, od.name " 
			+ "ORDER BY totail DESC", nativeQuery = true)
	List<Object[]> getSalerByDate(@Param("dateNow")String dateNow, @Param("dateTo")String dateTo);
	
	@Query(value = "SELECT TOP 5 "
			+ "od.banner AS banner, "
			+ "od.name AS names, "
			+ "SUM(od.quantity) AS qty, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_details od "
			+ "JOIN orders o ON od.order_id = o.id "
			+ "WHERE MONTH(o.created_date) = :month "
			+ "GROUP BY od.product_id, od.banner, od.name " 
			+ "ORDER BY totail DESC", nativeQuery = true)
	List<Object[]> getSalerByMonth(@Param("month")int month);
	
	@Query(value = "SELECT TOP 5 "
			+ "od.banner AS banner, "
			+ "od.name AS names, "
			+ "SUM(od.quantity) AS qty, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_details od "
			+ "JOIN orders o ON od.order_id = o.id "
			+ "WHERE YEAR(o.created_date) = :year "
			+ "GROUP BY od.product_id, od.banner, od.name " 
			+ "ORDER BY totail DESC", nativeQuery = true)
	List<Object[]> getSalerByYear(@Param("year")int year);
	
}