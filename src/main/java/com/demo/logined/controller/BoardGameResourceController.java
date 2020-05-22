package com.demo.logined.controller;

import com.demo.front.service.BoardGameResourceService;
import com.demo.front.service.UserService;
import com.demo.utils.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("boardGameResource")
public class BoardGameResourceController {

    @Resource
    private BoardGameResourceService boardGameResourceService;

    @RequestMapping("/resource/{bgId}")
    @ResponseBody
    public Object index(@PathVariable("bgId") Long bgId) {
        return R.ok().setData(boardGameResourceService.selectByBgId(bgId));
    }

}
