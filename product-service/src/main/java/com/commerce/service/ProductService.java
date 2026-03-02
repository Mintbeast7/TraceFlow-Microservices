package com.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.commerce.dto.ProductDto;
import com.commerce.entity.ProductEntity;
import com.commerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ProductDto getProductById(Long id) {

		ProductEntity entity = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produt not found"));

		// Entity → DTO conversion
		return new ProductDto(entity.getProductId(), entity.getProductName(), entity.getPrice());
	}
}