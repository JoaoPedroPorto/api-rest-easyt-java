package com.easyt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyt.entity.Device;


public interface DeviceRepository extends JpaRepository<Device, Long> {
	
	Optional<Device> findOneByTokenAndUser_Id(String token, Long id);
	
}
