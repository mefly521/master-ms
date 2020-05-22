package com.demo.admin.controller;

import com.demo.admin.entity.AdminUser;
import com.demo.admin.service.impl.MenuService;
import com.demo.admin.service.impl.AdminUserService;
import com.demo.admin.vo.AdminUserVo;
import com.demo.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * 首页
 */

@RestController
@RequestMapping("admin")
public class AdminUserController {

    @Autowired
    private AdminUserService userService;
    @Autowired
    private MenuService menuService;

    /**
     * 首页分页
     */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestBody AdminUser adminUser) {
        userService.login(adminUser);
        return R.ok();
    }

    @RequestMapping("/adminUser/info")
    @ResponseBody
    public Object info() {
        return R.ok().setData(userService.info(null));
    }

    @RequestMapping("/adminUser/list")
    @ResponseBody
    public Object list(@RequestBody AdminUserVo adminUserVo) {
        return R.ok().setData(userService.list(adminUserVo));
    }

    @RequestMapping("/menu/nav")
    @ResponseBody
    public Object nav() {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:list");
        return R.ok().put("menuList", menuService.list(null)).put("permissions", permissions);
    }
}
