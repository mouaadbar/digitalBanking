package com.example.benyamine.dtos;

import lombok.Data;

@Data
public class CreditDto {
    String accountId;
    double amount;
    String description;
}
