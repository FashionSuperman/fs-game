package com.fashionsuperman.fs.game.facet.biz.message;

public class StatusCode {
	/**
	 * 0失败
	 * 1成功
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
