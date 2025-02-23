package com.shopping.shoppingmall_backend;

import com.shopping.model.Member;
import com.shopping.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shopping.model.Seller;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.SellerRepository;

@SpringBootTest
class ShoppingmallBackendApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .memberId("testman123")
                .password("testman123")
                .nickname("테스트닉네임")
                .role(Member.Role.SELLER)
                .build();
        memberRepository.save(member);
    }

    @Test
    void ProductCreateMultipleTest() {
        Seller seller = Seller.builder()
                .member(member)
                .build();

        sellerRepository.save(seller);

        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Product product = Product.builder()
                    .name("스마트폰 " + i)
                    .image("smartphone.jpg")
                    .description("훌륭한 스마트폰 " + i + "이다.")
                    .price(500)
                    .stock(100)
                    .seller(seller)
                    .category(Product.Category.ELECTRONICS)
                    .build();

            products.add(product);
        }

        productRepository.saveAll(products);

        assertEquals(10, productRepository.count());
    }

}
