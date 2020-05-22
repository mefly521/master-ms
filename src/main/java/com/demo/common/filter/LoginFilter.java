package com.demo.common.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.Constant;
import com.demo.front.entity.User;
import com.demo.utils.CacheUtil;
import com.demo.utils.R;
import com.demo.utils.RequestThreadBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 在SpringBoot中通过注解注册的方式简单的使用Filter
 *
 * @author chengxi
 */
@WebFilter(filterName = "LoginFilter")
@Configuration
public class LoginFilter implements Filter {

    @Autowired
    private CacheUtil cacheUtil;
    private static String[] noFilterUrl = {"/user/register","/user/login","/front/bg/","/admin/"};

    private boolean isInclude(String url) {
        for (String pattern : noFilterUrl) {
            if (url.indexOf(pattern) != -1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token");
        String token = request.getHeader("token");
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        if (!isInclude(requestURI)) {// note white list
            if (StrUtil.isBlank(token)) {
                responseOutWithJson(response, new R().error(HttpStatus.UNAUTHORIZED.value(), "未授权访问"));
            }
            token = StrUtil.format(Constant.USER_WEB_TOKEN, token);
            String user = (String)cacheUtil.get(token);
            if (user == null) {
                responseOutWithJson(response, new R().error(HttpStatus.UNAUTHORIZED.value(), "登录会话失效,请重新登录"));
                return;
            }
            if (user == null) {
                responseOutWithJson(response, new R().error(HttpStatus.UNAUTHORIZED.value(), "登录会话失效,请重新登录"));
                return;
            }
            User userInfo = JSONObject.parseObject(user, User.class);
            RequestThreadBinder.bindUser(userInfo);
        }
        chain.doFilter(servletRequest, res);
    }

    @Override
    public void destroy() {

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {
        //将实体对象转换为JSON Object转换
        String jsonString = JSONObject.toJSONString(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}