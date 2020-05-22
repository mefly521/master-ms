package com.demo.admin.controller;

import com.demo.admin.vo.UserVo;
import com.demo.front.entity.BoardGame;
import com.demo.front.entity.User;
import com.demo.front.service.UserService;
import com.demo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页
 */

@RestController
@RequestMapping("admin")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    @ResponseBody
    public Object list() {
        UserVo userVo = new UserVo();
        return R.ok().setData(userService.list(userVo));
    }

    @RequestMapping("/user/update")
    @ResponseBody
    public Object update(@RequestBody User user) {
        userService.updateById(user);
        return R.ok();
    }

    @RequestMapping("/user/{id}")
    @ResponseBody
    public Object selectById(@PathVariable("id") Long id) {
        return R.ok().setData(userService.getById(id));
    }

}
