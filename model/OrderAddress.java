package com.example.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_address")
public class OrderAddress extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name", nullable = false, columnDefinition = "nvarchar(50)")
	private String firstName;

	@Column(name = "last_name", nullable = false, columnDefinition = "nvarchar(50)")
	private String lastName;

	@Column(name = "address", nullable = false, columnDefinition = "nvarchar(255)")
	private String address;
	
	@Column(name = "phone", nullable = false, columnDefinition = "varchar(11)")
	private String phone;
	
	@Column(name = "email", nullable = false, columnDefinition = "nvarchar(50)")
	private String email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "orderAddress", cascade = CascadeType.ALL)
	private List<Order> orders;

}
