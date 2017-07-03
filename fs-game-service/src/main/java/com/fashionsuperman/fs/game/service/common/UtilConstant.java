package com.fashionsuperman.fs.game.service.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilConstant {
	@Value("{#utilProperties.appid}")
	public String appid;
	@Value("{#utilProperties.secret}")
	public String secret;
	@Value("{#utilProperties.redirect_uri}")
	public String redirect_uri;
	@Value("{#utilProperties.loginUrl}")
	public String loginUrl;
	@Value("{#utilProperties.getAccessTokenUrl}")
	public String getAccessTokenUrl;
	@Value("{#utilProperties.getAccessTokenUrl}")
	public String getUserinfoUrl;
	@Value("{#utilProperties.unifiedorderUrl}")
	public String unifiedorderUrl;
	@Value("{#utilProperties.mch_id}")
	public String mch_id;
}
