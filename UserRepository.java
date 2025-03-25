package com.example.security_ex.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security_ex.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByUsername(String username);

	
	

}
