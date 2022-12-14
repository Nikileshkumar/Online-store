package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer userId;
    private Double price;
}
