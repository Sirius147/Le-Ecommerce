package com.example.order_service.repository;

import com.example.order_service.domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String productId);

    Iterable<OrderEntity> findByUserId(String userId);
}
