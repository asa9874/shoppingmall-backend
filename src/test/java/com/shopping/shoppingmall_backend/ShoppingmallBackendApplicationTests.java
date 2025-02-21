package com.shopping.shoppingmall_backend;

import com.shopping.model.Member;
import com.shopping.model.Product;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                .memberId("TestMan123")
                .password("password123")
                .nickname("TestMan")
                .role(Member.Role.SELLER)
                .build();
		memberRepository.save(member);  
    }

	@Test
	void ProductCreateTest() {
		Seller seller = Seller.builder()
                .member(member)
                .build();

        sellerRepository.save(seller);  

        Product product = Product.builder()
                .name("Smartphone")
                .image("smartphone.jpg")
                .description("Latest model smartphone")
                .price(500)
                .stock(100)
                .seller(seller) 
                .build();

        productRepository.save(product); 

        assertNotNull(product.getId()); 
	}

}
