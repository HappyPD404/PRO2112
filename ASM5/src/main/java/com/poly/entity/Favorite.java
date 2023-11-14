package com.poly.entity;


import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Favorite {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private int productId;
    private LocalDate likeDate;
	public void setLikeDate(LocalDate now) {
		// TODO Auto-generated method stub
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public LocalDate getLikeDate() {
		return likeDate;
	}
	public Favorite(int id, String username, int productId, LocalDate likeDate) {
		super();
		this.id = id;
		this.username = username;
		this.productId = productId;
		this.likeDate = likeDate;
	}

    // Constructors, getters, and setters
	

}