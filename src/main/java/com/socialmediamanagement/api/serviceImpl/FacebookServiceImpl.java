package com.socialmediamanagement.api.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.socialmediamanagement.api.entity.FacebookPage;
import com.socialmediamanagement.api.entity.User;
import com.socialmediamanagement.api.repository.FacebookPageRepository;
import com.socialmediamanagement.api.service.FacebookService;

@Service
public class FacebookServiceImpl implements FacebookService {

	private final FacebookPageRepository pageRepository;
	private final RestTemplate restTemplate = new RestTemplate();

	public FacebookServiceImpl(FacebookPageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	public List<FacebookPage> linkFacebookPages(User user, String userAccessToken) {

		String url = "https://graph.facebook.com/me/accounts?access_token=" + userAccessToken;

		Map response = restTemplate.getForObject(url, Map.class);

		List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

		List<FacebookPage> savedPages = new ArrayList<>();

		for (Map<String, Object> pageData : data) {

			String pageId = (String) pageData.get("id");

			FacebookPage page = pageRepository.findByPageIdAndUser_Id(pageId, user.getId()).orElse(new FacebookPage());

			page.setPageId(pageId);
			page.setPageName((String) pageData.get("name"));
			page.setPageAccessToken((String) pageData.get("access_token"));
			page.setUser(user);

			savedPages.add(pageRepository.save(page));

		}

		return savedPages;
	}
}