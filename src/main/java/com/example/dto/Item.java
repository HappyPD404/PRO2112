package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private Long id;
	private String name;
	private String image;
	private String size;
	private float price;
	private float discount = 0;
	private int quantity;
}
