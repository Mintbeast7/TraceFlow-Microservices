package com.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.commerce.dto.OrderRequest;
import com.commerce.dto.ProductResponse;
import com.commerce.entity.OrderEntity;
import com.commerce.productclient.ProductClient;
import com.commerce.repository.OrderRepository;

import feign.FeignException.FeignClientException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductClient productClient;

	public void placeOrder(OrderRequest request) {

		ProductResponse productResponse;

		try {
			// 1️. Call Product Service using Feign
			productResponse = productClient.getProductById(request.getProductId());
		} catch (FeignClientException fce) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
		}

		// 2️. Create Order Entity
		OrderEntity order = new OrderEntity();
		order.setUserId(request.getUserId());
		order.setProductId(request.getProductId());
		order.setQuantity(request.getQuantity());
		order.setPaymentMode(request.getPaymentMode());

		// 3️. Set internal values
		order.setPrice(productResponse.getPrice());
		order.setOrderStatus("CREATED");

		// 4️. Save to DB
		orderRepository.save(order);
	}
}