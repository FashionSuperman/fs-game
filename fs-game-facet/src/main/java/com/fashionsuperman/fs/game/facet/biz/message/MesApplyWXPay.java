package com.fashionsuperman.fs.game.facet.biz.message;

public class MesApplyWXPay {
	/**
	 * 订单金额
	 */
	private String total_fee;
	/**
	 * 终端ip
	 */
	private String spbill_create_ip;
	
	/**
	 * 用户唯一标志
	 */
	private String openid;
	
	/**
	 * 订单id
	 */
	private String orderid;

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	
	
}
