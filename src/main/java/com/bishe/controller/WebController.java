package com.bishe.controller;

import cn.hutool.core.util.StrUtil;
import com.bishe.common.AuthAccess;
import com.bishe.common.Result;
import com.bishe.pojo.User;
import com.bishe.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Resource
    UserService userService;

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //如果用户名或者密码为空返回数据输入不合法
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.success("数据输入不合法");
        }
        user=userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.success("数据输入不合法");
        }
        user=userService.register(user);
        return Result.success(user);
    }

    @AuthAccess
    @PutMapping("/password")
    public Result forget(@RequestBody User user){

        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPhone())){
            return Result.error("传入数据不合法");
        }
        userService.resetPassword(user);
        return Result.success();
    }
}