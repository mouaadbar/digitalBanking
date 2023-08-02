package com.example.benyamine.controller;

import com.example.benyamine.dtos.BankAccountDto;

import com.example.benyamine.dtos.CurrentAccountDto;
import com.example.benyamine.dtos.SavingAccountDto;
import com.example.benyamine.exceptions.RessourceNonFoundException;
import com.example.benyamine.services.BanServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/accounts")
public class BankAccoutController {

    @Autowired
    private BanServiceImpl banService;

    @GetMapping("customers/{customerId}")
    public ResponseEntity<List<BankAccountDto>> getAllAccounts(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<List<BankAccountDto>>(banService.allAccouts(customerId), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountDto> getBankAccount(@PathVariable("accountId")String accountId) throws AccountNotFoundException {
        return new ResponseEntity<BankAccountDto>(banService.getBankAccount(accountId), HttpStatus.OK);
    }


    @PostMapping("/currentAccount")
    public ResponseEntity<CurrentAccountDto> createCurrentAccount(@RequestParam double balance,
                                                                  @RequestParam double overDraft,
                                                                  @RequestParam Long customerId) throws RessourceNonFoundException {
        return new ResponseEntity<CurrentAccountDto>(banService.saveCurrentBankAccount(balance,overDraft,customerId),HttpStatus.CREATED);
    }


    @PostMapping("/savingAccount")
    public ResponseEntity<SavingAccountDto> createSavingAccount(@RequestParam double balance,
                                                                @RequestParam double intersetRate,
                                                                @RequestParam Long customerId) throws RessourceNonFoundException {
        return new ResponseEntity<SavingAccountDto>(banService.createSavingAccount(balance,intersetRate,customerId),HttpStatus.CREATED);
    }


}
