package com.frank.revaluebackend.controller;


import com.frank.revaluebackend.pojo.LoginInfo;
import com.frank.revaluebackend.pojo.RegisterInfo;
import com.frank.revaluebackend.pojo.Result;
import com.frank.revaluebackend.pojo.User;
import com.frank.revaluebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        log.info("员工来注册啦 , {}", user);
        String email = user.getEmail();

        User user_ = userService.getUserByEmail(email);
        if(user_!=null){
            return Result.error("该用户已注册！");
        }
        RegisterInfo registerInfo = userService.register(user);
        if(registerInfo != null){
            return Result.success(registerInfo);
        }
        return Result.error("注册失败！");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("员工来登录啦 , {}", user);
        LoginInfo loginInfo = userService.login(user);
        if(loginInfo != null){
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误~");
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable String id){
        log.info("根据用户名查询用户的详细信息");
        Integer id_ = Integer.valueOf(id);
        User user  = userService.getInfo(id_);
        return Result.success(user);
    }

    /**
     * 更新员工信息
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        log.info("修改个人信息, {}", user);
        userService.update(user);
        return Result.success();
    }
}
