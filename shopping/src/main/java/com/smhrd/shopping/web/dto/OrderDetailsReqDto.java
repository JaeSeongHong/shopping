package com.smhrd.shopping.web.dto;

import java.time.LocalDateTime;

import com.smhrd.shopping.model.entity.OrderDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderDetailsReqDto {
	private Long orderId;
	private Long orderDetailId;	
	private Long productId;
	private String productName;
	private Long count;
	private Long price;
	private LocalDateTime orderedAt;
	
	@Builder
	public OrderDetailsReqDto(OrderDetails orderDetails) {
		this.orderId = orderDetails.getOrders().getId();
		this.orderDetailId = orderDetails.getId();
		this.productId = orderDetails.getProducts().getId();
		this.productName = orderDetails.getProducts().getName();
		this.count = orderDetails.getCount();
		this.price = orderDetails.getPrice();
		this.orderedAt = orderDetails.getCreatedAt();
	}
}
