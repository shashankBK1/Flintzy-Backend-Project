package com.socialmediamanagement.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmediamanagement.api.dto.PublishPostRequest;
import com.socialmediamanagement.api.dto.PublishPostResponse;
import com.socialmediamanagement.api.entity.SocialPost;
import com.socialmediamanagement.api.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/publish")
	public ResponseEntity<?> publish(@Valid @RequestBody PublishPostRequest requst, Authentication authentication) {

		String email = authentication.getName();

		SocialPost post = postService.publishPost(email, requst);

		return ResponseEntity.ok(new PublishPostResponse("SUCCESS", post.getFacebookPostId()));
	}
}
