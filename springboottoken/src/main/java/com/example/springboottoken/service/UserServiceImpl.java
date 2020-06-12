package com.example.springboottoken.service;

import com.example.springboottoken.dao.UserMapper;
import com.example.springboottoken.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements  UserService{
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(User user) {
        return userMapper.findByUsername(user.getUsername());
    }

    @Override
    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }
}
