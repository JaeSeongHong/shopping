package com.smhrd.shopping.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.smhrd.shopping.model.entity.Products;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "products")
public class Products extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private Long id;
	
	@Column(name="product_name")
	private String name;
	
	private Long price;
	
	@Builder
	public Products(String name, long price) {
		this.name = name;
		this.price = price;
	}
	
	public void update(String name, long price) {
		this.name = name;
		this.price = price;
	}
}
