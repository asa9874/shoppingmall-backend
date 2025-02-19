package com.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
