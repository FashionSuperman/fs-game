package com.fashionsuperman.fs.game.dubboxService.test;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.util.JedisUtil;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.facet.test.TestRedisI;

@Path("/TestRedis")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("TestRedis")
public class TestRedis implements TestRedisI{
	@Autowired
	private JedisUtil jedisUtil;
	
	@POST
	@Path("/test1")
	@Override
	public String test1() {
		jedisUtil.STRINGS.set("test", "qqqq");
		
		String test = jedisUtil.STRINGS.get("test");
		
		System.out.println(test);
		
		return "";
	}
	
	@POST
	@Path("/testRedirect")
	@Override
	public User testRedirect() throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) RpcContext.getContext().getResponse();
		
		httpServletResponse.sendRedirect("http://www.baidu.com");
		
		User user = new User();
		user.setAccountname("hello");
		return user;
	}
	
}
