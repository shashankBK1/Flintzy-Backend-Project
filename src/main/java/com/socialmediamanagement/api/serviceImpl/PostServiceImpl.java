package com.socialmediamanagement.api.serviceImpl;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.socialmediamanagement.api.dto.PublishPostRequest;
import com.socialmediamanagement.api.entity.FacebookPage;
import com.socialmediamanagement.api.entity.SocialPost;
import com.socialmediamanagement.api.entity.User;
import com.socialmediamanagement.api.exceptions.FacebookPageNotLinkedException;
import com.socialmediamanagement.api.repository.FacebookPageRepository;
import com.socialmediamanagement.api.repository.SocialPostRepository;
import com.socialmediamanagement.api.repository.UserRepository;
import com.socialmediamanagement.api.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private final FacebookPageRepository facebookPageRepository;
	private final SocialPostRepository postRepository;
	private final UserRepository userRepository;
	private final RestTemplate restTemplate = new RestTemplate();

	public PostServiceImpl(FacebookPageRepository facebookPageRepository, SocialPostRepository postRepository,
			UserRepository userRepository) {
		this.facebookPageRepository = facebookPageRepository;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	public SocialPost publishPost(String email, PublishPostRequest requst) {

		if (email == null || email.isBlank()) {
			throw new RuntimeException("Authenticated email is missing");
		}

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Authenticated user not found in DB"));

		FacebookPage page = facebookPageRepository.findByPageIdAndUser_Id(requst.getPageId(), user.getId())
				.orElseThrow(() -> new FacebookPageNotLinkedException("Facebook Page not linked to this user"));

		String url = "https://graph.facebook.com/" + page.getPageId() + "/feed";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> body = Map.of("message", requst.getMessage(), "access_token", page.getPageAccessToken());

		// HTTP entity combining headers and request payload
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

		// Publish post to Facebook page using Graph API
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, entity, Map.class);

		String faceBookPostId = responseEntity.getBody().get("id").toString();

		SocialPost post = new SocialPost();
		post.setPageID(page.getPageId());
		post.setContent(requst.getMessage());
		post.setFacebookPostId(faceBookPostId);
		post.setStatus("PUBLISH SUCESS");
		post.setCreatedAt(LocalDateTime.now());
		post.setUser(user);

		return postRepository.save(post);
	}

}
