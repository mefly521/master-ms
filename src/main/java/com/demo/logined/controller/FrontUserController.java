package com.demo.logined.controller;

import com.demo.admin.entity.AdminUser;
import com.demo.admin.vo.UserVo;
import com.demo.front.entity.User;
import com.demo.front.service.BoardGameService;
import com.demo.front.service.UserService;
import com.demo.front.vo.BoardGameVo;
import com.demo.utils.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 首页
 */

@RestController
@RequestMapping("user")
public class FrontUserController {

    @Resource
    private UserService userService;

    @RequestMapping("/info")
    @ResponseBody
    public Object index(@RequestBody BoardGameVo boardGameVo) {
        return R.ok();
    }

    @RequestMapping("/register")
    @ResponseBody
    public Object register(@RequestBody UserVo userVo) {
        userService.register(userVo);
        return R.ok();
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User user) {
        return R.ok().setData(userService.login(user));
    }

}
