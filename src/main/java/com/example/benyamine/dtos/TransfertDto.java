package com.example.benyamine.dtos;

import lombok.Data;

@Data
public class TransfertDto {
    String accountIdSource;
    String accountIDest;
    double amount;
    String description;
}
