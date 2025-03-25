package com.example.security_ex.userController;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security_ex.Model.User;
import com.example.security_ex.Service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	@Autowired
	UserService userService;
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user){
		return userService.insert(user);
		
	}
	@PostMapping("/login")
	public ResponseEntity<String> verify(@RequestBody User user) {
		System.out.println(new ResponseEntity<>(userService.Verify(user),HttpStatus.OK));
		return new ResponseEntity<>(userService.Verify(user),HttpStatus.OK);
		
	}
	@DeleteMapping("delete")
	public String delete(Authentication auth) {
		String username=auth.name();
		userService.deleteUser(username);
		return "Deleted successfully";
		
	}
	@PutMapping("/update")
	public User upProfile(Authentication auth, @RequestBody User userprofile) {
		String name=auth.name();
		return userService.userUpadateProfile(name, userprofile);
	}
	@GetMapping("/get")
	public User getprofile(User user,Authentication auth) {
		String name1=auth.name();

		return userService.getProfile(user, name1);
	}
	
	

}
