package com.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

