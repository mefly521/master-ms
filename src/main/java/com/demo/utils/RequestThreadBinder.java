package com.demo.utils;

import com.demo.front.entity.User;

/**
 * @Description:线程绑定类
 * @Author: mifei
 * @Date: 2018-10-18
 **/
public class RequestThreadBinder {
    // 声明当前线程 指定泛型为request
    private static ThreadLocal<User> local = new ThreadLocal<>();

    // 将request绑定到当前线程的方法
    public static void bindUser(User request) {
        local.set(request);
    }

    // 从当前线程获取request的方法
    public static User getUser() {
        return local.get();
    }

    // 从当前线程移除request的方法
    public static void removeUser() {
        local.remove();
    }

    public static User getUserInfo() {
        User user = RequestThreadBinder.getUser();
        return user;
    }
}
