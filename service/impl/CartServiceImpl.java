package com.example.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.dto.Item;
import com.example.model.Product;
import com.example.service.CartService;
import com.example.service.ProductService;

@SessionScope
@Service
public class CartServiceImpl implements CartService {

	Map<Long, Item> map = new HashMap<>();

	@Autowired
	ProductService productService;

	@Override
	public Item add(Long id, String size, int qty) {
		if (getItem(id) == null) {
			Product product = productService.findById(id).get();
			Item item = new Item();
			item.setId(id);
			item.setName(product.getName());
			item.setImage(product.getBanner());
			item.setSize(size);
			item.setPrice(product.getPrice());
			if (product.getDiscount() > 0 && product.getStartDayDiscount() != null
					&& product.getEndDayDiscount() != null) {
				Long dateNow = (new Date()).getTime();
				if (product.getStartDayDiscount().getTime() <= dateNow
						&& product.getEndDayDiscount().getTime() >= dateNow) {
					item.setDiscount(product.getDiscount());
				}
			}
			item.setQuantity(qty);
			map.put(id, item);
		} else {
			map.forEach((key, value) -> {
				if (value.getId() == id) {
					value.setQuantity(value.getQuantity() + 1);
					map.put(id, value);
					return;
				}
			});
		}
		return null;
	}

	@Override
	public void remove(Long id) {
		map.remove(id);
	}

	@Override
	public Item update(Long id, int qty) {
		for (Item item : map.values()) {
			if (item.getId() == id) {
				item.setQuantity(qty);
				map.put(id, item);
				return item;
			}
		}
		return null;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Collection<Item> getItems() {
		return map.values();
	}

	@Override
	public Item getItem(Long id) {
		for (Item item : map.values()) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	@Override
	public int getCount() {
		return map.size();
	}

	@Override
	public float getTotail() {
		float amount = 0;
		for (Item item : map.values()) {
			if (item.getDiscount() == 0)
				amount += item.getPrice() * item.getQuantity();
			else
				amount += (item.getPrice() - (item.getPrice() * item.getDiscount() * 0.01))* item.getQuantity();
		}
		return amount;
	}

}
