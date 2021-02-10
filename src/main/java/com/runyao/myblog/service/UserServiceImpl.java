package com.runyao.myblog.service;

import com.runyao.myblog.dao.UserRepository;
import com.runyao.myblog.po.User;
import com.runyao.myblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Runyao Xia
 * @date: 2021/1/28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
