package com.example.order.controller;

import com.example.common.OrderRequest;
import com.example.common.OrderResponse;
import com.example.order.service.OrderService;
import com.example.order.util.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class KafkaController {

   @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public OrderResponse addOrder(@RequestBody OrderRequest request){
        //kafkaTemplate.send(KafkaConstants.TOPIC, product);
        return orderService.add(request);
    }
}
