package com.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}