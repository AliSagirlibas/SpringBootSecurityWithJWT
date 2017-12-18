package com.omerta;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.omerta.model.Role;
import com.omerta.model.User;
import com.omerta.repository.RoleRepository;
import com.omerta.repository.UserRepository;

@SpringBootApplication
public class SpringBootSecurityTestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityTestApplication.class, args);
	}

	@Bean
	PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder(11);
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		
		userRepository.deleteAll();
		roleRepository.deleteAll();
		
				
		Role roleAdmin =new Role("ROLE_ADMIN");
		Role roleOperator =new Role("ROLE_OPERATOR");
		//roleRepository.save(roleAdmin);
		//roleRepository.save(roleOperator );
		
		System.out.println("ROLE ADMIN  ID "+ roleAdmin.getId());
		System.out.println("ROLE OPERATOR ID "+ roleOperator.getId());	
		
		
		
		User u=new User();
		u.setUsername("ali");
		u.setPassword( getPasswordEncoder().encode("...") );
		
		u.getRoles().add(roleAdmin);
		u.getRoles().add(roleOperator);		
		// userRepository.save(u);
		
		User u2=new User();
		u2.setUsername("veli");
		u2.setPassword( getPasswordEncoder().encode("...") );		
		u2.getRoles().add(roleOperator);
				
		userRepository.save( new HashSet<User>() 
		{{ 
			add( u) ;
			add( u2);
		}} );		
		
	}
	
	
}
