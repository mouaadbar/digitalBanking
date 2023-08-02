package com.example.benyamine.dtos;

import lombok.Data;

@Data
public class DebitDto {
    String accountId;
    double amount;
    String description;
}
