package com.example.security_ex.userConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
@EnableWebSecurity
@Configuration

public class UserConfiguration {
	@Autowired
 public JwtFilter jwtFilter;
	@Autowired
	public UserDetailsService userdetailsservice;
	
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception {
		
		return http
				.csrf(customizer->customizer.disable())
				.authorizeHttpRequests(request->request.requestMatchers("/user/**")
						.permitAll()
						.anyRequest().authenticated())
			
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
				
				.build();

				
	}
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}
	@Bean
	public AuthenticationProvider authenticationprovider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(userdetailsservice);
		return provider;
	}
	
	
}
