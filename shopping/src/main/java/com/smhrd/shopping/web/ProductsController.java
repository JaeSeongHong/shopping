package com.smhrd.shopping.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.shopping.service.ProductsService;
import com.smhrd.shopping.web.dto.ProductsSaveReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductsController {
private final ProductsService productsService;
	
	@PostMapping("/products/add")
	public Long addProduct(@RequestBody ProductsSaveReqDto requestDto) {
		log.info("post.. /product/add start!");
		return productsService.addProduct(requestDto);
	}
	
	@PutMapping("/products/update")
	public Long updateProduct(@RequestBody ProductsSaveReqDto requestDto) {
		log.info("put.. /product/update start!");
		return productsService.updateProduct(requestDto);
	}
	
	@DeleteMapping("/products/remove")
	public Long removeProducts(@RequestBody ProductsSaveReqDto requestDto) {
		return productsService.removeProduct(requestDto);
	}
}
