package com.example.springboottoken.dao;

import com.example.springboottoken.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author qiaoyn
 * @date 2019/06/14
 */
@Mapper
@Component
public interface UserMapper {

    User findByUsername(String username);

    User findUserById(String id);

}
