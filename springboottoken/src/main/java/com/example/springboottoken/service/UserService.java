package com.example.springboottoken.service;

import com.example.springboottoken.pojo.User;

public interface UserService {
    User findByUsername(User user);

    User findUserById(String id);
}
