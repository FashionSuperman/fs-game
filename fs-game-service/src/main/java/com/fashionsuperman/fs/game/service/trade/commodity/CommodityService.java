package com.fashionsuperman.fs.game.service.trade.commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.mapper.CommodityCatagoryMapper;
import com.fashionsuperman.fs.game.dao.mapper.CommodityMapper;
import com.fashionsuperman.fs.game.facet.commodity.message.AddCommodityParam;
import com.fashionsuperman.fs.game.service.trade.commoditycatagory.CommodityCatagoryService;

/**
 * 商品服务
 * @description 
 * @author FashionSuperman
 * @date 2017年3月24日 下午4:34:31
 * @version 1.0
 */
@Service
public class CommodityService {
	@Autowired
	private CommodityMapper commodityMapper;
	@Autowired
	private CommodityCatagoryService commodityCatagoryService;
	@Autowired
	private CommodityCatagoryMapper commodityCatagoryMapper;
	/**
	 * 添加商品
	 * @param commodity
	 */
	public void addCommodity(AddCommodityParam addCommodityParam){
		if(addCommodityParam == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "商品信息不能为空");
		}
		Long commoditycatagoryid = addCommodityParam.getCommoditycatagoryid();
		String catagoryname = addCommodityParam.getCatagoryname();
		
		String commodityname = addCommodityParam.getCommodityname();
		String commoditydes = addCommodityParam.getCommoditydes();
		
		if(StringUtil.isNullOrEmpty(commodityname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "commodityname不能为空");
		}
		
		//查询商品名称是否存在
		Commodity commodityE = commodityMapper.selectByCommodityName(commodityname);
		if(commodityE != null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, commodityname + " 已经存在");
		}
		
		if(StringUtil.isNullOrEmpty(commoditydes)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "commoditydes不能为空");
		}
		
		if(commoditycatagoryid == null && StringUtil.isNullOrEmpty(catagoryname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请传递commoditycatagoryid或catagoryname");
		}
		
		if(commoditycatagoryid == null){//查询该分类名称是否存在,不存在创建
			CommodityCatagory commodityCatagory = new CommodityCatagory();
			commodityCatagory.setCatagoryname(catagoryname);
			commodityCatagoryService.addCommodityCatagory(commodityCatagory);
			commodityCatagory = commodityCatagoryMapper.selectByCatagoryName(catagoryname);
			commoditycatagoryid = commodityCatagory.getCatagoryid();
		}
		
		Commodity commodity = new Commodity();
		commodity.setCommoditycatagoryid(commoditycatagoryid);
		commodity.setCommodityname(commodityname);
		commodity.setCommoditydes(commoditydes);
		
		//添加商品
		
		commodityMapper.insertIncrement(commodity);
		
	}
	
	/**
	 * 删除商品
	 * @param commodity
	 * @return
	 */
	public int deleteCommodity(Commodity commodity){
		int result = 0;
		
		if(commodity == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "商品信息不能为空");
		}
		Long commodityid = commodity.getCommodityid();
		String commodityname = commodity.getCommodityname();
		
		if(commodityid == null && StringUtil.isNullOrEmpty(commodityname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请传递commodityid或commodityname");
		}
		
		if(commodityid == null){
			Commodity commodityE = commodityMapper.selectByCommodityName(commodityname);
			if(commodityE == null){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "要删除的商品不存在");
			}
			commodityid = commodityE.getCommodityid();
		}
		
		//根据id删除该商品
		result = commodityMapper.deleteByPrimaryKey(commodityid);
		
		return result;
	}
}
