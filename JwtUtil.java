package com.example.security_ex.JwtUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
private String secret_Key="";
public JwtUtil() {
	try {
		KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
		SecretKey sk=keyGen.generateKey();
		secret_Key=Base64.getEncoder().encodeToString(sk.getEncoded());
		
	}catch(NoSuchAlgorithmException e) {
		throw new RuntimeException(e);
	}
	
}
public String generateToken(String username, Map<String, Object> claims) {
//	Map<String, Object> claims = new HashMap<>();
	return Jwts.builder()
			.claims()
			.add(claims)
			.subject(username)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
			.and()
			.signWith(getKey())
			.compact();
}
private SecretKey getKey() {
	byte[] keyBytes = Decoders.BASE64.decode(secret_Key);
	return Keys.hmacShaKeyFor(keyBytes);
}

public boolean validateToken(String token,UserDetails userDetails) {
	final String username = extractUsername(token);
	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

private boolean isTokenExpired(String token) {
	return extractExpiration(token).before(new Date());
}


private Date extractExpiration(String token) {
	return extractClaim(token, Claims::getExpiration);
}

public String extractUsername(String token) {
	return extractClaim(token, Claims::getSubject);
}

public String extractRole(String token) {
	return extractClaim(token, Claims -> Claims.get("role", String.class));
}

public Claims extractAllClaims(String token) {
	return Jwts.parser()
			.verifyWith(getKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
}

private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	final Claims claims = extractAllClaims(token);
	return claimsResolver.apply(claims);
}

}
