package com.example.benyamine.services;

import com.example.benyamine.dtos.CustomerDto;
import com.example.benyamine.entities.Customer;
import com.example.benyamine.exceptions.RessourceNonFoundException;
import com.example.benyamine.repository.AccountOperatinsRepository;
import com.example.benyamine.repository.BankAccountRepository;
import com.example.benyamine.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CustomerServiceImpl  implements CustomerService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperatinsRepository accountOperatinsRepository;
    private ModelMapper modelMapper;




    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer cust = mapDtoToEntity(customerDto);
        Customer customer1 = customerRepository.save(cust);
        CustomerDto savedCustDto = mapEntityToDto(customer1);
        return savedCustDto;
    }

    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers =customerRepository.findAll();
        List<CustomerDto> customerDtos =customers.stream().map(this::mapEntityToDto).collect(Collectors.toList());

        return customerDtos;
    }

    @Override
    public List<CustomerDto> findByKeyWord(String keyWord) {
        List<Customer> customers = customerRepository.findByNameOREmailContains(keyWord);
        List<CustomerDto> customerDtos = customers.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return customerDtos;
    }


    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) throws RessourceNonFoundException {
        Customer cust = customerRepository.findById(customerId)
                .orElseThrow(()->new RessourceNonFoundException("custmer not found"));

      CustomerDto custDto1 = mapEntityToDto(cust);

      custDto1.setName(customerDto.getName());
      custDto1.setEmail(customerDto.getEmail());
      Customer savedCust = mapDtoToEntity(custDto1);
      CustomerDto savedCustDto = mapEntityToDto(customerRepository.save(savedCust));
        return savedCustDto;
    }

    @Override
    public CustomerDto findById(Long customerId) throws RessourceNonFoundException {
        Customer cust = customerRepository.findById(customerId)
                .orElseThrow(()->new RessourceNonFoundException("custmer not found"));
        CustomerDto custDto = mapEntityToDto(cust);
        return  custDto;
    }

    @Override
    public void deleteCustomer(Long customerId) throws RessourceNonFoundException {
        Customer cust = customerRepository.findById(customerId)
                .orElseThrow(()->new RessourceNonFoundException("custmer not found"));

        customerRepository.deleteById(customerId);

    }


    public Customer mapDtoToEntity(CustomerDto customerDto){
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }

    public CustomerDto mapEntityToDto(Customer customer){
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }
}
