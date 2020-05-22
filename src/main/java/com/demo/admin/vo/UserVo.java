package com.demo.admin.vo;

import com.demo.utils.PageVo;
import lombok.Data;

/**
 * @author mifei
 * @create 2019-11-08 13:17
 **/
@Data
public class UserVo extends PageVo {

    private String username;
    private String password;

}
