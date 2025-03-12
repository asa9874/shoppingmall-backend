package com.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
