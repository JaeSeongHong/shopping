package com.smhrd.shopping.web.dto;

import java.time.LocalDateTime;


import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersReqDto {
	private Long id;
	private String user;
	private Long totalPrice;
	private LocalDateTime orderAt;
	
	@Builder
	public OrdersReqDto(Long id, String user, Long totalPrice, LocalDateTime orderAt) {
		this.id = id;
		this.user = user;
		this.totalPrice = totalPrice;
		this.orderAt =  orderAt;
	}
}
