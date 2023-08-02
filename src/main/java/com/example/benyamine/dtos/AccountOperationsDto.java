package com.example.benyamine.dtos;

import com.example.benyamine.entities.BankAccount;
import com.example.benyamine.entities.enums.OperationType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class AccountOperationsDto {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}

