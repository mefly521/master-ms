package com.demo.front.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.common.exception.RRException;
import com.demo.front.dao.BoardGameMapper;
import com.demo.front.entity.BoardGame;
import com.demo.front.vo.BoardGameVo;
import com.demo.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.Date;

/**
 * @author mifei
 * @create 2019-09-06 9:48
 **/
@Service
@Slf4j
public class BoardGameService {
    @Autowired
    private BoardGameMapper boardGameMapper;

    public PageUtils page(BoardGameVo boardGameVo) {
        Page page = PageUtils.buildPage(boardGameVo.getCurrent(), boardGameVo.getSize());
        IPage iPage =  boardGameMapper.listPage(page,boardGameVo.getCnName());
        return new PageUtils(iPage);
    }

    public void update(BoardGame boardGame) {
        if (StrUtil.isNotEmpty(boardGame.getResource()) ){
            boardGame.setUpdateTime(new Date());
        }
        boardGameMapper.updateById(boardGame);
    }

    public void insert(BoardGame boardGame) {
        if (StrUtil.isNotEmpty(boardGame.getResource()) ){
            boardGame.setUpdateTime(new Date());
        }
        if (StrUtil.isEmpty(boardGame.getCnName()) && StrUtil.isEmpty(boardGame.getEnName())){
            throw new RRException("中文名称和英文名称不能全部为空");
        }
        boardGameMapper.insert(boardGame);
    }

    public Integer resourceCount() {
        return boardGameMapper.selectCount(new QueryWrapper<BoardGame>().isNotNull("resource"));
    }

    public BoardGame selectById(Long id) {
       return boardGameMapper.selectById(id);
    }

}
