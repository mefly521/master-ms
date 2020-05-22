package com.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.front.dao.BoardGameMapper;
import com.demo.front.entity.BoardGame;
import com.demo.front.service.SpilderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTests {
	@Autowired
	private SpilderService spilderService;
	@Autowired
	private BoardGameMapper boardGameMapper;
	@Test
	public void test1(){
		//spilderService.clean();
		Long max = boardGameMapper.maxId();
		for (long i = max+1; i <= 1000000; i++) {
			boolean flag = true;
			while (flag) {
				try {
					spilderService.print(i);
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Test
	public void downLoad(){
		for (long i = 0; i <= 19581; i++) {
			try {
				BoardGame boardGame = boardGameMapper.selectOne(new QueryWrapper<BoardGame>().eq("eid",i));
				if (boardGame != null) {
					//spilderService.downLoadImage(i);
					boardGame.setImg("1");
					boardGameMapper.updateById(boardGame);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
