package com.smhrd.shopping.web.dto;

import java.time.LocalDateTime;

import com.smhrd.shopping.model.entity.Orders;

import lombok.Getter;

@Getter
public class OrdersResDto {
	private Long id;
	private String user;
	private Long totalPrice;
	private LocalDateTime orderAt;
	
	public OrdersResDto(Orders orders) {
		this.id = orders.getId();
		this.user = orders.getUsers().getUsername();
		this.totalPrice = orders.getTotalPrice();
		this.orderAt =  orders.getCreatedAt();
	}
}
