package com.smhrd.shopping.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.shopping.model.entity.Orders;

public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {

}
