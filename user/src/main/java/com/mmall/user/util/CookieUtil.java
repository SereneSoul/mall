package com.mmall.user.util;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = ".mall.com";
    private final static String COOKIE_NAME = "login_token";

    public static void writeLoginToken(HttpServletResponse httpServletResponse,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");
        ck.setMaxAge(60 * 60 * 6);
        httpServletResponse.addCookie(ck);
    }

    public static String readLoginToken(HttpServletRequest httpServletRequest){
        Cookie [] cks = httpServletRequest.getCookies();
        if(cks != null){
            for (Cookie ck : cks) {
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void delLoginToken(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        Cookie [] cks = httpServletRequest.getCookies();
        if(cks != null){
            for (Cookie ck : cks) {
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);
                    httpServletResponse.addCookie(ck);
                    return;
                }
            }
        }
    }
}
