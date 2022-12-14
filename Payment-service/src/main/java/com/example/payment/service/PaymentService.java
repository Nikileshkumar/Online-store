package com.example.payment.service;

import com.example.common.PaymentRequest;
import com.example.common.PaymentResponse;
import com.example.payment.model.Payment;
import com.example.payment.model.UserAccount;
import com.example.payment.repo.PaymentRepository;
import com.example.payment.repo.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserAccountRepository userAccountRepository;
    public PaymentResponse addPayment(PaymentRequest request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.getUserId());
        Payment payment = new Payment();
        payment.setAmount(request.getPrice());
        payment.setOrderId(request.getOrderId());
        if(userAccount.getAvailableAmount()>= request.getPrice()){
            userAccount.setAvailableAmount(userAccount.getAvailableAmount() - request.getPrice());
            userAccountRepository.save(userAccount);
            payment.setStatus("SUCCESS");
            payment = paymentRepository.save(payment);
            return new PaymentResponse(payment.getPaymentId(), request.getOrderId(), request.getProductId(), request.getQuantity(), request.getUserId(), request.getPrice(), "SUCCESS");
        }
    return new PaymentResponse(null, request.getOrderId(), request.getProductId(), request.getQuantity(), request.getUserId(), request.getPrice(), "FAILURE");
    }

    public PaymentResponse cancelPayment(PaymentRequest request) {
        UserAccount userAccount = userAccountRepository.getReferenceById(request.getUserId());
        paymentRepository.deleteById(request.getPaymentId());
        userAccount.setAvailableAmount(userAccount.getAvailableAmount() + request.getPrice());
        userAccountRepository.save(userAccount);
        return new PaymentResponse(request.getPaymentId(), request.getOrderId(), request.getProductId(), request.getQuantity(), request.getUserId(), request.getPrice(), "SUCCESS");
    }
}
