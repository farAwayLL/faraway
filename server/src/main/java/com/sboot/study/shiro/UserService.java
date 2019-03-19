package com.sboot.study.shiro;

import com.sboot.study.entity.User;
import com.sboot.study.mybatisMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author faraway
 * @date 2019/3/13 10:00
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectUserByUsername(String username) {

        return userMapper.selectUserByUsernameAndPassword(username);

    }
}
