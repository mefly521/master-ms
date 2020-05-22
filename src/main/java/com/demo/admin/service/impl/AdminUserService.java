package com.demo.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.admin.dao.AdminUserMapper;
import com.demo.admin.entity.AdminUser;
import com.demo.admin.vo.AdminUserVo;
import com.demo.common.exception.RRException;
import com.demo.utils.CacheUtil;
import com.demo.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mifei
 * @create 2019-09-06 9:48
 **/
@Service
@Slf4j
public class AdminUserService {
    @Autowired
    private AdminUserMapper userMapper;
    @Autowired
    private CacheUtil cacheUtil;

    public void login(AdminUser adminUserVo){
        AdminUser adminUser = userMapper.selectOne(new QueryWrapper<AdminUser>().eq("username", adminUserVo.getUsername()));
        if ((adminUser == null)
                || (adminUser != null && !SecureUtil.md5(adminUserVo.getPassword()).equals(adminUser.getPassword()))) {
            throw new RRException("用户名密码错误");
        }
        String token = SecureUtil.simpleUUID();
        token = StrUtil.format("bg:admin_user:token:{}:{}", adminUserVo.getId(), token);
        cacheUtil.set(token, adminUser.getId(), 60 * 60 * 24 * 7);
    }

    public AdminUser info(AdminUser adminUserVo){
        return  userMapper.selectById(1);
    }

    public PageUtils list(AdminUserVo adminUserVo){
        Page page = PageUtils.buildPage(adminUserVo.getCurrent(), adminUserVo.getSize());
        IPage iPage = userMapper.selectPage(page, new QueryWrapper<AdminUser>().like(StrUtil.isNotEmpty(adminUserVo.getUsername()),"username",adminUserVo.getUsername()));
        return new PageUtils(iPage);
    }

}
