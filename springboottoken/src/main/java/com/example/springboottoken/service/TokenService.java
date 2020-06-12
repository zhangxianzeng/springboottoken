package com.example.springboottoken.service;

import com.example.springboottoken.pojo.User;

public interface TokenService {
    public String getToken(User user);
}
