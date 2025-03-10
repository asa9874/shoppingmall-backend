package com.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
