package com.fashionsuperman.fs.game.facet.biz.message;

public class StatusCode {
	/**
	 * 1成功
	 * 2未登录失败 (提示用户登录)
	 * 3登录失败 (提示用户12小时后赠送,或者可以购买)
	 * 
	 */
	private String status;
	private String message;
	
	public StatusCode() {
		this.status = "0";
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
