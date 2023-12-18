package com.example.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.helper.DateHelper;
import com.example.service.OrderAddressService;
import com.example.service.OrderDetailService;
import com.example.service.OrderService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "home")
public class HomeController {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private Date datenow = new Date();

	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderAddressService orderAddressService;
	
	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping(value = "")
	public String home() {
		return "admin/home/index";
	}

	@GetMapping(value = "/count-order-date-now")
	public ResponseEntity<Integer> getCountOrderDateNow() {
		Date dateTo = DateHelper.sumNumberDate(datenow, 1);
		int countDateNow = orderService.getCountOrderByDate(formatter.format(datenow), formatter.format(dateTo));
		return new ResponseEntity<Integer>(countDateNow, HttpStatus.OK);
	}

	@GetMapping(value = "/count-order-date-ye")
	public ResponseEntity<Integer> getCountOrderDateYe() {
		Date dateYe = DateHelper.sumNumberDate(datenow, -1);
		int countDateYe = orderService.getCountOrderByDate(formatter.format(dateYe), formatter.format(datenow));
		return new ResponseEntity<Integer>(countDateYe, HttpStatus.OK);
	}

	@GetMapping(value = "/count-order-month-now")
	public ResponseEntity<Integer> getCountOrderMonthNow() {
		int countMonthNow = orderService.getCountOrderByMonth(DateHelper.getMonthNow() + 1);
		return new ResponseEntity<Integer>(countMonthNow, HttpStatus.OK);
	}

	@GetMapping(value = "/count-order-month-ye")
	public ResponseEntity<Integer> getCountOrderMonthYe() {
		int countMonthYe = orderService.getCountOrderByMonth(DateHelper.getMonthNow());
		return new ResponseEntity<Integer>(countMonthYe, HttpStatus.OK);
	}

	@GetMapping(value = "/count-order-year-now")
	public ResponseEntity<Integer> getCountOrderYearNow() {
		int countYearNow = orderService.getCountOrderByYear(DateHelper.getYearNow());
		return new ResponseEntity<Integer>(countYearNow, HttpStatus.OK);
	}

	@GetMapping(value = "/count-order-year-ye")
	public ResponseEntity<Integer> getCountOrderYearYe() {
		int countYearYe = orderService.getCountOrderByYear(DateHelper.getYearNow() - 1);
		return new ResponseEntity<Integer>(countYearYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/revenue-date-now")
	public ResponseEntity<Float> getRevenueDateNow() {
		Date dateTo = DateHelper.sumNumberDate(datenow, 1);
		float revenueNow = orderService.getRevenueByDate(formatter.format(datenow), formatter.format(dateTo));
		return new ResponseEntity<Float>(revenueNow, HttpStatus.OK);
	}

	@GetMapping(value = "/revenue-date-ye")
	public ResponseEntity<Float> getRevenueDateYe() {
		Date dateYe = DateHelper.sumNumberDate(datenow, -1);
		float revenueYe = orderService.getRevenueByDate(formatter.format(dateYe), formatter.format(datenow));
		return new ResponseEntity<Float>(revenueYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/revenue-month-now")
	public ResponseEntity<Float> getRevenueMonthNow() {
		float revenueMonthNow = orderService.getRevenueByMonth(DateHelper.getMonthNow() + 1);
		return new ResponseEntity<Float>(revenueMonthNow, HttpStatus.OK);
	}

	@GetMapping(value = "/revenue-month-ye")
	public ResponseEntity<Float> getRevenueMonthYe() {
		float revenueMonthYe = orderService.getRevenueByMonth(DateHelper.getMonthNow());
		return new ResponseEntity<Float>(revenueMonthYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/revenue-year-now")
	public ResponseEntity<Float> getRevenueYearNow() {
		float revenueYearNow = orderService.getRevenueByYear(DateHelper.getYearNow());
		return new ResponseEntity<Float>(revenueYearNow, HttpStatus.OK);
	}

	@GetMapping(value = "/revenue-year-ye")
	public ResponseEntity<Float> getRevenueYearYe() {
		float revenueYearYe = orderService.getRevenueByYear(DateHelper.getYearNow() - 1);
		return new ResponseEntity<Float>(revenueYearYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/customers-date-now")
	public ResponseEntity<Integer> getCustomersDateNow() {
		Date dateTo = DateHelper.sumNumberDate(datenow, 1);
		int customersDateNow = userService.getCustomersByDate(formatter.format(datenow), formatter.format(dateTo));
		return new ResponseEntity<Integer>(customersDateNow, HttpStatus.OK);
	}

	@GetMapping(value = "/customers-date-ye")
	public ResponseEntity<Integer> getCustomersDateYe() {
		Date dateYe = DateHelper.sumNumberDate(datenow, -1);
		int customersDateYe = userService.getCustomersByDate(formatter.format(dateYe), formatter.format(datenow));
		return new ResponseEntity<Integer>(customersDateYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/customers-month-now")
	public ResponseEntity<Integer> getCustomersMonthNow() {
		int customersMonthNow = userService.getCustomersByMonth(DateHelper.getMonthNow() + 1);
		return new ResponseEntity<Integer>(customersMonthNow, HttpStatus.OK);
	}

	@GetMapping(value = "/customers-month-ye")
	public ResponseEntity<Integer> getCustomersMonthYe() {
		int customersMonthYe = userService.getCustomersByMonth(DateHelper.getMonthNow());
		return new ResponseEntity<Integer>(customersMonthYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/customers-year-now")
	public ResponseEntity<Integer> getCustomersYearNow() {
		int customersYearNow = userService.getCustomersByYear(DateHelper.getYearNow());
		return new ResponseEntity<Integer>(customersYearNow, HttpStatus.OK);
	}

	@GetMapping(value = "/customers-year-ye")
	public ResponseEntity<Integer> getCustomersYearYe() {
		int customersYearYe = userService.getCustomersByYear(DateHelper.getYearNow() - 1);
		return new ResponseEntity<Integer>(customersYearYe, HttpStatus.OK);
	}
	
	@GetMapping(value = "/totail-order-date-now")
	public ResponseEntity<List<Object[]>> getTotailOrderDateNow() {
		Date dateTo = DateHelper.sumNumberDate(datenow, 1);
		List<Object[]> list = orderAddressService.getTotailOrderByDate(formatter.format(datenow), formatter.format(dateTo));
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/totail-order-month-now")
	public ResponseEntity<List<Object[]>> getTotailOrderMonthNow() {
		List<Object[]> list = orderAddressService.getTotailOrderByMonth(DateHelper.getMonthNow() + 1);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/totail-order-year-now")
	public ResponseEntity<List<Object[]>> getTotailOrderYearNow() {
		List<Object[]> list = orderAddressService.getTotailOrderByYear(DateHelper.getYearNow());
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/saler-date-now")
	public ResponseEntity<List<Object[]>> getSalerDateNow() {
		Date dateTo = DateHelper.sumNumberDate(datenow, 1);
		List<Object[]> list = orderDetailService.getSalerByDate(formatter.format(datenow), formatter.format(dateTo));
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/saler-month-now")
	public ResponseEntity<List<Object[]>> getSalerMonthNow() {
		List<Object[]> list = orderDetailService.getSalerByMonth(DateHelper.getMonthNow() + 1);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping(value = "/saler-year-now")
	public ResponseEntity<List<Object[]>> getSalerYearNow() {
		List<Object[]> list = orderDetailService.getSalerByYear(DateHelper.getYearNow());
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
}
