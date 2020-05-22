package com.demo.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author mifei
 * @create 2019-11-08 13:17
 **/
@Data
public class AdminUser {

    @TableId( type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;

}
