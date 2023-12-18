package com.example.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;

	@NotBlank(message = "{notblank.productName}")
	private String name;

	private int quantity = 0;

	private String color;

	private Long price;

	private float discount = 0;

	@DateTimeFormat(iso = ISO.DATE)
	private Date startDayDiscount;

	@DateTimeFormat(iso = ISO.DATE)
	private Date endDayDiscount;

	private String description;

	private Long categoryId;
}
