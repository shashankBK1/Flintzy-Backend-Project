package com.socialmediamanagement.api.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmediamanagement.api.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	  @GetMapping("/google/login")
	    public void login(HttpServletResponse response) throws IOException {
	        response.sendRedirect(authService.getLoginUrl());
	    }

	    // Google automatically calls this
	    @GetMapping("/google/callback")
	    public ResponseEntity<?> callback(@RequestParam String code,HttpServletResponse response) {
	    	 
	    	Map<String, Object> user = authService.handleCallback(code);
	    	
	    	Cookie cookie = new Cookie("userId", String.valueOf(user.get("userId")));
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        cookie.setMaxAge(300);
	        response.addCookie(cookie);
	        
	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("jwt", user.get("jwt"));
	        responseBody.put("message", "Google login successful");
	       
	        return ResponseEntity.ok(responseBody);
	    }
}
