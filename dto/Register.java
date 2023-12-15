package com.example.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Register {

	@Size(min = 5, message = "{size.username}")
	@NotBlank(message = "{notblank.username}")
	private String username;
	
	@Email(message = "{email.email}")
	@NotBlank(message = "{notblank.email}")
	private String email;
	
	@Size(min = 5, message = "{size.password}")
	@NotBlank(message = "{notblank.password}")
	private String password;
}
