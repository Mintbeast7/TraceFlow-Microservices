package com.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

}
