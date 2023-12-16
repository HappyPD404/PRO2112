package com.example.dto;

import com.example.common.Pay;
import com.example.common.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrder {
	private Long id;
	private Pay pay;
	private Status status;
}
