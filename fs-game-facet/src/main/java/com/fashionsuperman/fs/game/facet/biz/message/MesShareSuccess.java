package com.fashionsuperman.fs.game.facet.biz.message;

public class MesShareSuccess {
	/**
	 * 分享标志  
	 * 1朋友圈
	 * 2朋友
	 */
	private String flag;
	
	private Long userId;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
}
