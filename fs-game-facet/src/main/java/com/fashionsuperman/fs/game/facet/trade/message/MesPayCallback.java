package com.fashionsuperman.fs.game.facet.trade.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fashionsuperman.fs.game.facet.AdapterCDATA;

@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name = "xml") 
public class MesPayCallback {
	/**
	 * 返回状态码
	 * SUCCESS/FAIL
	 * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String return_code;
	/**
	 * 返回信息
	 * 返回信息，如非空，为错误原因
	 * 签名失败
	 * 参数格式校验错误
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String return_msg;
	
	//以下字段在return_code为SUCCESS的时候有返回 
	
	/**
	 * 公众账号ID
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String appid;
	/**
	 * 商户号
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String mch_id;
	/**
	 * 设备号
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String device_info;
	/**
	 * 随机字符串
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String nonce_str;
	/**
	 * 签名
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String sign;
	/**
	 * 签名类型
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String sign_type;
	/**
	 * 业务结果
	 * SUCCESS/FAIL
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String result_code;
	/**
	 * 错误代码
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String err_code;
	/**
	 * 错误代码描述
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String err_code_des;
	/**
	 * 用户标识
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String openid;
	/**
	 * 是否关注公众账号
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String is_subscribe;
	/**
	 * 交易类型
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String trade_type;
	/**
	 * 付款银行
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String bank_type;
	/**
	 * 订单金额
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String total_fee;
	/**
	 * 应结订单金额
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String settlement_total_fee;
	/**
	 * 货币种类
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String fee_type;
	/**
	 * 现金支付金额
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String cash_fee;
	/**
	 * 现金支付货币类型
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String cash_fee_type;
	/**
	 * 总代金券金额
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String coupon_fee;
	/**
	 * 代金券使用数量
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String coupon_count;
	/**
	 * 代金券类型 
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String coupon_type_$n;
	/**
	 * 代金券ID
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String coupon_id_$n;
	/**
	 * 单个代金券支付金额
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String coupon_fee_$n;
	/**
	 * 微信支付订单号
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String transaction_id;
	/**
	 * 商户订单号
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String out_trade_no;
	/**
	 * 商家数据包
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String attach;
	/**
	 * 支付完成时间
	 */
	@XmlJavaTypeAdapter(AdapterCDATA.class)
	private String time_end;
	
	
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(String settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(String coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getCoupon_type_$n() {
		return coupon_type_$n;
	}
	public void setCoupon_type_$n(String coupon_type_$n) {
		this.coupon_type_$n = coupon_type_$n;
	}
	public String getCoupon_id_$n() {
		return coupon_id_$n;
	}
	public void setCoupon_id_$n(String coupon_id_$n) {
		this.coupon_id_$n = coupon_id_$n;
	}
	public String getCoupon_fee_$n() {
		return coupon_fee_$n;
	}
	public void setCoupon_fee_$n(String coupon_fee_$n) {
		this.coupon_fee_$n = coupon_fee_$n;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
}
