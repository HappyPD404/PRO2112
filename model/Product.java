package com.example.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "products")
public class Product extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = true, columnDefinition = "Nvarchar(500)")
	private String name;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "color")
	private String color;

	@Column(name = "price")
	private Long price;

	@Column(name = "discount")
	private float discount;

	@Column(name = "start_day_discount")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date startDayDiscount;

	@Column(name = "end_day_discount")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date endDayDiscount;

	@Column(name = "views")
	private int views = 0;

	@Column(name = "description", nullable = true, columnDefinition = "Nvarchar(500)")
	private String description;

	@Column(name = "banner", nullable = true, columnDefinition = "Nvarchar(500)")
	private String banner;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ImageProduct> imageProducts;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetail;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Like> likes;
}
