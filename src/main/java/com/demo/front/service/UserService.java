package com.demo.front.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.admin.entity.AdminUser;
import com.demo.admin.vo.UserVo;
import com.demo.common.Constant;
import com.demo.common.exception.RRException;
import com.demo.front.dao.UserMapper;
import com.demo.front.entity.User;
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
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CacheUtil cacheUtil;

    public PageUtils list(UserVo userVo){
        Page page = PageUtils.buildPage(userVo.getCurrent(), userVo.getSize());
        IPage iPage = userMapper.selectPage(page, new QueryWrapper<>());
        return new PageUtils(iPage);
    }

    public void register(UserVo userVo) {
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("username", userVo.getUsername()));
        if (count > 0) {
            throw new RRException("此用户名已经被注册");
        }
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(SecureUtil.md5(userVo.getPassword()));
        userMapper.insert(user);
    }

    public User login(User userVo){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", userVo.getUsername()));
        if ((user == null)
                || (user != null && !SecureUtil.md5(userVo.getPassword()).equals(user.getPassword()))) {
            throw new RRException("用户名密码错误");
        }
        String token = SecureUtil.simpleUUID();
        user.setToken(token);
        token = StrUtil.format(Constant.USER_WEB_TOKEN,token);
        user.setPassword(null);
        cacheUtil.set(token, JSONObject.toJSONString(user), 60 * 60 * 24 * 15);
        return user;
    }
}
