package com.smhrd.shopping.web.dto;

import com.smhrd.shopping.model.entity.OrderDetails;
import com.smhrd.shopping.model.entity.Products;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDetailsSaveReqDto {
	private Long productId;
	private Long count;
	private Long price;
	
	@Builder
	public OrderDetailsSaveReqDto(Long productId, Long count, Long price) {
		this.productId = productId;
		this.count = count;
		this.price = price;
	}
	
	public OrderDetails toEntity(Products products, Long count) {
		return OrderDetails.builder().products(products).count(count).build();
	}
}
