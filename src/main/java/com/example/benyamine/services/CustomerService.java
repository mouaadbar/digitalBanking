package com.example.benyamine.services;

import com.example.benyamine.dtos.CustomerDto;
import com.example.benyamine.entities.Customer;
import com.example.benyamine.exceptions.RessourceNonFoundException;

import java.util.List;

public interface CustomerService {

    public CustomerDto saveCustomer(CustomerDto customerDto);

    List<CustomerDto> listCustomers();

    List<CustomerDto>findByKeyWord(String keyWord);

    CustomerDto updateCustomer(CustomerDto customerDto, Long id) throws RessourceNonFoundException;

    CustomerDto findById(Long customerid) throws RessourceNonFoundException;

    void deleteCustomer(Long id) throws RessourceNonFoundException;
}

