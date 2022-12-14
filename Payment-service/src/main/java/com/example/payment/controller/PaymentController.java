package com.example.payment.controller;

import com.example.common.PaymentRequest;
import com.example.common.PaymentResponse;
import com.example.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/add")
    public PaymentResponse addPayment(@RequestBody PaymentRequest request){
     return paymentService.addPayment(request);
    }

    @PostMapping("/cancel")
    public PaymentResponse cancelPayment(@RequestBody PaymentRequest request){
        return paymentService.cancelPayment(request);
    }
}
