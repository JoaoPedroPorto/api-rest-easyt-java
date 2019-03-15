package com.easyt.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("authenticationEntryPoint")
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authEx)
			throws IOException, ServletException {
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada.");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("easyt");
		super.afterPropertiesSet();
	}

}