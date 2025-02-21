package com.shopping.service;

import org.springframework.stereotype.Service;

import com.shopping.model.Member;
import com.shopping.model.Seller;
import com.shopping.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerService {
    
    private final SellerRepository sellerRepository;

    public void registerSeller(Member member){
        Seller seller = new Seller();
        seller.setMember(member);
        sellerRepository.save(seller);
    }
}
