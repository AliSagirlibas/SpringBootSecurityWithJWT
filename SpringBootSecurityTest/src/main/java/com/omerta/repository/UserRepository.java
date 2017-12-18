package com.omerta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omerta.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findUserByusername(String userName);
}
