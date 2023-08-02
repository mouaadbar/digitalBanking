package com.example.benyamine.controller;

import com.example.benyamine.dtos.*;
import com.example.benyamine.exceptions.BalenceNotSefficientException;
import com.example.benyamine.exceptions.BankAccountNotFound;
import com.example.benyamine.services.AccoutOperationsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/operations")
public class AccountOperationsController {
    @Autowired
    private AccoutOperationsServiceImpl accoutOperationsService;

    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountOperationsDto>> getAllAccountOperations(@PathVariable("accountId")String accountId) throws BankAccountNotFound {
        return new ResponseEntity<List<AccountOperationsDto>>(accoutOperationsService.findAllOperationsofAccount(accountId), HttpStatus.OK);
    }


    @GetMapping("/history/{accountId}/pageOperations")
    public ResponseEntity<AccountHistoryDto>getHistory(      @PathVariable String accountId,
                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "5") int size){
        return new ResponseEntity<AccountHistoryDto>(accoutOperationsService.getHistoryAccount(accountId,page, size),HttpStatus.OK);
    }


    @PostMapping("/debit")
    public ResponseEntity<?> debiterCompte(@RequestBody DebitDto debitDto) throws BalenceNotSefficientException, AccountNotFoundException {
        accoutOperationsService.debit(debitDto.getAccountId(),debitDto.getAmount(), debitDto.getDescription());
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/credit")
    public ResponseEntity<?> crediterCompte(@RequestBody CreditDto creditDto) throws BalenceNotSefficientException, AccountNotFoundException {
        accoutOperationsService.credit(creditDto.getAccountId(),creditDto.getAmount(), creditDto.getDescription());
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/transfert")
    public ResponseEntity<?> transfertCompte(@RequestBody TransfertDto transfertDto) throws BalenceNotSefficientException, AccountNotFoundException {
        accoutOperationsService.transfert(transfertDto.getAccountIdSource(),transfertDto.getAccountIDest(),transfertDto.getAmount(), transfertDto.getDescription());
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
