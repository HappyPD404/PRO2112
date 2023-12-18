package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.OrderAddress;

public interface OrderAddressService {
	Optional<OrderAddress> saveOrUpdate(OrderAddress orderAddress);
	
	Optional<OrderAddress> findById(Long orderAddress);
	
	Boolean existsByEmail(String email);
	
	Optional<OrderAddress> findByEmail(String email);
	
	List<Object[]> getTotailOrderByDate(String dateNow, String dateTo);
	
	List<Object[]> getTotailOrderByMonth(int month);
	
	List<Object[]> getTotailOrderByYear(int year);
}