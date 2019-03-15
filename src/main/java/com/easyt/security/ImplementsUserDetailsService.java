package com.easyt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.easyt.entity.Application;
import com.easyt.repository.ApplicationRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {
	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Application appDB = applicationRepository.findOneByLogin(login);
		if (appDB == null)
			throw new UsernameNotFoundException("Usuário não encontrado ou sem permissão de acesso...");
		return appDB;
	}

}