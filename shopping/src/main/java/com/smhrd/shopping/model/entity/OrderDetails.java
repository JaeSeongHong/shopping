package com.smhrd.shopping.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.smhrd.shopping.model.entity.OrderDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderDetails extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_detail_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="order_id", insertable=false, updatable=false)
	private Orders orders;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Products products;
	
	private Long count;
	
	private Long price;
	
	@Builder
	public OrderDetails(Products products, Long count) {
		this.products = products;
		this.count = count;
		this.price = this.calcuratePrice(products.getPrice(), count);
	}
	
	private Long calcuratePrice(Long price, Long count) {
		return price * count;
	}
}
