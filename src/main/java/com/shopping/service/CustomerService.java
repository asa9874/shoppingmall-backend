package com.shopping.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.model.CartItem;
import com.shopping.model.Customer;
import com.shopping.model.Member;
import com.shopping.repository.CustomerRepository;
import com.shopping.util.SecurityUtil;

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


    public List<CartItem> getCart() {
        return null;
    }
}
