package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.OrderDetail;

public interface OrderDetailService {

	Optional<OrderDetail> saveOrUpdate(OrderDetail orderDetail);

	List<OrderDetail> findAllByOrderId(Long id);
	
	List<Object[]> statisticsRevenueMonthByYear(int year);
	
	List<Object[]> getSalerByDate(String dateNow, String dateTo);
	
	List<Object[]> getSalerByMonth(int month);
	
	List<Object[]> getSalerByYear(int year);
}
