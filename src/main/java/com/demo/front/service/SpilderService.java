package com.demo.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.front.entity.BoardGame;
import com.demo.front.dao.BoardGameMapper;
import com.demo.utils.PageUtils;
import com.demo.front.vo.BoardGameVo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author mifei
 * @create 2019-09-06 9:48
 **/
@Service
@Slf4j
public class SpilderService {
    @Autowired
    private BoardGameMapper boardGameMapper;
    @Value("${imgPath}")
    private String imgPath;

    public PageUtils page(BoardGameVo boardGameVo) {
        Page page = PageUtils.buildPage(boardGameVo.getCurrent(), boardGameVo.getSize());
        IPage iPage =  boardGameMapper.listPage(page,boardGameVo.getCnName());
        return new PageUtils(iPage);
    }

    public void downLoad(){
        for (long i = 1; i <= 19581; i++) {
            try {
                BoardGame boardGame = boardGameMapper.selectOne(new QueryWrapper<BoardGame>().eq("eid",i));
                if (boardGame != null) {
                    //downLoadImage(i);
                    boardGame.setImg("1");
                    boardGameMapper.updateById(boardGame);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void downLoadImage( long id,long eid,String suffix) throws Exception {
        log.info("下载{}==",id);
        String imgUrl ="http://image.yihubg.com/default_"+eid+suffix;
        String filename = "bg_"+id+".jpg";
        //创建文件目录
        File files = new File(imgPath);
        if (!files.exists()) {
            files.mkdirs();
        }
        //获取下载地址
        URL url = new URL(imgUrl);
        //链接网络地址
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        //获取链接的输出流
        InputStream is = connection.getInputStream();
        //创建文件，fileName为编码之前的文件名
        File file = new File(imgPath +filename);
        //根据输入流写入文件
        FileOutputStream out = new FileOutputStream(file);
        int i = 0;
        while((i = is.read()) != -1){
            out.write(i);
        }
        out.close();
        is.close();
    }

    public void print( long i) throws Exception {
        String url = "http://www.yihubg.com/game/" + i;
        log.info("开始连接 "+i);
        Connection.Response execute = Jsoup.connect(url).timeout(500000).execute();
        String html = execute.body();
        //设置超时策略
        if (execute.url().getPath().equals("/top")){
            return;
        }
        log.info("连接完成");
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("div[class=lib-row lib-header]");
        if (elements.size() == 0) {
            return;
        }
        BoardGame boardGame = new BoardGame();
        boardGame.setImg(doc.select("img[class=lib-img-show]").attr("src"));
        boardGame.setCnName(elements.get(0).ownText());
        boardGame.setEnName(elements.get(0).select("div[class=lib-header-small]").get(0).text());
        boardGame.setPublishTime(elements.get(1).text());
        Elements p = doc.select("div[class=col-md-4]").select("div[class=lib-row lib-desc]").select("p");
        boardGame.setBggNum(p.get(0).select("span").text());
        boardGame.setBggRank(p.get(2).select("span").text());
        boardGame.setPalyerNum(p.get(3).select("span").text());
        boardGame.setPalyerTime(p.get(4).select("span").text());
        boardGame.setDifficult(p.get(5).select("span").text());
        boardGame.setAge(p.get(6).select("span").text());
        boardGame.setRank(p.get(7).select("span").text());
        Elements desc = doc.select("div[class=col-md-4]")
                .select("div[class=lib-row lib-desc]")
                .select("div[class=col-md-12 long-contents]").select("p");
        boardGame.setDependent(desc.get(1).text());
        boardGame.setEid(i);
        boardGame.setId(i);

        Elements category = doc.select("div[class=col-md-5]")
                .select("div[class=lib-row lib-desc]")
                .select("div[class=col-md-12 long-contents category]").select("p");
        Elements a = category.get(1).select("a");
        String s = "";
        for (Element element : a) {
            s += element.text()+",";
        }
        boardGame.setCategory(s);
        Elements jizhi = doc.select("div[class=col-md-5]")
                .select("div[class=lib-row lib-desc]")
                .select("div").last().select("p").last().select("a");
        s = "";
        for (Element element : jizhi) {
            s += element.text()+",";
        }
        boardGame.setMechanics(s);
        log.info(boardGame.toString());

        try {
            //downLoadImage(i);
            boardGame.setImg("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        boardGameMapper.insert(boardGame);
        log.info(boardGame.getEid()+" ok!");

    }

    public static void main(String[] args) throws Exception {
        new SpilderService().print(1);
    }
}
