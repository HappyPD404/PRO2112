package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.OrderAddress;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {

	Boolean existsByEmail(String email);
	
	Optional<OrderAddress> findByEmail(String email);
	
	@Query(value = "SELECT TOP 5 "
			+ "ad.first_name + ' ' + ad.last_name AS fullName, "
			+ "ad.email AS email, "
			+ "ad.phone AS phone, "
			+ "SUM(od.quantity) AS qty, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_address ad "
			+ "INNER JOIN orders o ON ad.id = o.order_address_id "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "WHERE o.created_date >= :dateNow AND o.created_date < :dateTo "
			+ "GROUP BY ad.email, ad.first_name + ' ' + ad.last_name, ad.phone "
			+ "ORDER BY totail DESC", nativeQuery = true)
	List<Object[]> getTotailOrderByDate(@Param("dateNow")String dateNow, @Param("dateTo")String dateTo);
	
	@Query(value = "SELECT TOP 5 "
			+ "ad.first_name + ' ' + ad.last_name AS fullName, "
			+ "ad.email AS email, "
			+ "ad.phone AS phone, "
			+ "SUM(od.quantity) AS qty, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_address ad "
			+ "INNER JOIN orders o ON ad.id = o.order_address_id "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "WHERE MONTH(o.created_date) = :month "
			+ "GROUP BY ad.email, ad.first_name + ' ' + ad.last_name, ad.phone "
			+ "ORDER BY totail DESC", nativeQuery = true)
	List<Object[]> getTotailOrderByMonth(@Param("month")int month);
	
	@Query(value = "SELECT TOP 5 "
			+ "ad.first_name + ' ' + ad.last_name AS fullName, "
			+ "ad.email AS email, "
			+ "ad.phone AS phone, "
			+ "SUM(od.quantity) AS qty, "
			+ "SUM(CASE WHEN od.discount<=0 "
			+ "THEN od.price*od.quantity "
			+ "ELSE (od.price-(od.price*od.discount*0.01))*od.quantity END) AS totail "
			+ "FROM order_address ad "
			+ "INNER JOIN orders o ON ad.id = o.order_address_id "
			+ "INNER JOIN order_details od ON o.id = od.order_id "
			+ "WHERE YEAR(o.created_date) = :year "
			+ "GROUP BY ad.email, ad.first_name + ' ' + ad.last_name, ad.phone "
			+ "ORDER BY totail DESC", nativeQuery = true)
	List<Object[]> getTotailOrderByYear(@Param("year")int year);
	
}
