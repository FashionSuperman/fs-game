package com.fashionsuperman.fs.game.service.trade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.Shop;
import com.fashionsuperman.fs.game.dao.entity.UserOrder;
import com.fashionsuperman.fs.game.dao.mapper.ShopMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserOrderMapper;
import com.fashionsuperman.fs.game.facet.trade.message.MesGenerateUserOrder;
import com.fashionsuperman.fs.game.facet.trade.message.ResGenerateUserOrder;
import com.fashionsuperman.fs.game.service.common.DealFlag;
import com.fashionsuperman.fs.game.service.common.OrderNoUtil;
/**
 * 
 * @description 用户订单服务(内部)
 * @author FashionSuperman
 * @date 2017年7月5日 上午10:54:04
 * @version 1.0
 */
@Service
public class OrderService {
	@Autowired
	private UserOrderMapper userOrderMapper;
	@Autowired
	private ShopMapper shopMapper;
	
	/**
	 * 生成用户订单
	 * @param mesGenerateUserOrder
	 * @return
	 */
	public ResGenerateUserOrder generateUserOrder(MesGenerateUserOrder mesGenerateUserOrder){
		ResGenerateUserOrder result = null;
		
		if(mesGenerateUserOrder == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long userid = mesGenerateUserOrder.getUserid();
		String note = mesGenerateUserOrder.getNote();
		if(userid == null || userid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户id非法");
		}
		if(StringUtil.isNullOrEmpty(note)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请填写备注");
		}
		
		Long commodityid = mesGenerateUserOrder.getCommodityid();
		Integer number = mesGenerateUserOrder.getNumber();
		
		if(commodityid != null && commodityid > 0){//如果传递商品id  查询该商品是否存在
			//如果传递商品id  必须传递数量
			if(number == null || number <= 0){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请填写购买数量");
			}
			List<Shop> shops = shopMapper.selectByCommodityId(commodityid);
			if(shops == null || shops.size() <= 0){//商品不存在商店
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "商品不存在");
			}else{//商品存在 判断数量 
				Shop shop = shops.get(0);
				if(shop.getNumber() != -1){//不是无数
					//判断库存是否满足购买数量
					if(number > shop.getNumber()){
						throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "商品库存不足");
					}
				}
			}
		}
		
		
		//生成订单id
		String out_trade_no = OrderNoUtil.generateOrderNo();
		
		UserOrder record = new UserOrder();
		
		record.setOrderid(out_trade_no);
		record.setCommodityid(commodityid);
		record.setNumber(number);
		record.setCreatedate(new Date());
		record.setNote(note);
		record.setUserid(userid);
		record.setDealflag(DealFlag.NotDeal);
		
		try {
			userOrderMapper.insert(record);
		} catch (Exception e) {
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "生成订单失败  请稍后重试");
		}
		
		result = new ResGenerateUserOrder();
		result.setOrderid(out_trade_no);
		
		return result;
	}
	
	
	
}
