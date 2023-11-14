package com.poly.dao;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Favorite;


public interface FavoriteDAO extends JpaRepository<Favorite, Integer> {
	 
	    @Query("SELECT f FROM Favorite f WHERE f.username = :username ORDER BY f.likeDate DESC")
	    List<Favorite> findLatestFavoritesByUsername(String username);

		List<Favorite> findByUsername(String username);

		List<Favorite> findByProductId(int productId);

		

		List<Favorite> getFavoritesByUsername(String username);

		List<Favorite> findByProductIdAndLikeDate(int productId, LocalDate likeDate);

		List<Favorite> findByUsernameAndLikeDate(String username, LocalDate likeDate);

		List<Favorite> findByUsernameAndProductId(String username, int productId);

		List<Favorite> findByLikeDate(LocalDate likeDate);

		

	
	}