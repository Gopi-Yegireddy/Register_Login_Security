package com.example.security_ex.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.security_ex.Model.User;

@Service
public interface UserInterface {
	
public ResponseEntity<User> insert(User user);
public String Verify(User user);
public void deleteUser(String username);
public User userUpadateProfile(String username,User UserProfile);
public User getProfile(User user,String username);
}
