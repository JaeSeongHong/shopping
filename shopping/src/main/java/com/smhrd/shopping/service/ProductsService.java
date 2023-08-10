package com.smhrd.shopping.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.shopping.model.repository.ProductsJpaRepository;

import com.smhrd.shopping.model.entity.Products;
import com.smhrd.shopping.web.dto.ProductsResDto;
import com.smhrd.shopping.web.dto.ProductsSaveReqDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductsService {
	private final ProductsJpaRepository productsJpaRepository;
	
	@Transactional
	public Long addProduct(ProductsSaveReqDto requestDto) {
		return productsJpaRepository.save(requestDto.toEntity()).getId();
	}
	
	@Transactional
	public Long updateProduct(ProductsSaveReqDto requestDto) {
		Optional<Products> product = Optional.of(productsJpaRepository.findByName(requestDto.getName()));
		
		product.orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
		
		product.get().update(requestDto.getName(), requestDto.getPrice());
		
		return product.get().getId();
	}
	
	@Transactional
	public Long removeProduct(ProductsSaveReqDto requestDto) {
		Products product = productsJpaRepository.findById(requestDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
		
		Long productId = product.getId();
		
		productsJpaRepository.delete(product);
		
		return productId;
	}
	
	@Transactional(readOnly = true)
	public Products findById(Long id) {
		Products products = productsJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
		return products;
	}
	
	@Transactional(readOnly = true)
	public List<ProductsResDto> findAll() {
		return productsJpaRepository.findAll().stream().map(ProductsResDto::new).collect(Collectors.toList());
	}
}
