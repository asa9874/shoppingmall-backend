package com.shopping.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import com.shopping.config.SecurityConfig;
import com.shopping.dto.Response.ProductResponseDTO;
import com.shopping.jwt.JwtTokenProvider;
import com.shopping.model.Member;
import com.shopping.model.Product;
import com.shopping.model.Seller;
import com.shopping.service.ProductService;

import lombok.extern.log4j.Log4j2;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
@Import(SecurityConfig.class)
@Log4j2
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setup(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();  
        log.info("테스트 시작: " + testName);
    }

    @AfterEach
    void logTestResult(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();  
        log.info("테스트 끝: " + testName);
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void testGetProductItems() throws Exception {
        // 가짜 데이터 설정
        Member member = Member.builder()
                .memberId("user1234")
                .password("password123!")
                .nickname("user123_nickname")
                .role(Member.Role.CUSTOMER)
                .build();

        Seller seller = Seller.builder()
                .member(member)
                .build();

        Product product = Product.builder()
                .name("테스트 상품 1")
                .price(10000)
                .image("test.jpg")
                .description("테스트 상품입니다.")
                .stock(100)
                .category(Product.Category.ELECTRONICS)
                .seller(seller)
                .build();

        List<ProductResponseDTO> mockProducts = Arrays.asList(
                ProductResponseDTO.fromEntity(product),
                ProductResponseDTO.fromEntity(product),
                ProductResponseDTO.fromEntity(product));

        when(productService.getProductItems(10)).thenReturn(mockProducts);

        mockMvc.perform(get("/product/")
                .param("count", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 응답 확인
                .andExpect(jsonPath("$.size()").value(3)) 
                .andExpect(jsonPath("$[0].name").value("테스트 상품 1")); 

    }
}