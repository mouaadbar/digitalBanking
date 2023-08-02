package com.example.benyamine;


;
import com.example.benyamine.entities.*;
import com.example.benyamine.entities.enums.AccountStatus;
import com.example.benyamine.entities.enums.OperationType;
import com.example.benyamine.exceptions.BalenceNotSefficientException;
import com.example.benyamine.repository.AccountOperatinsRepository;
import com.example.benyamine.repository.BankAccountRepository;
import com.example.benyamine.repository.CustomerRepository;
import com.example.benyamine.services.AccoutOperationsServiceImpl;
import com.example.benyamine.services.BanServiceImpl;
import com.example.benyamine.services.CustomerServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication

public class EmpManagApplication {


	public static void main(String[] args) {

		SpringApplication.run(EmpManagApplication.class, args);



		}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	/*@Bean
	CommandLineRunner commandLineRunner(BanServiceImpl banService,
										AccoutOperationsServiceImpl accoutOperationsService,
										CustomerServiceImpl customerService) {
		return args -> {
			Stream.of("mouad", "yassmine", "ikram").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerService.saveCustomer(customer);

				List<Customer> customers = customerService.listCustomers();
				customers.forEach(cs ->{
					banService.saveCurrentBankAccount(Math.random()* 90000, 9000, cs.getId());
					banService.createSavingAccount(Math.random()* 90000, 4.5, cs.getId());
					System.out.println("======================================"+name+"=====================================");
				});


					banService.allAccouts().forEach(bankAccount -> {
						try {
							System.out.println("=========================="+bankAccount.getId()+"=====================================");
							accoutOperationsService.credit(bankAccount.getId(), 1000+Math.random()*120000,"Credit Operation");
							accoutOperationsService.debit(bankAccount.getId(), 1000+Math.random()*120,"Credit Operation");
						} catch (AccountNotFoundException | BalenceNotSefficientException e) {
							e.printStackTrace();
						}
					});

				});


		};
	}*/
}