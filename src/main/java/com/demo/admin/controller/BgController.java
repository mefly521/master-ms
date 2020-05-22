package com.demo.admin.controller;

import com.demo.front.entity.BoardGame;
import com.demo.front.service.BoardGameService;
import com.demo.front.service.SpilderService;
import com.demo.front.vo.BoardGameVo;
import com.demo.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 */

@RestController
@RequestMapping("admin/boardgame")
public class BgController {

    @Resource
    private BoardGameService boardGameService;

    /**
     * 分页
     */
    @RequestMapping("/page")
    @ResponseBody
    public Object page(@RequestBody BoardGameVo boardGameVo) {
        return R.ok().setData(boardGameService.page(boardGameVo));
    }
    @RequestMapping("/update")
    @ResponseBody
    public Object update(@RequestBody BoardGame boardGame) {
        boardGameService.update(boardGame);
        return R.ok();
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@RequestBody BoardGame boardGame) {
        boardGameService.insert(boardGame);
        return R.ok();
    }
    @RequestMapping("/{id}")
    @ResponseBody
    public Object selectById(@PathVariable("id") Long id) {
        return R.ok().setData(boardGameService.selectById(id));
    }

}
