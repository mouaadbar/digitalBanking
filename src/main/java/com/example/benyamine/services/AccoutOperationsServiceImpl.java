package com.example.benyamine.services;

import com.example.benyamine.dtos.AccountHistoryDto;
import com.example.benyamine.dtos.AccountOperationsDto;
import com.example.benyamine.dtos.BankAccountDto;
import com.example.benyamine.entities.AccountOperations;
import com.example.benyamine.entities.BankAccount;
import com.example.benyamine.entities.enums.OperationType;
import com.example.benyamine.exceptions.BalenceNotSefficientException;
import com.example.benyamine.exceptions.BankAccountNotFound;
import com.example.benyamine.repository.AccountOperatinsRepository;
import com.example.benyamine.repository.BankAccountRepository;
import com.example.benyamine.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AccoutOperationsServiceImpl implements AccountOperationsService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperatinsRepository accountOperatinsRepository;
    private ModelMapper modelMapper;


    @Override
    public List<AccountOperationsDto> findAllOperationsofAccount(String accountId) throws BankAccountNotFound {

        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFound("bank account not found"));

        List<AccountOperations> operations = accountOperatinsRepository.findByBankAccountId(accountId);
        List<AccountOperationsDto> operationsDtos = operations.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return operationsDtos;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws AccountNotFoundException, BalenceNotSefficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException("account not found" ));

           if(bankAccount.getBalance()< amount){
              throw new BalenceNotSefficientException("Balance not sufficient");
           }else {
               AccountOperations accountOperations = new AccountOperations();
               accountOperations.setBankAccount(bankAccount);
               accountOperations.setOperationDate(new Date());
               accountOperations.setType(OperationType.DEBIT);
               accountOperations.setAmount(amount);
               accountOperations.setDescription("debit");
               double  solde = bankAccount.getBalance() -amount;

               bankAccount.setBalance(solde);
               accountOperatinsRepository.save(accountOperations);
               bankAccountRepository.save(bankAccount);
           }

    }

    @Override
    public void credit(String accountId, double amount, String description) throws  AccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException("account not found" ));


            AccountOperations accountOperations = new AccountOperations();
            accountOperations.setBankAccount(bankAccount);
            accountOperations.setOperationDate(new Date());
            accountOperations.setType(OperationType.CREDIT);
            accountOperations.setAmount(amount);
            accountOperations.setDescription("credit");
            double  credit = bankAccount.getBalance() + amount;

            bankAccount.setBalance(credit);
            accountOperatinsRepository.save(accountOperations);
            bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfert(String accountIdSource, String accountIDest, double amount, String description) throws BalenceNotSefficientException, AccountNotFoundException {
          debit(accountIdSource, amount, "transfert to" + accountIDest);
          credit(accountIDest, amount, "tranfert from " + accountIdSource);

    }



    public AccountOperations mapDtoToEntity(AccountOperationsDto accountOperationsDto){
        AccountOperations accountOperations = modelMapper.map(accountOperationsDto, AccountOperations.class);
        return accountOperations;

    }


    public AccountOperationsDto mapEntityToDto(AccountOperations accountOperations){
        AccountOperationsDto accountOperationsDto = modelMapper.map(accountOperations, AccountOperationsDto.class);
        return accountOperationsDto;

    }





    public AccountHistoryDto getHistoryAccount(String accountId, int page, int size) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new com.example.benyamine.exceptions.AccountNotFoundException("account not found"));

        Pageable peageable = PageRequest.of(page, size);
        Page<AccountOperations> accountOperationsPage = accountOperatinsRepository.findByBankAccountIdOrderByOperationDateDesc(peageable, accountId);
        List<AccountOperations> accountOperations = accountOperationsPage.getContent();
        List<AccountOperationsDto> accountOperationsDto = accountOperations.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();

        accountHistoryDto.setAccountId(bankAccount.getId());
        accountHistoryDto.setAccountOperationsDto(accountOperationsDto);
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(accountOperationsPage.getTotalPages());
        accountHistoryDto.setTotalElements(accountOperationsPage.getTotalElements());
        accountHistoryDto.setBalance(bankAccount.getBalance());

      return accountHistoryDto;




    }
}
