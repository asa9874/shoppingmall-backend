package com.shopping.shoppingmall_backend;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopping.model.Member;
import com.shopping.model.Product;
import com.shopping.model.Seller;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.SellerRepository;

//TODO: 단위 테스트(unit test) 생성하기
@SpringBootTest
class ShoppingmallBackendApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SellerRepository sellerRepository;

    private Member member;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void CreateAdminAcount() {
        member = Member.builder()
                .memberId("admin123")
                .password(passwordEncoder.encode("admin123"))
                .nickname("어드민")
                .role(Member.Role.ADMIN)
                .build();
        memberRepository.save(member);
    }

    @Test
    void ProductCreateMultipleTest() {
        member = Member.builder()
                .memberId("testman123")
                .password(passwordEncoder.encode("testman123"))
                .nickname("테스트닉네임")
                .role(Member.Role.SELLER)
                .build();

        Seller seller = Seller.builder()
                .member(member)
                .build();

        sellerRepository.save(seller);

        List<Product> products = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Product product = Product.builder()
                    .name("스마트폰 " + i)
                    .image("/images/smartphone.jpg")
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
