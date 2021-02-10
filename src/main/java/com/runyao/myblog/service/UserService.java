package com.runyao.myblog.service;

import com.runyao.myblog.po.User;

/**
 * @author Runyao Xia
 * @date: 2021/1/28
 */
public interface UserService {
    User checkUser(String username, String password);
}
