package com.runyao.myblog.dao;

import com.runyao.myblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


/**
 * @author Runyao Xia
 * @date: 2021/1/28
 */

@Component
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}

