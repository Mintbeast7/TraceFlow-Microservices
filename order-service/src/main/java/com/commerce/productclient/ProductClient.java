package com.commerce.productclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.commerce.dto.ProductResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "product-service") // configuration = TracingFeignConfig.class)
public interface ProductClient {

	@CircuitBreaker(name = "ProductService", fallbackMethod = "productFallback")
	@Retry(name = "ProductServiceRetry")
	@GetMapping("/products/{id}")
	ProductResponse getProductById(@PathVariable("id") Long id);

	// fallback method
	default ProductResponse productFallback(Long id, Exception e) {

		ProductResponse response = new ProductResponse();
		response.setProductId(id);
		response.setProductName("Product service is currently unavailable. Please try again later.");
		response.setPrice(0.0);

		return response;
	}
}

//This code lets Order Service call Product Service’s /products/{id} API and receive product data as a ProductResponse object.