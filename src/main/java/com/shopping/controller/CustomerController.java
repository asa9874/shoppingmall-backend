package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.Response.CartItemResponseDto;
import com.shopping.dto.Response.OrderItemResponseDto;
import com.shopping.model.CartItem;
import com.shopping.model.OrderItem;
import com.shopping.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "구매자API", description = "/customer")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    //맴버의 구매한 상품 리스트 조회
    @GetMapping("/{memberId}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<List<OrderItemResponseDto>> getOrders(@PathVariable Long memberId) {
        List<OrderItem> orderItem = customerService.getOrders(memberId);
        List<OrderItemResponseDto> responseDto = orderItem.stream()
                .map(OrderItemResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDto);
    }

    // 상품 구입 조회
    @GetMapping("/{memberId}/orders/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<OrderItemResponseDto> getOrder(@PathVariable Long memberId, @PathVariable Long orderId) {
        OrderItem orderItem = customerService.getOrder(orderId);
        OrderItemResponseDto responseDto = OrderItemResponseDto.fromEntity(orderItem);
        return ResponseEntity.ok(responseDto);
    }

    // 특정 상품구입
    @PostMapping("/{memberId}/buy-product/{productId}/{quantity}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<OrderItemResponseDto> buyProduct(@PathVariable Long productId, @PathVariable Long memberId, @PathVariable int quantity) {
        OrderItem orderItem = customerService.buyProduct(memberId,productId,quantity);
        return ResponseEntity.ok(OrderItemResponseDto.fromEntity(orderItem));}

    // TODO: 장바구니 상품전체 구입
    @PostMapping("/{memberId}/buy-product")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<OrderItemResponseDto> buyCart() {
        return null;
    }

    // 장바구니 조회
    @GetMapping("/{memberId}/cart")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<List<CartItemResponseDto>> getCart(@PathVariable Long memberId) {
        List<CartItem> cartItems = customerService.getCart(memberId);
        List<CartItemResponseDto> responseDto = cartItems.stream()
                .map(CartItemResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDto);
    }

    // 장바구니 추가
    @PostMapping("/{memberId}/cart/{productId}/{quantity}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<CartItemResponseDto> addCart(@PathVariable Long productId, @PathVariable int quantity,
            @PathVariable Long memberId) {
        CartItem cartItem = customerService.addCart(memberId, productId, quantity);
        return ResponseEntity.ok(CartItemResponseDto.fromEntity(cartItem));
    }

    // 장바구니 상품제거
    @DeleteMapping("/{memberId}/cart/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartItemId, @PathVariable Long memberId) {
        customerService.deleteCart(memberId, cartItemId);
        return ResponseEntity.noContent().build();
    }


    //TODO: 장바구니 전체제거
    @DeleteMapping("/{memberId}/cart/clear")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> clearCart(@PathVariable Long memberId) {
        return ResponseEntity.noContent().build();
    }

}
