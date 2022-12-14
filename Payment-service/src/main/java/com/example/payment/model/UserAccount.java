package com.example.payment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserAccount {
    @Id
    private Integer userId;
    private Double availableAmount;
}
