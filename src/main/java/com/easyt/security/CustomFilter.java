package com.easyt.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CustomFilter implements Filter {

	// private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHORIZATION_SCHEME = "Basic";
	// private static final String AUTHENTICATION_PROPERTY = "Authentication";
	/*
	 * @Autowired private static AuthenticationService authenticationService;
	 */

	/*
	 * @Override public void init(FilterConfig filterConfig)throws ServletException
	 * { System.out.println("Init::called"); }
	 */

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		System.out.println("doFilter::called");
		/*
		 * HttpServletRequest req = (HttpServletRequest) servletRequest;
		 * HttpServletResponse res = (HttpServletResponse) servletResponse; if
		 * (!isMethodValid(req.getMethod()))
		 * res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		 * "Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada."
		 * ); final String authorization = req.getHeader(AUTHORIZATION_PROPERTY); if
		 * (!verifyAuthorization(req.getHeader(AUTHORIZATION_PROPERTY)))
		 * res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		 * "Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada."
		 * ); final String encodedUserPassword =
		 * authorization.replaceFirst(AUTHORIZATION_SCHEME + " ", ""); String
		 * usernameAndPassword = new
		 * String(Base64.getDecoder().decode(encodedUserPassword)); final
		 * StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		 * final String username = tokenizer.nextToken(); final String password =
		 * tokenizer.nextToken(); if (!isUserAuthorized(username, password))
		 * res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		 * "Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada."
		 * ); final String authentication = req.getHeader(AUTHENTICATION_PROPERTY); if
		 * (!isUserAuthenticated(authentication))
		 * res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
		 * "Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada."
		 * ); Principal userPrincipal = req.getUserPrincipal();
		 * System.out.println("userPrincipal: " + userPrincipal);
		 * filterChain.doFilter(servletRequest, servletResponse);
		 */
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		System.out.println("Destroy::called");
	}

	/*
	 * private boolean isUserAuthorized(final String username, final String
	 * password) { if
	 * (ResourceBundleHandler.getValue(ResourceBundleHandler.SECURITY_BUNDLE,
	 * "username").equals(username) &&
	 * ResourceBundleHandler.getValue(ResourceBundleHandler.SECURITY_BUNDLE,
	 * "password").equals(password)) return true; return false; }
	 */

	/*
	 * private boolean isUserAuthenticated(final String token) { if (token == null
	 * || token.trim().isEmpty()) return false; // TODO: VERIFICAR TOKEN DE ACESSO
	 * return true; // return authenticationService.verifyUserAuthenticated(token);
	 * }
	 */

	public boolean verifyAuthorization(String authentication) {
		try {
			if (authentication == null || authentication.trim().isEmpty())
				return false;
			String[] parts = authentication.trim().split(" ");
			if ((parts[0] != null && !parts[0].trim().isEmpty() && parts[0].equals(AUTHORIZATION_SCHEME))
					&& (parts[1] != null && !parts[1].trim().isEmpty()))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean isMethodValid(String method) {
		if (method == null || method.trim().isEmpty())
			return false;
		List<String> listMethods = new ArrayList<>();
		listMethods.add("GET");
		listMethods.add("POST");
		listMethods.add("PUT");
		listMethods.add("PATCH");
		listMethods.add("DELETE");
		if (!listMethods.contains(method))
			return false;
		return true;
	}

}
