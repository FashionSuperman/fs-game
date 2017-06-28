package com.fashionsuperman.fs.game.dubboxService.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import com.alibaba.dubbo.rpc.RpcContext;
import com.fashionSuperman.fs.core.util.JedisUtil;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class LoginFilter implements ContainerResponseFilter{
	
	private String loginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx324313fc147c74e2&redirect_uri=http%3a%2f%2fwww.fashionsuperman.top%2fifyoudog%2fUser%2floginwx&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	
	private JedisUtil jedisUtil;
	private JedisPool jedisPool;
	public LoginFilter() throws FileNotFoundException, IOException {
		//读取redis配置文件
		Properties properties = new Properties();
		properties.load(new FileInputStream(LoginFilter.class.getResource("/redis.properties").toString()));
		
		//初始化jedisUtil
		jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisPool);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		
		jedisPool = new JedisPool(jedisPoolConfig, 
				properties.getProperty("redis.hostname"), Integer.parseInt(properties.getProperty("redis.port")), 
				60000);
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		HttpServletRequest request = (HttpServletRequest) requestContext.getRequest();
		HttpServletResponse httpServletResponse = (HttpServletResponse) RpcContext.getContext().getResponse();
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null || cookies.length == 0){
			//跳转登录
			httpServletResponse.sendRedirect(loginUrl);
		}
		boolean has = false;
		for(Cookie c : cookies){
			if("sessionId".equals(c.getName())){
				has = true;
				break;
			}
		}
		
		if(!has){
			//跳转登录
			httpServletResponse.sendRedirect(loginUrl);
		}
	}

}
