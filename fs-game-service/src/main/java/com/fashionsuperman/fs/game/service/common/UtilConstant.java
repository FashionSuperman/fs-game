package com.fashionsuperman.fs.game.service.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilConstant {
	@Value("#{utilProperties.appid}")
	public String appid;
	@Value("#{utilProperties.secret}")
	public String secret;
	@Value("#{utilProperties.redirect_uri}")
	public String redirect_uri;
	@Value("#{utilProperties.loginUrl}")
	public String loginUrl;
	@Value("#{utilProperties.getAccessTokenUrl}")
	public String getAccessTokenUrl;
	@Value("#{utilProperties.getUserinfoUrl}")
	public String getUserinfoUrl;
	@Value("#{utilProperties.unifiedorderUrl}")
	public String unifiedorderUrl;
	@Value("#{utilProperties.mch_id}")
	public String mch_id;
	@Value("#{utilProperties.notify_url}")
	public String notify_url;
	@Value("#{utilProperties.wxkey}")
	public String wxkey;
	@Value("#{utilProperties.ifyoudogindex}")
	public String ifyoudogindex;
	
	
	/**
	 * 是否允许不登录可以玩
	 * 1表示必须登录
	 * 0表示不需要
	 */
	@Value("#{utilProperties.shouldLogin}")
	public String shouldLogin;
}
