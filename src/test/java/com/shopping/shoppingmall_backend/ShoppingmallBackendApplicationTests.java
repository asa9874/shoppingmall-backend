package com.shopping.shoppingmall_backend;

import java.lang.reflect.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shopping.model.Seller;
import com.shopping.repository.ProductRepository;

@SpringBootTest
class ShoppingmallBackendApplicationTests {

	@Autowired
    private ProductRepository productRepository;

	private Seller TestSeller=Seller.builder().build();


	@Test
	void ProductCreateTest() {
		
	}

}
