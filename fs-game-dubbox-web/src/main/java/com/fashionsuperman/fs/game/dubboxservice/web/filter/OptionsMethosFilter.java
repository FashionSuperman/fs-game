package com.fashionsuperman.fs.game.dubboxservice.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * options方发过滤器
 * @description 
 * @author FashionSuperman
 * @date 2017年5月3日 下午4:44:51
 * @version 1.0
 */
public class OptionsMethosFilter implements Filter{

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if(httpServletRequest.getMethod().equals("OPTIONS")){
			httpServletResponse.setStatus(204);
			httpServletResponse.setHeader("Access-Control-Allow-Method", "GET,POST");
			httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			httpServletResponse.flushBuffer();
		}else
			chain.doFilter(httpServletRequest, httpServletResponse);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
