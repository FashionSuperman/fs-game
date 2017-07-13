package com.fashionsuperman.fs.game.facet.user.message;

import java.util.Date;

public class UserLogin {
	private Long userid;
	private String accountname;
	private String nickname;
	private String foreighid;
	private String foreightype;
	private Float funds;
	private Date createdate;
	private String password;
	
	private String headimgurl;
	
	private Float score;
	

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

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

	public String getForeighid() {
		return foreighid;
	}

	public void setForeighid(String foreighid) {
		this.foreighid = foreighid;
	}

	public String getForeightype() {
		return foreightype;
	}

	public void setForeightype(String foreightype) {
		this.foreightype = foreightype;
	}

	public Float getFunds() {
		return funds;
	}

	public void setFunds(Float funds) {
		this.funds = funds;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "UserLogin [userid=" + userid + ", accountname=" + accountname + ", nickname=" + nickname
				+ ", foreighid=" + foreighid + ", foreightype=" + foreightype + ", funds=" + funds + ", createdate="
				+ createdate + ", password=" + password + ", headimgurl=" + headimgurl + ", score=" + score + "]";
	}
	
	
	
}
