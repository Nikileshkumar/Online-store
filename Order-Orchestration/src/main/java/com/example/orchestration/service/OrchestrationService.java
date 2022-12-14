package com.example.orchestration.service;

import com.example.common.*;
import com.example.orchestration.util.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrchestrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, OrderResponse> kafkaTemplate;

    @KafkaListener(topics = KafkaConstants.TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void listener(OrderRequest request) {
        System.out.println("***Msg recieved from Kafka Topic ::" );
        OrderResponse orderResponse = new OrderResponse(request.getOrderId(), null, request.getProductId(), request.getQuantity(), request.getPrice(), null);
        PaymentRequest paymentRequest = new PaymentRequest(null, request.getOrderId(), request.getProductId(), request.getQuantity(), request.getPrice(), request.getUserId());
        PaymentResponse paymentResponse = restTemplate.postForObject("http://localhost:7004/payment/add",paymentRequest, PaymentResponse.class);
        if(paymentResponse.getStatus().equals("SUCCESS")){
            InventoryRequest inventoryRequest = new InventoryRequest(request.getProductId(), request.getQuantity());
            InventoryResponse inventoryResponse = restTemplate.postForObject("http://localhost:7003/product/deduct",inventoryRequest, InventoryResponse.class);
            if(!inventoryResponse.getStatus().equals("SUCCESS")){
                paymentRequest.setPaymentId(paymentResponse.getPaymentId());
                PaymentResponse paymentReversal = restTemplate.postForObject("http://localhost:7004/payment/cancel",paymentRequest, PaymentResponse.class);
                orderResponse.setStatus("FAILURE");
                kafkaTemplate.send("order-response", orderResponse);
                return ;
            }
            orderResponse.setPaymentId(paymentResponse.getPaymentId());
            orderResponse.setStatus("SUCCESS");
        }
        kafkaTemplate.send("order-response", orderResponse);
    }
}
