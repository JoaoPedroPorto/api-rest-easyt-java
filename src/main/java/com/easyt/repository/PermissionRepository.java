package com.easyt.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyt.constant.StatusEnum;
import com.easyt.entity.Permission;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
	
	Optional<Permission> findOneByDescription(String description);
	Optional<Permission> findOneByIdAndStatusNot(Long id, StatusEnum status);
	Stream<Permission> findAllByStatusNot(StatusEnum status);
	
	// GET PERMISSIONS BY PROFILE
	Stream<Permission> findAllByManagerAndStatusNot(Boolean manager, StatusEnum status);
	Stream<Permission> findAllByModeratorAndStatusNot(Boolean moderator, StatusEnum status);
	Stream<Permission> findAllByStudentAndStatusNot(Boolean student, StatusEnum status);
	Stream<Permission> findAllByDriverAndStatusNot(Boolean driver, StatusEnum status);
}
