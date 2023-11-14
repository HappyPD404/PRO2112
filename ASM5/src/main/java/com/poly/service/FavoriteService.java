package com.poly.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.FavoriteDAO;
import com.poly.entity.Favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface FavoriteService {
    
List<Favorite> findByUsername(String username);

List<Favorite> findByProductId(int productId);

List<Favorite> findByLikeDate(LocalDate likeDate);

List<Favorite> findByUsernameAndProductId(String username, int productId);

List<Favorite> findByUsernameAndLikeDate(String username, LocalDate likeDate);

List<Favorite> findByProductIdAndLikeDate(int productId, LocalDate likeDate);

List<Favorite> getFavoritesByUsername(String username);

Favorite addFavorite(Favorite favorite);

}
