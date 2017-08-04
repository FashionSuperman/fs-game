package com.fashionsuperman.fs.game.facet.biz.message;

import java.util.List;

public class ResGetWxConfigParam {
	private String debug;
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	private List<String> jsApiList;
	public String getDebug() {
		return debug;
	}
	public void setDebug(String debug) {
		this.debug = debug;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public List<String> getJsApiList() {
		return jsApiList;
	}
	public void setJsApiList(List<String> jsApiList) {
		this.jsApiList = jsApiList;
	}
	
	
	
}
