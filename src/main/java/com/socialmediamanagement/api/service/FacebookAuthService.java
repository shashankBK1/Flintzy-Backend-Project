package com.socialmediamanagement.api.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface FacebookAuthService {

	String getFacebookLoginUrl();

    Map<String, Object> exchangeCodeForAccessToken(String code,HttpServletRequest response);
}
