package com.example.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddressDto {

	private Long id;
	
	@NotBlank(message = "{notblank.firstName}")
	private String firstName;
	
	@NotBlank(message = "{notblank.lastName}")
	private String lastName;
	
	@NotBlank(message = "{notblank.address}")
	private String address;
	
	@NotBlank(message = "{notblank.phone}")
	private String phone;
	
	@Email(message = "{email.email}")
	@NotBlank(message = "{notblank.email}")
	private String email;
}
