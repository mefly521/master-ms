package com.demo.front.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author mifei
 * @create 2019-11-08 13:17
 **/
@Data
public class BoardGameResource {

    @TableId( type = IdType.AUTO)
    private Long id;
    private Long bgId;
    private String resource;

}
