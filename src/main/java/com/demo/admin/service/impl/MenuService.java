package com.demo.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.admin.dao.MenuMapper;
import com.demo.admin.entity.AdminUser;
import com.demo.admin.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mifei
 * @create 2019-09-06 9:48
 **/
@Service
@Slf4j
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> list(Menu menu){
        return menuMapper.selectList(new QueryWrapper<>());
    }

}
