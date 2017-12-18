package com.omerta.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class JWTAuthorizationFilter  extends BasicAuthenticationFilter{

	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	
	 private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
	        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER_STRING);
	        if (token != null) {
	            // parse the token.
	        	try {
	        		token=token.replace(SecurityConstants.TOKEN_PREFIX, "");
	        		Claims claims= 
		            Jwts.parser()
		                    .setSigningKey(SecurityConstants.SECRET)
		                    .parseClaimsJws(token)
		                    .getBody();
	        		
	        			        		
	        	        Collection<? extends GrantedAuthority> authorities =
	        	            Arrays.stream(claims.get(SecurityConstants.JWT_AUTH_CLAIM_STRING).toString().split(","))
	        	                .map(SimpleGrantedAuthority::new)
	        	                .collect(Collectors.toList());

	        	        User principal = new User(claims.getSubject(), token, authorities);

	        	        return new UsernamePasswordAuthenticationToken(principal, token, authorities);	        			        
				} catch (Exception e) {
					System.out.println(e);
				}
	        		           
	        }
	        return null;
	    }	
	

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		//super.doFilterInternal(arg0, arg1, arg2);
		
		String header =req.getHeader(SecurityConstants.AUTHORIZATION_HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(req, res);
            return;
        }
		
		UsernamePasswordAuthenticationToken authenticationToken=getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		filterChain.doFilter(req, res);
	}
	
	
	
	

}
