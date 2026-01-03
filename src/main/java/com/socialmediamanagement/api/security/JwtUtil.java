package com.socialmediamanagement.api.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.socialmediamanagement.api.exceptions.InvalidJwtTokenException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(String email) {
		return Jwts.builder().setSubject(email.trim().toLowerCase()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	public String extractEmial(String token) {

		try {
			return Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody()
					.getSubject();

		} catch (ExpiredJwtException e) {
			throw new InvalidJwtTokenException("JWT token expired");
		} catch (MalformedJwtException e) {
			throw new InvalidJwtTokenException("JWT token malformed");
		} catch (SignatureException e) {
			throw new InvalidJwtTokenException("JWT signature invalid");
		} catch (Exception e) {
			throw new InvalidJwtTokenException("JWT token invalid");
		}
	}
}
