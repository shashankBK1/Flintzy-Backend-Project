package com.socialmediamanagement.api.service;

import com.socialmediamanagement.api.dto.PublishPostRequest;
import com.socialmediamanagement.api.entity.SocialPost;

public interface PostService {

	public SocialPost publishPost(String email, PublishPostRequest requst);
}
