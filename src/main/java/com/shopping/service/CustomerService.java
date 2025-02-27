package com.shopping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.model.CartItem;
import com.shopping.model.Customer;
import com.shopping.model.Member;
import com.shopping.model.Product;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.CustomerRepository;
import com.shopping.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public void registerCustomer(Member member) {
        Customer customer = new Customer();
        customer.setMember(member);
        customerRepository.save(customer);
    }

    public List<CartItem> getCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getCartProducts();
    }

    public CartItem addCart(Long customerId, Long productId,int quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = CartItem.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .build();
        cartItemRepository.save(cartItem);
        customer.getCartProducts().add(cartItem);
        customerRepository.save(customer);
        return cartItem;
    }

    public void deleteCart(Long customerId, Long cartItemId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        customer.getCartProducts().remove(cartItem);
        customerRepository.save(customer);
        cartItemRepository.delete(cartItem);
    }
}
