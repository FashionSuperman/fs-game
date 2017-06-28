package com.fashionsuperman.fs.game.dubboxservice.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fashionSuperman.fs.core.util.JedisUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedis extends AbstractContextControllerTest{
	@Autowired
	private JedisUtil jedisUtil;
	
	@Test
	public void testJedis(){
		jedisUtil.STRINGS.set("test", "qqqq");
		
		String test = jedisUtil.STRINGS.get("test");
		
		System.out.println(test);
		
	}
}
