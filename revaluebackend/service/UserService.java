package com.frank.revaluebackend.service;

import com.frank.revaluebackend.pojo.LoginInfo;
import com.frank.revaluebackend.pojo.RegisterInfo;
import com.frank.revaluebackend.pojo.User;

public interface UserService {
    RegisterInfo register(User user);

    User getUserByEmail(String email);

    LoginInfo login(User user);

    User getInfo(Integer id);

    void update(User user);

    String getUsername(Integer buyerId);
}
