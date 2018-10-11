package com.mmall.user.controller;

import com.mmall.user.common.Const;
import com.mmall.user.common.ResponseResult;
import com.mmall.user.entity.User;
import com.mmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends AppController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseResult login(String username, String password, HttpSession httpSession, HttpServletResponse httpServletResponse){
        ResponseResult result = userService.selectByUsername(username,password);
        if (result.isSuccess()) {
            setLoginUser(httpSession.getId(), result.getData(), Const.RedisExTime);
        }
        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseResult logout(HttpSession httpSession, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        ResponseResult result = new ResponseResult();
        try{
            redisUtil.del(httpSession.getId());
            result.setSuccess(true);
            result.setMsg("退出登录成功！");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("退出登录失败！" + e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseResult register(User user){
        ResponseResult result = userService.insert(user);
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseResult delete(User user,HttpSession httpSession){
        ResponseResult result = new ResponseResult();
        User loginUser = getLoginUser(httpSession.getId());
        if (loginUser != null){
            if(loginUser.getRole() == 1){
                result = userService.deleteByPrimaryKey(user.getId());
            }else{
                result.setSuccess(false);
                result.setMsg("无权限删除用户信息！");
            }
        }else{
            result.setSuccess(false);
            result.setMsg("请登录后删除用户信息！");
        }
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseResult update(User user,HttpSession httpSession){
        ResponseResult result = new ResponseResult();
        User loginUser = getLoginUser(httpSession.getId());
        if (loginUser != null){
            if(loginUser.getRole() == 1 || loginUser.getId() == user.getId()){
                result = userService.updateByPrimaryKeySelective(user);
            }else{
                result.setSuccess(false);
                result.setMsg("无权限更新用户信息！");
            }
        }else{
            result.setSuccess(false);
            result.setMsg("请登录后更新用户信息！");
        }
        return result;
    }

    @RequestMapping(value = "/queryUserList",method = RequestMethod.GET)
    public ResponseResult queryUserList(HttpSession httpSession, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        ResponseResult result = new ResponseResult();
        User user = getLoginUser(httpSession.getId());
        if(user != null){
            result = userService.selectUserList();
        }else{
            result.setSuccess(false);
            result.setMsg("请登录后再查询");
        }
        return result;
    }
}
