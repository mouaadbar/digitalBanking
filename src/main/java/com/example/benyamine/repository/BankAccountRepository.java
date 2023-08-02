package com.example.benyamine.repository;

import com.example.benyamine.dtos.BankAccountDto;
import com.example.benyamine.entities.AccountOperations;
import com.example.benyamine.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    List<AccountOperations> findOperationsById(@Param("id") String id);
    List<BankAccount> findByCustomerId(@Param("customerId")Long customerId);

}
