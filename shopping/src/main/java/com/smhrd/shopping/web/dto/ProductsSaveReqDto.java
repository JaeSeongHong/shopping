package com.smhrd.shopping.web.dto;

import com.smhrd.shopping.model.entity.Products;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductsSaveReqDto {
	private Long id;
	private String name;
	private Long price;
	
	@Builder
	public ProductsSaveReqDto (Long id, String name, Long price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public Products toEntity() {
		return Products.builder().name(name).price(price).build();
	}
} 
