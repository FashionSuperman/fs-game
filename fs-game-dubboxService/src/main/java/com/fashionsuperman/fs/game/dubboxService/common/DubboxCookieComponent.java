package com.fashionsuperman.fs.game.dubboxService.common;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.RpcContext;
import com.fashionSuperman.fs.core.util.JedisUtil;
import com.fashionsuperman.fs.game.facet.user.message.UserLogin;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DubboxCookieComponent {
	@Autowired
	private JedisUtil jedisUtil;
	
	/**
	 * 判断用户是否登录,如果已登录,返回登录的用户信息,如果未登录,返回null
	 * @return
	 */
	public UserLogin getLoginUser(){
		UserLogin result = null;
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
		
		Cookie[] cookies = httpServletRequest.getCookies();
		Cookie cookie = null;
		if (cookies == null || cookies.length == 0) {//未登录
			return result;
		}
		
		for (Cookie c : cookies) {
			if ("sessionId".equals(c.getName())) {
				cookie = c;
				break;
			}
		}
		
		if(cookie == null){//未登录
			return result;
		}
		
		String sessionId = cookie.getValue();
		String userInRedis = this.jedisUtil.STRINGS.get(sessionId);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		UserLogin user = null;
		try {
			user = mapper.readValue(userInRedis, UserLogin.class);
			result = user;
		} catch (IOException e) {
			return result;
		}
		
		return result;
	}
	
	
	/**
	 * 根据cookie名称获取cookie
	 * @param cookieName
	 * @return
	 */
	public Cookie getCookie(String cookieName){
		Cookie result = null;
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
		
		Cookie[] cookies = httpServletRequest.getCookies();
		Cookie cookie = null;
		if (cookies == null || cookies.length == 0) {//未登录
			return result;
		}
		
		for (Cookie c : cookies) {
			if (cookieName.equals(c.getName())) {
				cookie = c;
				break;
			}
		}
		
		if(cookie == null){//未登录
			return result;
		}else
			return cookie;
	}
}
