package com.example.benyamine.services;

import com.example.benyamine.dtos.BankAccountDto;
import com.example.benyamine.dtos.CurrentAccountDto;
import com.example.benyamine.dtos.SavingAccountDto;
import com.example.benyamine.entities.BankAccount;
import com.example.benyamine.entities.CurrentAccount;
import com.example.benyamine.entities.Customer;
import com.example.benyamine.entities.SavingAccount;
import com.example.benyamine.entities.enums.AccountStatus;
import com.example.benyamine.exceptions.RessourceNonFoundException;
import com.example.benyamine.repository.BankAccountRepository;
import com.example.benyamine.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import javax.security.auth.login.AccountNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BanServiceImpl implements BankService{

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;


    public BanServiceImpl(BankAccountRepository bankAccountRepository,CustomerRepository customerRepository,ModelMapper modelMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BankAccountDto> allAccouts(Long customerId) {
        log.info("find all accounts");

        List<BankAccount> allAccounts = bankAccountRepository.findByCustomerId(customerId);
        List<BankAccountDto>allAccountsDto = allAccounts.stream().map(bankAccount -> {

            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return mapEntityToDto(savingAccount);
            }else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return mapEntityToDto(currentAccount);
            }
        }).collect(Collectors.toList());
        return allAccountsDto;
    }

    @Override
    public CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws RessourceNonFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new RessourceNonFoundException("Customer not found"));


        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedCurrentBankAccount = bankAccountRepository.save(currentAccount);

        CurrentAccountDto savedCurrentBankAccountDto = mapEntityToDto(savedCurrentBankAccount);


        return savedCurrentBankAccountDto;
    }

    @Override
    public SavingAccountDto createSavingAccount(double balance, double intersetRate, Long customerId) throws RessourceNonFoundException {
        System.out.println("----------------------------------------------");
        Customer cust = customerRepository.findById(customerId)
                .orElseThrow(()->new RessourceNonFoundException("customer not foundnot found"));

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCustomer(cust);
        savingAccount.setIntersetRate(intersetRate);
        savingAccount.setBalance(balance);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCreatedAt(new Date());
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);

        SavingAccountDto savedsavingBankAccountDto = mapEntityToDto(savedSavingAccount);
        return savedsavingBankAccountDto;
    }


    @Override
    public BankAccountDto getBankAccount(String accountId) throws AccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException("account not found" ));

        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return mapEntityToDto(savingAccount);
        }else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return mapEntityToDto(currentAccount);
        }



    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccount, String id) {
        return null;
    }

    @Override
    public void deleteBankAcount(String id) {

    }






    public SavingAccount mapDtoToEntity(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount = modelMapper.map(savingAccountDto, SavingAccount.class);
        return savingAccount;
    }

    public SavingAccountDto mapEntityToDto(SavingAccount savingAccount){

        SavingAccountDto savingAccountDto = modelMapper.map(savingAccount, SavingAccountDto.class);
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
        return savingAccountDto;


    }



    public CurrentAccount mapDtoToEntity(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount = modelMapper.map(currentAccountDto, CurrentAccount.class);
        return currentAccount;
    }

    public CurrentAccountDto mapEntityToDto(CurrentAccount currentAccount){

        CurrentAccountDto currentAccountDto = modelMapper.map(currentAccount, CurrentAccountDto.class);
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDto;


    }



    public BankAccount mapDtoToEntity(BankAccountDto bankAccountDto){

        BankAccount bankAccount = modelMapper.map(bankAccountDto, BankAccount.class);
        return bankAccount;
    }

    public BankAccountDto mapEntityToDto(BankAccount bankAccount){

        BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setType(bankAccount.getClass().getSimpleName());
        return modelMapper.map(bankAccount, BankAccountDto.class);
    }




}
