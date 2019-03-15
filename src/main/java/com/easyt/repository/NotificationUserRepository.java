package com.easyt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyt.entity.NotificationUser;


public interface NotificationUserRepository extends JpaRepository<NotificationUser, Long> {
	
}
