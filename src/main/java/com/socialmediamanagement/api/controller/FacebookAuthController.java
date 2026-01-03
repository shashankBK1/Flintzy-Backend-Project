package com.socialmediamanagement.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.socialmediamanagement.api.service.FacebookAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class FacebookAuthController {

	private final FacebookAuthService facebookAuthService;

	public FacebookAuthController(FacebookAuthService facebookAuthService) {
		this.facebookAuthService = facebookAuthService;
	}

	@GetMapping("/facebook/login")
	public void facebookLogin(HttpServletResponse response) throws Exception {
		response.sendRedirect(facebookAuthService.getFacebookLoginUrl());
	}

	@GetMapping("/facebook/callback")
	public ResponseEntity<Map<String, Object>> facebookCallback(@RequestParam String code,
			HttpServletRequest request) {

		Map<String, Object> tokenResponse = facebookAuthService.exchangeCodeForAccessToken(code, request);

		return ResponseEntity.ok(tokenResponse);
	}
}
