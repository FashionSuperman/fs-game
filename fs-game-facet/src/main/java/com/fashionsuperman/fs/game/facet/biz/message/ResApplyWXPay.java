package com.fashionsuperman.fs.game.facet.biz.message;

public class ResApplyWXPay {
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String backage;
	private String signType;
	private String paySign;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getBackage() {
		return backage;
	}
	public void setBackage(String backage) {
		this.backage = backage;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	
}
