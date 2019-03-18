package com.easyt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.entity.Application;
import com.easyt.repository.ApplicationRepository;

@Repository
public class ImplementsUserDetailsService implements UserDetailsService {
	@Autowired
	private ApplicationRepository applicationRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(ImplementsUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		try {
			Application appDB = applicationRepository.findOneByLogin(login);
			if (appDB == null)
				throw new UsernameNotFoundException("Bad credentials");
			return appDB;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new UsernameNotFoundException(MessagesErroEnum.ERRO_SOLICITATION.getMessage());
		}
		
	}

}