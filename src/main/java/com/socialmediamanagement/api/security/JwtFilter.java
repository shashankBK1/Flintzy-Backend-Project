package com.socialmediamanagement.api.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.socialmediamanagement.api.entity.User;
import com.socialmediamanagement.api.exceptions.InvalidJwtTokenException;
import com.socialmediamanagement.api.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final JwtAuthenticationEntryPoint entryPoint;

	public JwtFilter(JwtUtil jwtUtil, UserRepository userRepository, JwtAuthenticationEntryPoint entryPoint) {
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.entryPoint = entryPoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {

			String token = header.substring(7);

			try {

				String email = jwtUtil.extractEmial(token);

				User user = userRepository.findByEmail(email)
						.orElseThrow(() -> new InvalidJwtTokenException("JWT token is invalid"));

				CustomUserDetails userDetails = new CustomUserDetails(user);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (InvalidJwtTokenException e) {
				SecurityContextHolder.clearContext();
				entryPoint.commence(request, response, null);
				return; 
			}
		}
		filterChain.doFilter(request, response);

	}
}
