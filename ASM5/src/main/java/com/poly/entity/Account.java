package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

	@Id
	@NotBlank(message = "Không được để trống")
	String username;
	@NotBlank(message = "Không được để trống")
	@Size(min = 3, max = 12, message = "Mật khẩu phải từ 3 đến 12 ký tự")
	String password;
	@NotBlank(message = "Không được để trống")
	String fullname;
	@NotBlank(message = "Không được để trống")
	@Email(message = "Email không đúng định dạng")
	String email;
	String photo;
	String token;
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;
}