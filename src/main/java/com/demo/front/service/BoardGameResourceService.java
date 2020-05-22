package com.demo.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.exception.RRException;
import com.demo.front.dao.BoardGameMapper;
import com.demo.front.dao.BoardGameResourceMapper;
import com.demo.front.entity.BoardGameResource;
import com.demo.front.entity.User;
import com.demo.utils.RequestThreadBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mifei
 * @create 2019-09-06 9:48
 **/
@Service
@Slf4j
public class BoardGameResourceService extends ServiceImpl<BoardGameResourceMapper, BoardGameResource> implements IService<BoardGameResource> {

    @Autowired
    private BoardGameResourceMapper boardGameResourceMapper;

    public Object selectByBgId(Long bgId) {
        User userInfo = RequestThreadBinder.getUserInfo();
        if (userInfo.getLevel() != 2) {
            throw new RRException("你的资源权限不够,不能下载");
        }
        return boardGameResourceMapper.selectOne(new QueryWrapper<BoardGameResource>().eq("bg_id",bgId));
    }
}
