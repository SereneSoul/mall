package com.mmall.user.controller;

import com.mmall.user.entity.User;
import com.mmall.user.util.RedisUtil;
import com.mmall.user.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AppController {
    @Autowired
    protected RedisUtil redisUtil;

    protected User getLoginUser(String sessionId){
        return JsonUtil.stringToObj((String) redisUtil.get(sessionId),User.class);
    }

    protected boolean setLoginUser(String sessionId, Object obj, long time){
        return redisUtil.set(sessionId, JsonUtil.objToString(obj), time);
    }

}
