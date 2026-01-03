package com.socialmediamanagement.api.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.socialmediamanagement.api.entity.User;
import com.socialmediamanagement.api.repository.UserRepository;
import com.socialmediamanagement.api.service.FacebookAuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class FacebookAuthServiceImpl implements FacebookAuthService {

	@Value("${facebook.client-id}")
	private String clientId;

	@Value("${facebook.client-secret}")
	private String clientSecret;

	@Value("${facebook.redirect-uri}")
	private String redirectUri;

	private final RestTemplate restTemplate = new RestTemplate();

	private final UserRepository userRepository;
	
	 public FacebookAuthServiceImpl(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
	private static final String FACEBOOK_AUTH_URL = "https://www.facebook.com/v18.0/dialog/oauth";

	private static final String FACEBOOK_TOKEN_URL = "https://graph.facebook.com/v18.0/oauth/access_token";

	 @Override
	    public String getFacebookLoginUrl() {
	        return FACEBOOK_AUTH_URL + "?client_id=" + clientId + "&redirect_uri=" + redirectUri
	                + "&response_type=code&scope=email,public_profile,pages_show_list,pages_read_engagement,pages_manage_metadata,pages_manage_posts";
	    }

	@Override
	public Map<String, Object> exchangeCodeForAccessToken(String code,HttpServletRequest request) {

		String url = FACEBOOK_TOKEN_URL + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&client_secret="
				+ clientSecret + "&code=" + code;
		
		Map<String, Object> result = new HashMap<>();
		
		 Map<String, Object> fbResponse =
	                restTemplate.getForObject(url, Map.class);
		 String accessToken = (String) fbResponse.get("access_token");
	      
		 String userId = null;
	        if (request.getCookies() != null) {
	            for (Cookie cookie : request.getCookies()) {
	                if ("userId".equals(cookie.getName())) {
	                    userId = cookie.getValue();
	                    break;
	                }
	            }
	        }
	        if (userId == null) {
	            result.put("status", "Google user not logged in. Please login with Google first.");
	            return result;
	        }
	        Optional<User> optionalUser = userRepository.findById(Long.parseLong(userId));
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            user.setFacebookAccessToken(accessToken);
	            userRepository.save(user);

	            result.put("status", "Facebook login successful!");
	           
	        } else {
	            result.put("status", "User not found in database.");
	        }

	        return result;
	        
	
	}
}
