package com.smhrd.shopping.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Orders extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users users;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="order_id")
	private List<OrderDetails> orderDetails;
	
	private Long totalPrice;
	
	@Builder
	public Orders(Users users, List<OrderDetails> orderDetails) {
		this.users = users;
		this.orderDetails = orderDetails;
		
		for (OrderDetails element : orderDetails) {
			this.calcurateTotalPrice(element.getPrice());
		}
	}
	
	private void calcurateTotalPrice(Long price) {
		this.totalPrice = this.totalPrice == null ? price : this.totalPrice + price;
	}
}