package com.example.benyamine.controller;

import com.example.benyamine.dtos.CustomerDto;
import com.example.benyamine.entities.Customer;
import com.example.benyamine.exceptions.RessourceNonFoundException;
import com.example.benyamine.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>>getAllCustomers(){
        return new ResponseEntity<List<CustomerDto>>(customerService.listCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> findCustomerById(@PathVariable("customerId") Long customerId) throws RessourceNonFoundException {
        CustomerDto custDto =customerService.findById(customerId);
        return new ResponseEntity<CustomerDto>(custDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDto>> findByKeyWord(@RequestParam(name="keyword", defaultValue = "") String keyword){
        return new ResponseEntity<List<CustomerDto>>(customerService.findByKeyWord("%"+keyword+"%"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        CustomerDto custDto =customerService.saveCustomer(customerDto);
        return new ResponseEntity<CustomerDto>(custDto, HttpStatus.CREATED);
    }
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("customerId")Long customerId,
                                                      @RequestBody CustomerDto customerDto) throws RessourceNonFoundException {
        return new ResponseEntity<CustomerDto>(customerService.updateCustomer(customerDto, customerId),HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId")Long customerId) throws RessourceNonFoundException {
            customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

}
