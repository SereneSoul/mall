package com.mmall.user.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mmall.user.common.ResponseResult;
import com.mmall.user.dao.UserMapper;
import com.mmall.user.entity.User;
import com.mmall.user.service.UserService;
import com.mmall.user.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public ResponseResult deleteByPrimaryKey(Integer id) {
        ResponseResult result = new ResponseResult();
        try{
            int count = userMapper.deleteByPrimaryKey(id);
            if(count > 0){
                result.setSuccess(true);
                result.setMsg("用户删除成功！");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("用户删除失败！" + e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public ResponseResult insert(User record) {
        ResponseResult result = new ResponseResult();
        try {
            User user = userMapper.selectByUsername(record.getUsername());
            if(user == null){
                String password = MD5Util.MD5EncodeUtf8(record.getPassword());
                record.setPassword(password);
                int count = userMapper.insert(record);
                if(count > 0){
                    result.setSuccess(true);
                    result.setMsg("用户注册成功！");
                }
            }else{
                result.setSuccess(false);
                result.setMsg("用户名已存在！");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public ResponseResult updateByPrimaryKeySelective(User record) {
        ResponseResult result = new ResponseResult();
        result.setSuccess(false);
        try{
            record.setPassword(null);
            int count = userMapper.updateByPrimaryKeySelective(record);
            if(count > 0){
                result.setSuccess(true);
                result.setMsg("用户资料修改成功！");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("用户资料修改失败！" + e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public ResponseResult updateByPrimaryKey(User record) {
        ResponseResult result = new ResponseResult();
        result.setSuccess(false);
        try{
            int count = userMapper.updateByPrimaryKey(record);
            if(count > 0){
                result.setSuccess(true);
                result.setMsg("用户资料修改成功！");
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("用户资料修改失败！" + e.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResult selectByUsername(String username,String password) {
        ResponseResult result = new ResponseResult();
        result.setSuccess(false);
        try{
            User user = userMapper.selectByUsername(username);
            String realPassword = MD5Util.MD5EncodeUtf8(password);
            if(user == null){
                throw new RuntimeException("请检查用户名或密码是否正确！");
            }
            if(!StringUtils.equals(user.getPassword(),realPassword)){
                throw new RuntimeException("请检查用户名或密码是否正确！");
            }
            result.setSuccess(true);
            user.setPassword(null);
            result.setData(user);
        }catch (Exception e){
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public ResponseResult selectUserList() {
        ResponseResult result = new ResponseResult();
        result.setSuccess(false);
        try{
            List<User> list = userMapper.selectUserList();
            result.setSuccess(true);
            result.setData(list);
        }catch (Exception e){
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
