package com.smhrd.shopping.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smhrd.shopping.model.entity.Products;

public interface ProductsJpaRepository extends JpaRepository<Products, Long> {
	@Query("select p from products p where p.name = :name")
	public Products findByName(@Param("name")String name);
}
