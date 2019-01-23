package com.mmall.user.service;

import com.mmall.user.common.ResponseResult;
import com.mmall.user.entity.User;

public interface UserService {
    ResponseResult deleteByPrimaryKey(Integer id);

    ResponseResult insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    ResponseResult updateByPrimaryKeySelective(User record);

    ResponseResult updateByPrimaryKey(User record);

    ResponseResult selectByUsername(String username,String password);

    ResponseResult selectUserList();
    
    ResponseResult forgetGetQuestion(String username);
    
    ResponseResult forgetCheckAnswer(String username, String answer);

    ResponseResult forgetRestPassword(String username, String password, String token);

    ResponseResult resetPassword(String username, String oldPassword, String newPassword);
}
