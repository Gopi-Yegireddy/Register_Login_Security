package com.example.security_ex.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.security_ex.Model.User;
import com.example.security_ex.Model.UserPrinciples;
import com.example.security_ex.Repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	private UserDetails userdetails=null;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//User user=repo.findByUsername(username);
		if(username.startsWith("USER ")) {
			User user = repo.findByUsername(username.substring(5));
			return new UserPrinciples(user);
		//return new UserPrinciples(user);
		
	}
		return userdetails;
	

}
}
