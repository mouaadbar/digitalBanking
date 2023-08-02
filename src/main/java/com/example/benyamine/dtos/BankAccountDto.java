package com.example.benyamine.dtos;

import com.example.benyamine.entities.AccountOperations;
import com.example.benyamine.entities.Customer;
import com.example.benyamine.entities.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {


    private String id;
    private String type;


}
