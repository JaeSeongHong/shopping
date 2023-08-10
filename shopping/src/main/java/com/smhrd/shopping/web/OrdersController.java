package com.smhrd.shopping.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.shopping.service.OrdersService;
import com.smhrd.shopping.web.dto.OrderDetailsReqDto;
import com.smhrd.shopping.web.dto.OrderDetailsResDto;
import com.smhrd.shopping.web.dto.OrderDetailsSaveReqDto;
import com.smhrd.shopping.web.dto.OrdersReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrdersController {
	
	private final OrdersService ordersService;
	
	@PostMapping("/order/add")
	public Long order(@RequestBody List<OrderDetailsSaveReqDto> requestDto) {
		log.info("view to controller with order");
		return ordersService.order(requestDto);
	}
	
	@PutMapping("/order/update")
	public Long updateOrder(@RequestBody OrderDetailsReqDto requestDto) {
		log.info("view to controller with updateOrder");
		return ordersService.updateOrder(requestDto);
	}
	
	@DeleteMapping("/order/delete")
	public Long removeOrder(@RequestBody OrderDetailsReqDto requestDto) {
		log.info("view to controller with removeOrder");
		return ordersService.removeOrder(requestDto);
	}
	
	@GetMapping("/order/detail/get/{id}")
	public List<OrderDetailsResDto> ById(@PathVariable(name="id") Long id) {
		return ordersService.findByOrders(OrdersReqDto.builder().id(id).build());
	}
}
