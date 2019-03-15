package com.easyt.repository;

import org.springframework.data.repository.CrudRepository;

import com.easyt.entity.Application;


public interface ApplicationRepository extends CrudRepository<Application, String> {
	
	Application findOneByLogin(String login);	
	
}
