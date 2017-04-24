package com.fashionsuperman.fs.game.dubboxService.trade;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.mapper.CommodityCatagoryMapper;
import com.fashionsuperman.fs.game.dao.mapper.CommodityMapper;
import com.fashionsuperman.fs.game.facet.trade.ShopI;
import com.fashionsuperman.fs.game.facet.trade.message.MesBuyShopCommodity;
import com.fashionsuperman.fs.game.facet.trade.message.MesGetShopCommodities;
import com.fashionsuperman.fs.game.service.trade.ShopService;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCommodityList;
@Path("/Shop")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("ShopServiceX")
public class ShopServiceX implements ShopI {
	@Autowired
	private CommodityMapper commodityMapper;
	@Autowired
	private CommodityCatagoryMapper commodityCatagoryMapper;
	@Autowired
	private ShopService shopService;
	@Override
	public PageInfo getShopCommodities(MesGetShopCommodities param) {
		PageInfo result = new PageInfo();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		Long commodityid = param.getCommodityid();
		String commodityname = param.getCommodityname();
		Long catagoryid = param.getCatagoryid();
		String catagoryname = param.getCatagoryname();
		
		if(StringUtil.isNullOrEmpty(commodityname) && commodityid != null && commodityid > 0){
			//查询商品名称
			Commodity commodity = commodityMapper.selectByPrimaryKey(commodityid);
			if(commodity != null){
				commodityname = commodity.getCommodityname();
			}
		}
		
		if(StringUtil.isNullOrEmpty(catagoryname) && catagoryid != null && catagoryid > 0){
			//查询商品分类名称
			CommodityCatagory commodityCatagory = commodityCatagoryMapper.selectByPrimaryKey(catagoryid);
			if(commodityCatagory != null){
				catagoryname = commodityCatagory.getCatagoryname();
			}
		}
		MesQueryCommodityList param2 = new MesQueryCommodityList();
		param2.setCommodityname(commodityname);
		param2.setCatagoryname(catagoryname);
		param2.setCurrentPage(param.getCurrentPage());
		param2.setPageSize(param.getPageSize());
		result = shopService.queryShopCommodityList(param2);
		return result;
	}

	@Override
	public void buyShopCommodity(MesBuyShopCommodity param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		shopService.buyShopCommodity(param);
	}

}
