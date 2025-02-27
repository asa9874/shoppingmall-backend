package com.shopping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.model.CartItem;
import com.shopping.model.Customer;
import com.shopping.model.Member;
import com.shopping.model.OrderItem;
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

    //ORDER 
    public List<OrderItem> getOrders(Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getOrderedItems();
    }

    public OrderItem buyProduct(Long memberId, Long productId, int quantity) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getStock() < quantity){
            throw new RuntimeException("Not enough stock");
        }
        product.setStock(product.getStock() - quantity);
        if(product.getStock() == 0){
            productRepository.delete(product);
        }
        else{
            productRepository.save(product);
        }
        OrderItem orderItem = OrderItem.builder()
                .customer(customer)
                .product(product)
                .build();
        customer.getOrderedItems().add(orderItem);
        customerRepository.save(customer);
        return orderItem;
    }




    //CART
    public List<CartItem> getCart(Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getCartItems();
    }

    public CartItem addCart(Long memberId, Long productId,int quantity) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = CartItem.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .build();
        cartItemRepository.save(cartItem);
        customer.getCartItems().add(cartItem);
        customerRepository.save(customer);
        return cartItem;
    }

    public void deleteCart(Long memberId, Long cartItemId){
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        customer.getCartItems().remove(cartItem);
        customerRepository.save(customer);
        cartItemRepository.delete(cartItem);
    }
}
