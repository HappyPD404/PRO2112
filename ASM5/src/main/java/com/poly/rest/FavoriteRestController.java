package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.poly.entity.Favorite;
import com.poly.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorite")
public class FavoriteRestController {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/{username}")
    public List<Favorite> getFavoritesByUsername(@PathVariable String username) {
        return favoriteService.getFavoritesByUsername(username);
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite) {
        return favoriteService.addFavorite(favorite);
    }

	/*
	 * @DeleteMapping("/{favoriteId}") public void removeFavorite(@PathVariable int
	 * favoriteId) { favoriteService.addFavorite(favoriteId); }
	 */
}