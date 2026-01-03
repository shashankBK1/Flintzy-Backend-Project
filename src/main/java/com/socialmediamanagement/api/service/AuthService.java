package com.socialmediamanagement.api.service;

import java.util.Map;

public interface AuthService {

    String getLoginUrl();

    Map<String, Object> handleCallback(String code);
    
   }
