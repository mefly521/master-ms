package com.demo.front.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.front.entity.BoardGame;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 */
@Mapper
public interface BoardGameMapper extends BaseMapper<BoardGame> {


    @Select(" select * from board_game where cn_name like '%${name}%' or en_name like '%${name}%' order by update_time desc,cn_name desc  ")
    IPage<BoardGame> listPage(Page page, @Param("name") String name);


    @Select(" SELECT MAX(eid) FROM `board_game`   ")
    Long maxId();
}
