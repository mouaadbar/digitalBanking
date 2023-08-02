package com.example.benyamine.services;

import com.example.benyamine.dtos.BankAccountDto;
import com.example.benyamine.dtos.CurrentAccountDto;
import com.example.benyamine.dtos.SavingAccountDto;
import com.example.benyamine.entities.BankAccount;
import com.example.benyamine.exceptions.RessourceNonFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface BankService {
    public List<BankAccountDto> allAccouts(Long customerId);


    public CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws RessourceNonFoundException;


    public SavingAccountDto createSavingAccount(double balance, double intersetRate, Long customerId) throws RessourceNonFoundException;


    public BankAccountDto getBankAccount (String accountId) throws AccountNotFoundException;

    public BankAccount updateBankAccount(BankAccount bankAccount, String id );

    public void deleteBankAcount(String id);
}
