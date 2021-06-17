package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
/*
 * Cookie 是从 HttpServletRequest 对象里面取出来的，
 * 因为很多情况下都需要获获取 Cookie，所以写一个工具类
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        if (httpServletRequest == null || name == null) {
            throw new IllegalArgumentException("参数为空！");
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
