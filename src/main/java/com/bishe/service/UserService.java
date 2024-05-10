package com.bishe.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishe.exception.ServiceException;
import com.bishe.mapper.UserMapper;
import com.bishe.pojo.User;
import com.bishe.utils.PasswordUtil;
import com.bishe.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService extends  ServiceImpl<UserMapper,User>{

    @Resource
    private UserMapper userMapper;


    @Override
    public boolean save(User entity) {
        if (StrUtil.isBlank(entity.getName())) {
            entity.setName(entity.getUsername());
        }
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword(PasswordUtil.encrypt("123"));   // 默认密码123
        }
        if (StrUtil.isBlank(entity.getRole())) {
            entity.setRole("用户");   // 默认角色：用户
        }
        return super.save(entity);
    }

    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);  //  eq => ==   where username = #{username}
        // 根据用户名查询数据库的用户信息
        return getOne(queryWrapper); //  select * from user where username = #{username}
    }

    //实现一个登录功能
    public User login(User user) {
        //验证用户账户合法 检查用户名是否存在
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser==null){
            //抛出自定义异常
            throw new ServiceException("用户不存在");
        }
        if(!PasswordUtil.decrypt(user.getPassword(),dbUser.getPassword())){
            throw new ServiceException("用户名或者密码错误");
        }
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser!=null){
            //抛出自定义异常
            throw new ServiceException("用户名已存在");
        }
        user.setName(user.getUsername());
        String md5Hex = PasswordUtil.encrypt(user.getPassword());
        user.setPassword(md5Hex);
        userMapper.insert(user);
        return user;
    }


    public void resetPassword(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser==null){
            //抛出自定义异常
            throw new ServiceException("用户名不存在");
        }
        //当数据库里的密码
        if(!user.getPhone().equals(dbUser.getPhone())){
            throw new ServiceException("手机验证错误");
        }
        String md5Hex = PasswordUtil.encrypt("123");
        dbUser.setPassword(md5Hex);
        userMapper.updateById(dbUser);

    }
}
