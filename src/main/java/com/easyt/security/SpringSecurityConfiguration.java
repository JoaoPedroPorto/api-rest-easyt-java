package com.easyt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	private static final String[] AUTH_LIST = {
			"*swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("*swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().authorizeRequests()
		 	.antMatchers(AUTH_LIST).permitAll()
	        .antMatchers(HttpMethod.POST, "/api/authentication/loginPanel").permitAll()
			.antMatchers(HttpMethod.POST, "/api/authentication/loginMobile").permitAll()
			.antMatchers(HttpMethod.POST, "/api/authentication/validateToken").permitAll()
			.antMatchers(HttpMethod.POST, "/api/authentication/resetPassword").permitAll()
			.antMatchers(HttpMethod.POST, "/api/authentication/rememberPassword").permitAll()
			.antMatchers(HttpMethod.POST, "/api/permission").permitAll()
	        .anyRequest()
	        .authenticated()
	    .and()
	    	.httpBasic();
		
		//http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
		//.addFilterBefore(customFilter(), BasicAuthenticationFilter.class)
	}
	
	@Bean
	public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
	    BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
	    entryPoint.setRealmName("Swagger Realm");
	    return entryPoint;
	}
	
	
	
	
	

	
}