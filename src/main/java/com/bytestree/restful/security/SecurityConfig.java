package com.bytestree.restful.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * 
 * @author hemantbh
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static String REALM_NAME ="RESTFUL_REALM";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
	  http.csrf().disable()
	  	.authorizeRequests()
	  	.antMatchers(HttpMethod.POST, "/employee/**").hasAnyAuthority("ADMIN")
	  	.antMatchers(HttpMethod.PUT, "/employee/**").hasAnyAuthority("ADMIN")
	  	.antMatchers(HttpMethod.DELETE, "/employee/**").hasAnyAuthority("ADMIN")
	  	.anyRequest().authenticated()
		.and().httpBasic()
		.realmName(REALM_NAME).authenticationEntryPoint(getBasicAuthEntryPoint())
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  }
	
	
	@Bean
	public BasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		BasicAuthenticationEntryPoint basicAuthEntryPoint = new BasicAuthenticationEntryPoint();
		basicAuthEntryPoint.setRealmName(REALM_NAME);
		return basicAuthEntryPoint;
	}

}
