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
public class BoardGame {

    @TableId( type = IdType.AUTO)
    private Long id;
    private Long eid;
    private String cnName;
    private String enName;
    private String palyerNum;
    private String palyerTime;
    private String publishTime;
    private String difficult;
    private String age;

    private String rank;//专业评分
    private String dependent;//语言依赖
    private String bggNum;//bgg排名
    private String bggRank;//bgg评分
    private String publisher;//
    private String designer;//
    private String art;//
    private String category;//
    private String mechanics;//
    private String img;//
    private Integer level;//
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String resource;//
}
