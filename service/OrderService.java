package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.Order;

public interface OrderService {

	Optional<Order> saveOrUpdate(Order order);

	Optional<Order> findById(Long id);

	/**
	 * @param id
	 */
	void deleteById(Long id);

	List<Order> findAllByOrderAddressEmail(String email);

	Page<Order> findAllByOrderAddressEmailLike(String keyword, Pageable pageable);

	List<Integer> getYearOrder();

	List<Object[]> statisticsRevenueCategoryByYear(int year);

	int getCountOrderByDate(String dateNow, String dateTo);

	int getCountOrderByMonth(int month);

	int getCountOrderByYear(int year);

	float getRevenueByDate(String dateNow, String dateTo);

	float getRevenueByMonth(int month);

	float getRevenueByYear(int year);
}
