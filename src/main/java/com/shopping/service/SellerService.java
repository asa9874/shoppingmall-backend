package com.shopping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.model.Member;
import com.shopping.model.Product;
import com.shopping.model.Seller;
import com.shopping.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public void registerSeller(Member member) {
        Seller seller = new Seller();
        seller.setMember(member);
        sellerRepository.save(seller);
    }

    public List<Product> getProducts(Long memberId, Integer count) {
        Seller seller = sellerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("판매자 정보를 찾을 수 없습니다."));
        if (count != null) {
            return seller.getProducts().subList(0, count);
        }
        else{
            return seller.getProducts();
        }
    }
}
