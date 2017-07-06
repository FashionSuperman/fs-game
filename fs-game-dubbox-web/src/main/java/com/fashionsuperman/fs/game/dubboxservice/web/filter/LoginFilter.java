package com.fashionsuperman.fs.game.dubboxservice.web.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fashionSuperman.fs.core.util.JedisUtil;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 登录校验过滤器
 * 
 * @description
 * @author FashionSuperman
 * @date 2017年6月30日 下午1:07:33
 * @version 1.0
 */
public class LoginFilter implements Filter {
	private Logger logger = LogManager.getLogger(LoginFilter.class);
//	private String loginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx324313fc147c74e2&redirect_uri=http%3a%2f%2fwww.fashionsuperman.top%2fifyoudog%2ffs-game-service%2fUser%2floginwx&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	private String loginUrl = "";

	private JedisUtil jedisUtil;
	private JedisPool jedisPool;
	
	private Properties propertiesRedis;
	private Properties propertiesUtil;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 读取redis配置文件
		propertiesRedis = new Properties();
		propertiesUtil = new Properties();
		try {
			propertiesRedis.load(new FileInputStream(LoginFilter.class.getResource("/redis.properties").getPath()));
			propertiesUtil.load(new FileInputStream(LoginFilter.class.getResource("/util.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 初始化jedisUtil
		jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisPool);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		jedisPool = new JedisPool(jedisPoolConfig, propertiesRedis.getProperty("redis.hostname"),
				Integer.parseInt(propertiesRedis.getProperty("redis.port")), 60000);
		
		
		this.loginUrl = this.propertiesUtil.getProperty("loginUrl");
		String appid = this.propertiesUtil.getProperty("appid");
		String redirect_uri = this.propertiesUtil.getProperty("redirect_uri");
		
		this.loginUrl = String.format(this.loginUrl, appid,redirect_uri);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String requestUri = httpServletRequest.getRequestURI();
		logger.info("请求路径: " + requestUri);
		if (requestUri.contains("loginwx")) {
			chain.doFilter(request, response);
			return;
		}

		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies == null || cookies.length == 0) {
			// 跳转登录
			httpServletResponse.sendRedirect(loginUrl);
			return;
		}
		boolean has = false;
		for (Cookie c : cookies) {
			if ("sessionId".equals(c.getName())) {
				has = true;
				break;
			}
		}

		if (!has) {
			// 跳转登录
			httpServletResponse.sendRedirect(loginUrl);
			return;
		}else{
			chain.doFilter(request, response);
			return;
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
