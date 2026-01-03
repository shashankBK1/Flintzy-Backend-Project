package com.socialmediamanagement.api.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.socialmediamanagement.api.entity.User;
import com.socialmediamanagement.api.repository.UserRepository;
import com.socialmediamanagement.api.security.JwtUtil;
import com.socialmediamanagement.api.service.AuthService;
import com.socialmediamanagement.api.utility.AuthProvider;

@Service
public class AuthServiceImpl implements AuthService {

	@Value("${google.client-id}")
	private String clientId;

	@Value("${google.client-secret}")
	private String clientSecret;

	@Value("${google.redirect-uri}")
	private String redirectUri;

	@Value("${google.auth-uri}")
	private String authUri;

	@Value("${google.token-uri}")
	private String tokenUri;

	@Value("${google.userinfo-uri}")
	private String userInfoUri;

	private final RestTemplate restTemplate = new RestTemplate();
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;

	public AuthServiceImpl(JwtUtil jwtUtil, UserRepository userRepository) {
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
	}

	@Override
	public String getLoginUrl() {
		return authUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=code"
				+ "&scope=openid email profile";
	}

	@Override
	public Map<String, Object> handleCallback(String code) {

		String googleAccessToken = exchangeCodeForToken(code);

		Map<String, Object> profile = fetchUserProfile(googleAccessToken);

		String email = profile.get("email").toString();
		String name = profile.get("name").toString();

		Optional<User> optionalUser = userRepository.findByEmail(email);
		User user;
		if (optionalUser.isPresent()) {
			user = optionalUser.get();
			user.setName(name);

		} else {
			user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setProvider(AuthProvider.GOOGLE);
			user.setCreatedAt(LocalDateTime.now());
		}
		userRepository.save(user);

		String jwt = jwtUtil.generateToken(email);

		Map<String, Object> response = new HashMap<>();
		response.put("email", email);
		response.put("jwt", jwt);
		response.put("Status", "Google Login Successfull");
		response.put("userId", user.getId());
		return response;
	}

	private String exchangeCodeForToken(String code) {
		String url = tokenUri + "?client_id=" + clientId + "&client_secret=" + clientSecret + "&redirect_uri="
				+ redirectUri + "&code=" + code + "&grant_type=authorization_code";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<String> request = new HttpEntity<>(headers);

		Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
		return response.get("access_token").toString();
	}

	private Map<String, Object> fetchUserProfile(String accessToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, request, Map.class);

		return response.getBody();
	}
}
