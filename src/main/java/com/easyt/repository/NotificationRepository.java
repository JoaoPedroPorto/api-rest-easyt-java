package com.easyt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyt.constant.StatusEnum;
import com.easyt.entity.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	Optional<Notification> findOneByIdAndStatusNot(Long id, StatusEnum status);
	
}
