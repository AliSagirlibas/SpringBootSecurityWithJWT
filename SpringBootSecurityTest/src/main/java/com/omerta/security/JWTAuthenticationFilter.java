package com.omerta.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omerta.model.LoginRequest;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			LoginRequest loginRequest= new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);
			boolean rememberMe = (loginRequest.isRememberMe() == null) ? false : loginRequest.isRememberMe();

			UsernamePasswordAuthenticationToken authenticationToken =
		            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
	        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
	        
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        return authentication;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
				
		
		LoginRequest loginRequest= new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
		boolean rememberMe = (loginRequest.isRememberMe() == null) ? false : loginRequest.isRememberMe();

		
		String token =SecurityUtils.createTokenFromAuthenticationData(auth,rememberMe);				

		response.addHeader(SecurityConstants.AUTHORIZATION_HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

	}
}
