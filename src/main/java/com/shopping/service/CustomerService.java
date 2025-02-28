package com.shopping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shopping.model.CartItem;
import com.shopping.model.Customer;
import com.shopping.model.Member;
import com.shopping.model.OrderItem;
import com.shopping.model.Product;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.CustomerRepository;
import com.shopping.repository.OrderItemRepository;
import com.shopping.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    public void registerCustomer(Member member) {
        Customer customer = new Customer();
        customer.setMember(member);
        customerRepository.save(customer);
    }

    // ORDER
    public List<OrderItem> getOrders(Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getOrderedItems();
    }

    public OrderItem getOrder(Long orderId) {
        OrderItem orderItem = orderItemRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderItem;
    }

    public OrderItem buyProduct(Long memberId, Long productId, int quantity) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        OrderItem orderItem = OrderItem.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .orderDate(LocalDateTime.now())
                .build();
        customer.getOrderedItems().add(orderItem);
        customerRepository.save(customer);
        return orderItem;
    }

    public List<OrderItem> buyCart(Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<CartItem> cartItems = customer.getCartItems();
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> buyProduct(memberId, cartItem.getProduct().getId(), cartItem.getQuantity()))
                .collect(Collectors.toList());
        return orderItems;
    }

    // CART
    public List<CartItem> getCart(Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getCartItems();
    }

    public CartItem addCart(Long memberId, Long productId, int quantity) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }
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

    @Transactional
    public void deleteCart(Long memberId, Long cartItemId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        customer.getCartItems().remove(cartItem);
    }

    @Transactional
    public void clearCart(Long memberId) {
        Customer customer = customerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.getCartItems().clear(); 
    }
}
