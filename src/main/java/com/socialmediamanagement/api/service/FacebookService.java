package com.socialmediamanagement.api.service;

import java.util.List;

import com.socialmediamanagement.api.entity.FacebookPage;
import com.socialmediamanagement.api.entity.User;

public interface FacebookService {

	public List<FacebookPage> linkFacebookPages(User user, String userAccessToken);
}
