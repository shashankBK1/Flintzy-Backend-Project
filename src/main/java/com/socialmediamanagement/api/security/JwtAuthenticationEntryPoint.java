package com.socialmediamanagement.api.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.socialmediamanagement.api.utility.CustomErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper mapper;

	public JwtAuthenticationEntryPoint() {
		this.mapper = new ObjectMapper();
		this.mapper.registerModule(new JavaTimeModule());
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		CustomErrorResponse error = new CustomErrorResponse("JWT token is invalid or expired",
				HttpServletResponse.SC_UNAUTHORIZED, LocalDateTime.now());

		response.getWriter().write(mapper.writeValueAsString(error));
	}
}
