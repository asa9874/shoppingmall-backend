package com.shopping.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.model.Seller;


public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByMemberId(Long memberId);
}
