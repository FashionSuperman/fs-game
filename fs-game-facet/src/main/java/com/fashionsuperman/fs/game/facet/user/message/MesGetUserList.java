package com.fashionsuperman.fs.game.facet.user.message;

import com.fashionSuperman.fs.core.common.PageInfo;

public class MesGetUserList extends PageInfo{
	private String accountname;
	private String nickname;
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
