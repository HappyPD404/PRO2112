package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.User;

public interface UserService {
	Optional<User> saveOrUpdate(User user);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findById(String id);
	
	Optional<User> findByEmail(String email);
	
	void deleteById(String id);
	
	void updateLastLoginDate(User user);
	
	List<User> findAll();

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
	
	Page<User> findAllByUsernameLike(String keyword, Pageable pageable);
	
	List<Object[]> statisticsViewMonthByYear(int year);
	
	int getCustomersByDate(String dateNow, String dateTo);
	
	int getCustomersByMonth(int month);
	
	int getCustomersByYear(int year);
	
	Optional<User> changePassword(String username, String password);
}