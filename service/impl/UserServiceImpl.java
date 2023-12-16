package com.example.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> saveOrUpdate(User user) {
		if(user.getId() == null || user.getId().equals(""))
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		User userOld = userRepository.save(user);
		return Optional.of(userOld);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		userRepository.deleteById(id);
	}

	@Override
	public void updateLastLoginDate(User user) {
		user.setLastLoginDate(new Date());
		saveOrUpdate(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public Page<User> findAllByUsernameLike(String keyword, Pageable pageable) {
		return userRepository.findAllByUsernameLike(keyword, pageable);
	}

	@Override
	public List<Object[]> statisticsViewMonthByYear(int year) {
		return userRepository.statisticsViewMonthByYear(year);
	}

	@Override
	public int getCustomersByDate(String dateNow, String dateTo) {
		return userRepository.getCustomersByDate(dateNow, dateTo);
	}

	@Override
	public int getCustomersByMonth(int month) {
		return userRepository.getCustomersByMonth(month);
	}

	@Override
	public int getCustomersByYear(int year) {
		return userRepository.getCustomersByYear(year);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> changePassword(String username, String password) {
		User user = userRepository.findByUsername(username).get();
		user.setPassword(passwordEncoder.encode(password));
		User userOld = userRepository.save(user);
		return Optional.of(userOld);
	}

}
