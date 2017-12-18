package com.omerta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	//@Autowired
	//private BasicAuthenticationPoint authenticationEntryPoint;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http
		 .cors().and().csrf().disable()
		 //.formLogin().and() //httpBasic().authenticationEntryPoint(authenticationEntryPoint)		 		 
		 .authorizeRequests().antMatchers("/api/hello").permitAll()         
         .and()
         .authorizeRequests().antMatchers("/mappings").permitAll()         
         .and()
         .authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
         .and()         
         .authorizeRequests().antMatchers("/api/secure").hasAnyRole("ADMIN")
         .and()
         .authorizeRequests().antMatchers("/api/secure2").hasAnyRole("OPERATOR","ADMIN")
         .and()
         .addFilter(new JWTAuthenticationFilter(authenticationManager()))
         .addFilter(new JWTAuthorizationFilter(authenticationManager()))         
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) ;
         
         //.maximumSessions(2);
         
         
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
		
		auth.authenticationProvider(getAuthProvider());
		
		/*
		auth.inMemoryAuthentication()
		.withUser("ali").password("...").roles("ADMIN","OPERATOR")
		.and()
		.withUser("op").password("...").roles("OPERATOR");
		*/
				
	}
	
	@Bean
	DaoAuthenticationProvider getAuthProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return daoAuthenticationProvider;
	}

	private PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	

	
}
