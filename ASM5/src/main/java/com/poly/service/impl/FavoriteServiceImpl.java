package com.poly.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

import com.poly.dao.FavoriteDAO;
import com.poly.entity.Favorite;
import com.poly.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    FavoriteDAO dao;
    
    private FavoriteService favoriteService;


	@Override
	public List<Favorite> findByUsername(String username) {
		 return dao.findByUsername(username);
	}

	@Override
	public List<Favorite> findByProductId(int productId) {
		return dao.findByProductId(productId);
		
	}

	@Override
	public List<Favorite> findByLikeDate(LocalDate likeDate) {
		return dao.findByLikeDate(likeDate);
	}

	@Override
	public List<Favorite> findByUsernameAndProductId(String username, int productId) {
		return dao.findByUsernameAndProductId(username, productId);
    
	}

	@Override
	public List<Favorite> findByUsernameAndLikeDate(String username, LocalDate likeDate) {
		return dao.findByUsernameAndLikeDate(username, likeDate);
    }

	@Override
	public List<Favorite> findByProductIdAndLikeDate(int productId, LocalDate likeDate) {
		 return dao.findByProductIdAndLikeDate(productId, likeDate);
    }

	@Override
	public List<Favorite> getFavoritesByUsername(String username) {
		return dao.getFavoritesByUsername(username);
    }


	@Override
	public Favorite addFavorite(Favorite favorite) {
		 favorite.setLikeDate(LocalDate.now());
	        return dao.save(favorite);
	    }
}