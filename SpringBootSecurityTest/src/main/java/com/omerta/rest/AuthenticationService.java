package com.omerta.rest;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.omerta.model.LoginRequest;
import com.omerta.security.SecurityConstants;
import com.omerta.security.SecurityUtils;



@RestController
@RequestMapping("api/")
public class AuthenticationService {

	
	private final AuthenticationManager authenticationManager;

    public AuthenticationService(AuthenticationManager authenticationManager) 
    {
        this.authenticationManager = authenticationManager;
    }
	
	@RequestMapping(path="/authenticate")
	public ResponseEntity<JWTToken> authenticate(@Valid @RequestBody LoginRequest loginRequest)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		
		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginRequest.isRememberMe() == null) ? false : loginRequest.isRememberMe();
        
        String jwt = SecurityUtils.createTokenFromAuthenticationData(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstants.AUTHORIZATION_HEADER_STRING, "Bearer " + jwt);
        return new ResponseEntity<>( new JWTToken(jwt) , httpHeaders, HttpStatus.OK);
	}
	
	
	 /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
