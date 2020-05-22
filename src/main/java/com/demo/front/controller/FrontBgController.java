package com.demo.front.controller;

import com.demo.front.service.BoardGameService;
import com.demo.front.service.SpilderService;
import com.demo.utils.R;
import com.demo.front.vo.BoardGameVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 首页
 */

@RestController
@RequestMapping("front/bg")
public class FrontBgController {

    @Resource
    private BoardGameService boardGameService;

    /**
     * 首页分页
     */
    @RequestMapping("/page")
    @ResponseBody
    public Object index(@RequestBody BoardGameVo boardGameVo) {
        return R.ok().setData(boardGameService.page(boardGameVo));
    }

    @RequestMapping("/resourceCount")
    @ResponseBody
    public Object resourceCount() {
        return R.ok().setData(boardGameService.resourceCount());
    }

}
