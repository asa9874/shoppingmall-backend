package com.shopping.service;


import org.springframework.stereotype.Service;

import com.shopping.model.Customer;
import com.shopping.model.Member;
import com.shopping.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void registerCustomer(Member member){
        Customer customer = new Customer();
        customer.setMember(member);
        customerRepository.save(customer);
    }
}
