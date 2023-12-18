package com.example.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class UserDto {

	private String id;
	
	@Size(min = 5, message = "{size.username}")
	@NotBlank(message = "{notblank.username}")
	private String username;
	
	@NotBlank(message = "{notblank.firstName}")
	private String firstName;
	
	@NotBlank(message = "{notblank.lastName}")
	private String lastName;
	
	@Email(message = "{email.email}")
	@NotBlank(message = "{notblank.email}")
	private String email;
	
	@NotBlank(message = "{notblank.address}")
	private String address;
	
	@DateTimeFormat(iso = ISO.DATE)
	private Date birthDay;
	
	private Boolean gender;
	
	private Boolean login;
	
	private Boolean role;
}
