package com.example.order_service.service;

import com.example.order_service.domain.OrderEntity;
import com.example.order_service.dto.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
