package com.demo.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.front.dao.BoardGameMapper;
import com.demo.front.entity.BoardGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author mifei
 * @create 2020-05-08 15:01
 **/
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Autowired
    private SpilderService spilderService;
    @Autowired
    private BoardGameMapper boardGameMapper;
    @Value("${autoRun}")
    private int autoRun;
    @Value("${imgPath}")
    private String imgPath;

//    @Override
//    public void run(String... args) throws Exception {
//        if (autoRun != 1) {
//            return;
//        }
//        System.out.println("开始自动执行==============");
//        Long max = boardGameMapper.maxId();
//        for (long i = max+1; i <= 100_0000; i++) {
//            boolean flag = true;
//            while (flag) {
//                try {
//                    spilderService.print(i);
//                    break;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    //补录下载图片
    @Override
    public void run(String... args) throws Exception {
        if (autoRun != 1) {
            return;
        }
        System.out.println("开始执行");
        Long max = boardGameMapper.maxId();
        for (long i = 0; i <= max; i++) {
            System.out.println("id==" + i);
            BoardGame boardGame = boardGameMapper.selectOne(new QueryWrapper<BoardGame>().eq("id", i));
            if (boardGame != null) {
                File file = new File(imgPath + "bg_" + i + ".jpg");
                if (file.exists()) {
                    continue;
                }
                System.out.println("下载==" + i);

                boolean flag = false;
                try {
                    spilderService.downLoadImage(i, boardGame.getEid(), ".jpg");
                    flag = true;
                } catch (Exception e) {
                    System.out.println("下载jpg出错");
                    e.printStackTrace();
                }
                try {
                    spilderService.downLoadImage(i, boardGame.getEid(), ".png");
                    flag = true;
                } catch (Exception e) {
                    System.out.println("下载png出错");
                    e.printStackTrace();
                }
                if (flag){
                    boardGame.setImg("1");
                    boardGameMapper.updateById(boardGame);
                }
            }

        }
    }
}
