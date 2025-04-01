package com.shopping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.common.ApiResponse;
import com.shopping.dto.Request.CartRequestDto;
import com.shopping.dto.Request.OrderRequestDto;
import com.shopping.dto.Response.CartItemResponseDto;
import com.shopping.dto.Response.OrderItemResponseDto;
import com.shopping.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "구매자API", description = "/customer")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "맴버의 구매한 상품 리스트 조회", description = "맴버의 구매한 상품 리스트를 조회합니다.(Cusomer 본인 권한)")
    @GetMapping("/{memberId}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<List<OrderItemResponseDto>>> getOrders(@PathVariable Long memberId) {
        List<OrderItemResponseDto> responseDto = customerService.getOrders(memberId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "상품 구입 조회", description = "특정 상품 구입을 조회합니다.(Cusomer 본인 권한)")
    @GetMapping("/{memberId}/orders/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<OrderItemResponseDto>> getOrder(@PathVariable Long memberId, 
                                                                       @PathVariable Long orderId) {
        OrderItemResponseDto responseDto = customerService.getOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "특정 상품 구입", description = "특정 상품을 구입합니다.(Cusomer 본인 권한)")
    @PostMapping("/{memberId}/orders/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<OrderItemResponseDto>> buyProduct(@PathVariable Long productId, 
                                                                       @PathVariable Long memberId,
                                                                       @RequestBody OrderRequestDto orderRequestDto) {
        OrderItemResponseDto responseDto = customerService.buyProduct(memberId, productId, orderRequestDto.getQuantity());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "장바구니 상품 전체 구입", description = "장바구니에 있는 모든 상품을 구입합니다.(Cusomer 본인 권한)")
    @PostMapping("/{memberId}/orders")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<List<OrderItemResponseDto>>> buyCart(@PathVariable Long memberId) {
        List<OrderItemResponseDto> responseDto = customerService.buyCart(memberId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "장바구니 조회", description = "장바구니에 있는 상품을 조회합니다.(Cusomer 본인 권한)")
    @GetMapping("/{memberId}/cart")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<List<CartItemResponseDto>>> getCart(@PathVariable Long memberId) {
        List<CartItemResponseDto> responseDto = customerService.getCart(memberId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "장바구니 추가", description = "장바구니에 상품을 추가합니다.(Cusomer 본인 권한)")
    @PostMapping("/{memberId}/cart/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addCart(@PathVariable Long productId,
            @Valid @RequestBody CartRequestDto cartRequestDto, @PathVariable Long memberId) {
        CartItemResponseDto responseDto = customerService.addCart(memberId, productId, cartRequestDto.getQuantity());
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @Operation(summary = "장바구니 상품 제거", description = "장바구니에서 특정 상품을 제거합니다.(Cusomer 본인 권한)")
    @DeleteMapping("/{memberId}/cart/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartItemId, @PathVariable Long memberId) {
        customerService.deleteCart(memberId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "장바구니 전체 제거", description = "장바구니에 있는 모든 상품을 제거합니다.(Cusomer 본인 권한)")
    @DeleteMapping("/{memberId}/cart/clear")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #memberId == authentication.principal.id")
    public ResponseEntity<Void> clearCart(@PathVariable Long memberId) {
        customerService.clearCart(memberId);
        return ResponseEntity.noContent().build();
    }
}
