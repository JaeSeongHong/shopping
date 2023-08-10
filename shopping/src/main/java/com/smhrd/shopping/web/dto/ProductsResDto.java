package com.smhrd.shopping.web.dto;

import com.smhrd.shopping.model.entity.Products;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductsResDto {
	private Long id;
	private String name;
	private Long price;
	
	@Builder
	public ProductsResDto(Products products) {
		this.id = products.getId();
		this.name = products.getName();
		this.price = products.getPrice();
	}
	
	public Products toEntity() {
		return Products.builder().name(name).price(price).build();
	}
}
