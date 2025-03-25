package com.example.security_ex.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security_ex.JwtUtil.JwtUtil;
import com.example.security_ex.Model.User;
import com.example.security_ex.Repo.UserRepository;

@Service
public class UserService implements UserInterface{

	@Autowired
	private UserRepository repo;
	@Autowired 
	AuthenticationManager authenticationManager;
	@Autowired
	public JwtUtil jwtUtil;
	
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	@Override
	public ResponseEntity<User> insert(User user) {
		 if (user.getUserpassword() == null || user.getUserpassword().trim().isEmpty()) {
	            throw new IllegalArgumentException("Password cannot be null or empty");
	        }
		 
		user.setUserpassword(encoder.encode(user.getUserpassword()));
		User savedUser=repo.save(user);
		return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
	}

	@Override
	public String Verify(User user) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("USER "+user.getUsername(), user.getUserpassword()));
			if(authentication.isAuthenticated()) {
				Map<String, Object> claims = new HashMap<>();
				claims.put("role", "USER");
				String token = jwtUtil.generateToken(user.getUsername(), claims);
				//User user2 = repo.findByUsername(user.getUsername());
				//tokenService.saveUserToken(token, user2);
				System.out.println(token);
				return token;
			}
		}catch (Exception e) {
			throw new RuntimeException("Invalid username or password!!");
		}
		return "fail";
	}

	@Override
	public void deleteUser(String username) {
		try {
			User user=repo.findByUsername(username);
			if(user!=null)
				repo.delete(user);
		}catch (Exception e) {
			throw new UsernameNotFoundException("Record not found to delete");
		}
		
	}

	@Override
	public User userUpadateProfile(String username, User UserProfile) {
		
		User user=repo.findByUsername(username);
		user.setFname(UserProfile.getFname());
		user.setLname(UserProfile.getLname());
		
		
		return repo.save(user);
	}

	@Override
	public User getProfile(User user,String username) {
		User user1=repo.findByUsername(username);
		return user1;
	}

	

}
