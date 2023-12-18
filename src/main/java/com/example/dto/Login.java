package com.example.dto;

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
public class Login {

	@Size(min = 5, message = "{size.username}")
	@NotBlank(message = "{notblank.username}")
	private String username;
	
	@Size(min = 5, message = "{size.password}")
	@NotBlank(message = "{notblank.password}")
	private String password;
}
