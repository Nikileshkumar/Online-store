package com.example.order.service;

import com.example.common.OrderRequest;
import com.example.common.OrderResponse;
import com.example.order.entity.Order;
import com.example.order.repository.OrderRepository;
import com.example.order.util.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private KafkaTemplate<String, OrderRequest> kafkaTemplate;
    @Autowired
    private OrderRepository repository;

    public OrderResponse add(OrderRequest request) {
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setUserId(request.getUserId());
        order.setPrice(request.getPrice());
        order= repository.save(order);
        request.setOrderId(order.getOrderId());
        kafkaTemplate.send(KafkaConstants.TOPIC, request);
        return null;
    }

    @KafkaListener(topics = "order-response", groupId = KafkaConstants.GROUP_ID)
    public void listener(OrderResponse response) {
        System.out.println(response.getStatus());
    }
}
