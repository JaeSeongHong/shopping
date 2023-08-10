package com.smhrd.shopping.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.shopping.model.entity.OrderDetails;
import com.smhrd.shopping.model.entity.Orders;
import com.smhrd.shopping.model.entity.Products;
import com.smhrd.shopping.model.entity.Users;
import com.smhrd.shopping.model.repository.OrdersJpaRepository;
import com.smhrd.shopping.web.dto.OrderDetailsReqDto;
import com.smhrd.shopping.web.dto.OrderDetailsResDto;
import com.smhrd.shopping.web.dto.OrderDetailsSaveReqDto;
import com.smhrd.shopping.web.dto.OrdersReqDto;
import com.smhrd.shopping.web.dto.OrdersResDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrdersService {
	
	private final ProductsService productsService; /* 상품 서비스 */
//	private final SettlesService settlesService; /* 결제 서비스 */
	private final OrdersJpaRepository ordersJpaRepository; /* 주문 리파지토리 */
	
	/**
	 * method	주문
	 */
	@Transactional
	public Long order(List<OrderDetailsSaveReqDto> requestDto) {
		Long code = 200L;
		
		this.saveOrder(requestDto); /* 주문정보 저장 */
//		code = SettlesService.xxxxxxx /* 결제 로직 구현(PG API 구현 예정) */
		
		if (!code.equals(200L)) {
			throw new IllegalArgumentException("주문에 실패하였습니다. 관리자에게 문의하시기 바랍니다.");
		}
		
		return code;
	}
	
	/**
	 * method	주문정보 삭제
	 */
	@Transactional
	public Long removeOrder(OrderDetailsReqDto requestDto) {
		Long orderId = -1L;
		
		orderId = requestDto.getOrderId();
		
		if (orderId < 0L) {
			throw new IllegalArgumentException("해당 주문정보가 없습니다.");
		}
		
		Orders orders = ordersJpaRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문정보가 없습니다."));
		ordersJpaRepository.delete(orders);
		
		return orderId;
	}
	
	/**
	 * method	주문정보 변경
	 */
	@Transactional
	public Long updateOrder(OrderDetailsReqDto requestDto) {
//		List<OrderDetailsSaveReqDto> orderDetailsListSaveReqDto = new ArrayList<OrderDetailsSaveReqDto>();
		
//		for (OrderDetailsReqDto element : requestDto) {
//			OrderDetailsSaveReqDto orderDetailsSaveReqDto = OrderDetailsSaveReqDto.builder().productId(element.getProductId()).count(element.getCount()).price(element.getPrice()).build();
//			orderDetailsListSaveReqDto.add(orderDetailsSaveReqDto);
//		}
		
//		this.removeOrder(requestDto); // 주문 취소
		
//		return this.order(orderDetailsListSaveReqDto); //주문 등록
		Orders orders = ordersJpaRepository.findById(requestDto.getOrderId()).orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
		List<OrderDetails> orderDetails = orders.getOrderDetails();
		
		List<OrderDetailsSaveReqDto> orderDetailsSaveReqDto = new ArrayList<OrderDetailsSaveReqDto>();
		for (OrderDetails orderDetail : orderDetails) {
			if (orderDetail.getId().equals(requestDto.getOrderDetailId())) {
				continue;
			}
			OrderDetailsSaveReqDto orderDetailSaveReqDto = OrderDetailsSaveReqDto.builder().productId(orderDetail.getProducts().getId()).count(orderDetail.getCount()).price(orderDetail.getPrice()).build();
			orderDetailsSaveReqDto.add(orderDetailSaveReqDto);
		}
		
		
		this.removeOrder(requestDto); // 주문정보 삭제
		
		return this.saveOrder(orderDetailsSaveReqDto); // 주문정보 등록
	}
	
	/**
	 * method	주문정보 등록
	 */
	@Transactional
	public Long saveOrder(List<OrderDetailsSaveReqDto> requestDto) {
		Users users = Users.builder().build();
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		
		for (OrderDetailsSaveReqDto element :requestDto) {
			Products products = productsService.findById(element.getProductId());
			OrderDetails orderDetails = OrderDetails.builder().products(products).count(element.getCount()).build();
			orderDetailsList.add(orderDetails);
		}
		
		return ordersJpaRepository.save(Orders.builder().users(users).orderDetails(orderDetailsList).build()).getId();
	}
	
	/**
	 * method	주문정보 전체 조회
	 */
	@Transactional(readOnly = true)
	public List<OrdersResDto> findAllOrders() {
		List<Orders> orders = ordersJpaRepository.findAll();
		
		if (orders == null || orders.isEmpty()) {
			throw new IllegalArgumentException("조회된 주문 정보가 없습니다");
		}
		
		List<OrdersResDto> result = orders.stream().map(obj -> new OrdersResDto(obj)).collect(Collectors.toList());
		
		return result;
	}

	/**
	 * method	주문정보 단위 조회
	 */
	@Transactional(readOnly = true)
	public List<OrderDetailsResDto> findByOrders(OrdersReqDto request) {
		Orders orders = ordersJpaRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
		List<OrderDetailsResDto> results = orders.getOrderDetails().stream().map((obj) -> new OrderDetailsResDto(obj)).collect(Collectors.toList());
		
		return results;
	}
	
}
