package com.omerta.rest;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omerta.model.Greeting;
import com.omerta.security.CustomUserDetails;

@RestController
@RequestMapping(path="api/")
public class SampleService {

	@RequestMapping(path="hello")
	public Greeting sayHello(Authentication authentication)
	{

		if(authentication!=null)
		{
			CustomUserDetails customUserDetails=(CustomUserDetails)authentication.getPrincipal();
			return new Greeting(1,"Principal :"+customUserDetails.getUsername()+" Authorities :"+authentication.getAuthorities().toString());
		}
		else
			return new Greeting(1, "Merhaba Ali SAĞIRLIBAŞ not Principal");
	}
	
	@RequestMapping(path="secure")
	public Greeting saySecureHello()
	{
		return new Greeting(1, "Secure Ali SAĞIRLIBAŞ");
	}
	
	
	@RequestMapping(path="secure2")
	public Greeting saySecure3Hello()
	{
		return new Greeting(1, "Secure222 VELİ");
	}
}
