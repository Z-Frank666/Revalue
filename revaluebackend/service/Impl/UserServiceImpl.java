package com.frank.revaluebackend.service.Impl;

import com.frank.revaluebackend.mapper.UserMapper;
import com.frank.revaluebackend.pojo.LoginInfo;
import com.frank.revaluebackend.pojo.RegisterInfo;
import com.frank.revaluebackend.pojo.User;
import com.frank.revaluebackend.service.UserService;
import com.frank.revaluebackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public RegisterInfo register(User user) {
        userMapper.insert(user);
        User Registeruser = userMapper.getUser(user);
        RegisterInfo registerInfo = new RegisterInfo(Registeruser.getId(),Registeruser.getName(),Registeruser.getPassword(),Registeruser.getEmail());
        return registerInfo;
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public LoginInfo login(User user) {
        User userLogin = userMapper.getUser(user);
        if(userLogin != null){
            //1. 生成JWT令牌
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id", userLogin.getId());
            dataMap.put("name", userLogin.getName());
            dataMap.put("email", userLogin.getEmail());
            dataMap.put("password", userLogin.getPassword());

            String jwt = JwtUtils.generateJwt(dataMap);
            LoginInfo loginInfo = new LoginInfo(userLogin.getId(), userLogin.getName(), userLogin.getEmail(),userLogin.getPassword(), jwt);
            return loginInfo;
        }
        return null;
    }

    @Override
    public User getInfo(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public String getUsername(Integer Id) {
        return userMapper.getUsername(Id);
    }
}
