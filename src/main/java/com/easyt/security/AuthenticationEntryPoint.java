package com.easyt.security;

/*@Component("authenticationEntryPoint")*/
public class AuthenticationEntryPoint /*extends BasicAuthenticationEntryPoint*/ {

	/*@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authEx)
			throws IOException, ServletException {
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Acesso negado. Você deve estar autenticado no sistema para acessar a URL solicitada.");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("easyt");
		super.afterPropertiesSet();
	}*/

}