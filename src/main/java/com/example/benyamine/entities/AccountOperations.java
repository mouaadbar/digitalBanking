package com.example.benyamine.entities;

import com.example.benyamine.entities.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private BankAccount bankAccount;
    @Enumerated(EnumType.STRING)
    private OperationType type;

    private String description;
}
