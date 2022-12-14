package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Integer paymentId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Double price;
    private Integer userId;
}
