package com.omerta.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.omerta.model.Role;
import com.omerta.model.User;

public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186578513661388309L;
	private User user;
	private Collection<Role> userRoles;
	public CustomUserDetails(User u){
		this.user=u ;
		System.out.println("Fetching User Roles LAZILY");
		this.userRoles=u.getRoles();
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		Collection<SimpleGrantedAuthority> authList=new ArrayList<>();
		for (Iterator iterator = userRoles.iterator(); iterator.hasNext();) {
			Role role= (Role) iterator.next();
			authList.add(new SimpleGrantedAuthority(role.getName()) );
			
		} 
				
		// authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		// authList.add(new SimpleGrantedAuthority("ROLE_OPERATOR"));
		return authList;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
