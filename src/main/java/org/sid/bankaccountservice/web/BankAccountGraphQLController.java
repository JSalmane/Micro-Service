package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.entities.Customer;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.repositories.CustomerRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;

    @QueryMapping
    private List<BankAccount> accountsList(){
        return bankAccountRepository.findAll();
    }
    @QueryMapping
    private BankAccount bankAccountById(@Argument String id){
        return bankAccountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(String.format("Account %s not found", id)));
    }
    @MutationMapping
    private BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount){
        return accountService.addAccount(bankAccount);
    }
    @MutationMapping
    private BankAccountResponseDTO updateAccount(@Argument String id, @Argument BankAccountRequestDTO bankAccount){
        return accountService.updateAccount(id, bankAccount);
    }
    @MutationMapping
    private Boolean deleteAccount(@Argument String id){
        bankAccountRepository.deleteById(id);
        return true;
    }
    @QueryMapping
    private List<Customer> customersList(){
        return customerRepository.findAll();
    }
}

/*
record BankAccountDTO (Double balance, String type, String currency){}
*/