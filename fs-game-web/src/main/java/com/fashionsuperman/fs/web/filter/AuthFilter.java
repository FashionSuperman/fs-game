package com.fashionsuperman.fs.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fashionSuperman.fs.core.util.StringUtil;
/**
 * 登录校验
 * @description 
 * @author FashionSuperman
 * @date 2017年5月22日 上午9:04:20
 * @version 1.0
 */
public class AuthFilter implements Filter{
	private String excludedPages;
	private String[] excludedPagesArr;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		
		String url = httpServletRequest.getRequestURI();
		
		if(excludedPagesArr != null && excludedPagesArr.length > 0){
			for(String s : excludedPagesArr){
				if(url.endsWith(s)){
					chain.doFilter(httpServletRequest, httpServletResponse);
					return;
				}
			}
		}
		
		if(url.contains("login")){
			chain.doFilter(httpServletRequest, httpServletResponse);
		}else{
			HttpSession session = httpServletRequest.getSession();
			String userId = (String) session.getAttribute("userId");
			
			if(StringUtil.isNullOrEmpty(userId)){
				//跳转登录页
				httpServletResponse.sendRedirect(httpServletRequest.getServletContext().getContextPath() + "/login.jsp");
			}else{
				chain.doFilter(httpServletRequest, httpServletResponse);
			}
		}
		
		
	}

	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludedPages = filterConfig.getInitParameter("excludedPages");
		if(StringUtil.isNotEmpty(excludedPages)){
			excludedPagesArr = excludedPages.split(",");
		}
		
	}
	@Override
	public void destroy() {
	}

}
