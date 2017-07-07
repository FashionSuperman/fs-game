package com.fashionsuperman.fs.game.dubboxService.trade;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.component.DistributedLockService;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.UserOrder;
import com.fashionsuperman.fs.game.dao.mapper.UserMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserOrderMapper;
import com.fashionsuperman.fs.game.facet.trade.WXPayI;
import com.fashionsuperman.fs.game.facet.trade.message.MesPayCallback;
import com.fashionsuperman.fs.game.facet.trade.message.ResPayCallback;
import com.fashionsuperman.fs.game.service.common.DealFlag;
@Path("/WXPay")
@Service("WXPayServiceX")
@Produces(ContentType.TEXT_XML_UTF_8)
public class WXPayServiceX implements WXPayI{
	
	private Logger Logger = LogManager.getLogger(WXPayServiceX.class);
	
	@Autowired
	private DistributedLockService distributedLockService;
	@Autowired
	private UserOrderMapper userOrderMapper;
	@Autowired
	private UserMapper userMapper;

	@POST
	@OPTIONS
	@Path("/payCallback")
	@Consumes({MediaType.TEXT_XML,MediaType.APPLICATION_XML})
	@Override
	public ResPayCallback payCallback(MesPayCallback mesPayCallback) {
		
		ResPayCallback result = new ResPayCallback();
		result.setReturn_code("FAIL");
		result.setReturn_msg("FAIL");
		
		if(mesPayCallback == null){
			return result;
		}
		
		//TODO 验证微信签名(暂时不校验)
		
		//获取内部订单 
		String out_trade_no = mesPayCallback.getOut_trade_no();
		//订单金额
		String total_fee = mesPayCallback.getTotal_fee();
		//用户id
		String openid = mesPayCallback.getOpenid();
		
		if(StringUtil.isNullOrEmpty(out_trade_no)){
			return result;
		}
		if(StringUtil.isNullOrEmpty(total_fee)){
			return result;
		}
		if(StringUtil.isNullOrEmpty(openid)){
			return result;
		}
		
		//查询该用户
		User user = userMapper.selectByForeignId(openid);
		if(user == null){//该用户不存在  返回微信失败
			Logger.error("用户 " + openid + " 不存在");
			return result;
		}
		
		
		String lockName = "wxPay:" + out_trade_no;
		//获取该订单的锁
		String lockResult = distributedLockService.acquireLockWithTimeout(lockName, 3*1000, 3*1000);
		
		if(lockResult == null){//获取锁失败  直接返回微信失败
			Logger.error("用户 " + openid + " 获取分布式锁失败");
			return result;
		}
		
		//查询该订单 
		UserOrder userOrder = userOrderMapper.selectByPrimaryKey(out_trade_no);
		if(userOrder == null){//订单不存在  返回微信失败
			Logger.error("用户 " + openid + " 对应的订单 " + out_trade_no + " 不存在");
			return result;
		}
		//判断处理状态
		String dealFlag = userOrder.getDealflag();
		if(DealFlag.DealSuccess.equals(dealFlag)){//已经处理成功 直接返回微信成功
			result.setReturn_code("SUCCESS");
			result.setReturn_msg("SUCCESS");
			return result;
		}else if(DealFlag.DealFailure.equals(dealFlag) || DealFlag.NotDeal.equals(dealFlag)){//没有处理 或者 处理失败  继续处理
			//给用户添加相应的资产
			Float fundsTemp = Float.parseFloat(total_fee);
			
			Float funds = fundsTemp/100;
			
//			Float funds = Float.parseFloat(fundsTemp2);
			Float fundsInDb = user.getFunds();
			Logger.debug("用户 " + openid + " 原来资产 " + fundsInDb);
			if(fundsInDb == null){
				fundsInDb = 0f;
			}
			
			fundsInDb += funds;
			
			Logger.debug("用户 " + openid + " 现在资产 " + fundsInDb);
			
			//更新用户资产
			user.setFunds(fundsInDb);
			try {
				userMapper.updateByPrimaryKeySelective(user);
				
				
				//修改状态为已处理
				UserOrder userOrderUpdate = new UserOrder();
				userOrderUpdate.setOrderid(out_trade_no);
				userOrderUpdate.setDealflag(DealFlag.DealSuccess);
				
				userOrderMapper.updateByPrimaryKeySelective(userOrderUpdate);
				
				result.setReturn_code("SUCCESS");
				result.setReturn_msg("SUCCESS");
				return result;
			} catch (Exception e) {
				Logger.debug("用户 " + openid + " 更新资产失败");
				Logger.debug(e.getMessage());
				return result;
			}
		}
		
		return result;
	}

}
