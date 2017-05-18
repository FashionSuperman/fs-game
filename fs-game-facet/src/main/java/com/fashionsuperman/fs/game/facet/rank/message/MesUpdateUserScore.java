package com.fashionsuperman.fs.game.facet.rank.message;

public class MesUpdateUserScore {
	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 分数
	 */
	private Float score;
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	
	
}
