package com.example.benyamine.entities;

import com.example.benyamine.entities.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 4, discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<AccountOperations> operations;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Customer customer;
}
