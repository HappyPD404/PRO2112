package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public Optional<Order> saveOrUpdate(Order order) {
		Order orderOld = orderRepository.save(order);
		return Optional.of(orderOld);
	}

	@Override
	public Optional<Order> findById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		return order;
	}

	@Override
	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}

	@Override
	public List<Order> findAllByOrderAddressEmail(String email) {
		return orderRepository.findAllByOrderAddressEmailOrderByCreatedDateAsc(email);
	}

	@Override
	public Page<Order> findAllByOrderAddressEmailLike(String keyword, Pageable pageable) {
		return orderRepository.findAllByOrderAddressEmailLike(keyword, pageable);
	}

	@Override
	public List<Integer> getYearOrder() {
		return orderRepository.getYearOrder();
	}

	@Override
	public List<Object[]> statisticsRevenueCategoryByYear(int year) {
		return orderRepository.statisticsRevenueCategoryByYear(year);
	}

	@Override
	public int getCountOrderByDate(String dateNow, String dateTo) {
		return orderRepository.getCountOrderByDate(dateNow, dateTo);
	}

	@Override
	public int getCountOrderByMonth(int month) {
		return orderRepository.getCountOrderByMonth(month);
	}

	@Override
	public int getCountOrderByYear(int year) {
		return orderRepository.getCountOrderByYear(year);
	}

	@Override
	public float getRevenueByDate(String dateNow, String dateTo) {
		if(orderRepository.getRevenueByDate(dateNow, dateTo) == null)
			return 0;
		return orderRepository.getRevenueByDate(dateNow, dateTo);
	}

	@Override
	public float getRevenueByMonth(int month) {
		if(orderRepository.getRevenueByMonth(month) == null)
			return 0;
		return orderRepository.getRevenueByMonth(month);
	}

	@Override
	public float getRevenueByYear(int year) {
		if(orderRepository.getRevenueByYear(year) == null)
			return 0;
		return orderRepository.getRevenueByYear(year);
	}

}
