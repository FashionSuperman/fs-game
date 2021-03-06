package com.fashionsuperman.fs.game.dubboxservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fashionSuperman.fs.core.util.JedisUtil;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private JedisUtil jedisUtil;
	@RequestMapping("/testRedis")
	public void testRedis(){
		jedisUtil.STRINGS.set("test", "qqqq");
		
		String test = jedisUtil.STRINGS.get("test");
		
		System.out.println(test);
	}
}
