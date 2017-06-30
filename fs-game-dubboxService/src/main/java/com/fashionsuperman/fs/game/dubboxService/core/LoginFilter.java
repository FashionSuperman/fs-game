package com.fashionsuperman.fs.game.dubboxService.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fashionSuperman.fs.core.util.JedisUtil;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class LoginFilter implements ContainerRequestFilter {

	private Logger logger = LogManager.getLogger(LoginFilter.class);

	private String loginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx324313fc147c74e2&redirect_uri=http%3a%2f%2fwww.fashionsuperman.top%2fifyoudog%2fUser%2floginwx&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	private JedisUtil jedisUtil;
	private JedisPool jedisPool;

	public LoginFilter() throws FileNotFoundException, IOException {
		// 读取redis配置文件
		Properties properties = new Properties();
		properties.load(new FileInputStream(LoginFilter.class.getResource("/redis.properties").getPath()));

		// 初始化jedisUtil
		jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisPool);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

		jedisPool = new JedisPool(jedisPoolConfig, properties.getProperty("redis.hostname"),
				Integer.parseInt(properties.getProperty("redis.port")), 60000);
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UriInfo uriInfo = requestContext.getUriInfo();

		String requestUri = uriInfo.getPath();
		logger.info("请求路径: " + requestUri);
		if (requestUri.contains("loginwx")) {
			return;
		}

		Map<String, javax.ws.rs.core.Cookie> cookieMap = requestContext.getCookies();
		Collection<javax.ws.rs.core.Cookie> cookies = cookieMap.values();

		if (cookies == null || cookies.size() == 0) {
			// 跳转登录
			try {
				MultivaluedMap<String, String> headers = requestContext.getHeaders();
				headers.add("jump", "true");
				requestContext.abortWith(Response.temporaryRedirect(new URI(loginUrl)).build());
				return;
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		boolean has = false;
		for (javax.ws.rs.core.Cookie c : cookies) {
			if ("sessionId".equals(c.getName())) {
				has = true;
				break;
			}
		}

		if (!has) {
			// 跳转登录
			try {
				MultivaluedMap<String, String> headers = requestContext.getHeaders();
				headers.add("jump", "true");
				
				requestContext.abortWith(Response.temporaryRedirect(new URI(loginUrl)).build());
				return;
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}

}
