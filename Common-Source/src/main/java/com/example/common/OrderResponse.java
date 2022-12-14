package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private Integer paymentId;
    private Integer productId;
    private Integer quantity;
    private Double price;
    private String status;
}
