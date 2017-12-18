package com.omerta.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityUtils {

	public static String createTokenFromAuthenticationData(Authentication auth,boolean rememberMe){
		
		Date date;
		if(rememberMe==false){
			date=new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
		}
		else{
			date=new Date(System.currentTimeMillis() + SecurityConstants.LONG_EXPIRATION_TIME);
		}
		String authorities = auth.getAuthorities().stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.joining(","));		
				//Claims claims;
				//claims.set
		String token = Jwts.builder().setSubject(((CustomUserDetails) auth.getPrincipal()).getUsername())	
				//.setClaims("")
				.claim("roles", authorities)
				.setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET).compact();
		
		return token;

	}
}
