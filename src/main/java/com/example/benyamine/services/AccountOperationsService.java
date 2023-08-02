package com.example.benyamine.services;

import com.example.benyamine.dtos.AccountHistoryDto;
import com.example.benyamine.dtos.AccountOperationsDto;
import com.example.benyamine.dtos.BankAccountDto;
import com.example.benyamine.entities.BankAccount;
import com.example.benyamine.exceptions.BalenceNotSefficientException;
import com.example.benyamine.exceptions.BankAccountNotFound;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountOperationsService {
    List<AccountOperationsDto> findAllOperationsofAccount(String accountId) throws BankAccountNotFound;
    public void debit(String accountId, double amount, String description) throws AccountNotFoundException, BalenceNotSefficientException;
    public void credit(String accountId, double amount, String description) throws BalenceNotSefficientException, AccountNotFoundException;
    public void transfert(String accountIdSource,String accountIDest, double amount, String description) throws BalenceNotSefficientException, AccountNotFoundException;
    public AccountHistoryDto getHistoryAccount(String accountId, int page, int size);
}
