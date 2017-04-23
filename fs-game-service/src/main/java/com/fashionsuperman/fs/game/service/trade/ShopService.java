package com.fashionsuperman.fs.game.service.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.entity.Shop;
import com.fashionsuperman.fs.game.dao.entity.custom.ShopCustom;
import com.fashionsuperman.fs.game.dao.mapper.CommodityMapper;
import com.fashionsuperman.fs.game.dao.mapper.ShopMapper;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCommodityList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ShopService {
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private CommodityMapper commodityMapper;
	
	/**
	 * 获取商品列表
	 * @param param
	 * @return
	 */
	public PageInfo queryShopCommodityList(MesQueryCommodityList param){
		PageInfo result = new PageInfo();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		//商品名
		String commodityname = param.getCommodityname();
		//商品分类名
		String catagoryname = param.getCatagoryname();
		//分页设置
		Page<?> page = PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("commodityname", commodityname);
		paramMap.put("catagoryname", catagoryname);
		
		List<ShopCustom> shopCustoms = shopMapper.selectByParam(paramMap);
		result.setRows(shopCustoms);
		result.setTotal(page.getTotal());
		
		return result;
	}

	/**
	 * 查询某个商品分类下的商品列表
	 * @param param
	 * @return
	 */
	public List<Commodity> queryFooCatagoryCommodities(CommodityCatagory param) {
		List<Commodity> result = new ArrayList<>();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long catagoryid = param.getCatagoryid();
		if(catagoryid == null || catagoryid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "catagoryid不能为空");
		}
		
		//查询该分类下所有未添加到商店的商品
		result = commodityMapper.queryFooCatagoryCommodities(catagoryid);
		return result;
	}

	/**
	 * 商店添加商品
	 * @param param
	 */
	public void addNewShopCommodity(Shop param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long commodityid = param.getCommodityid();
		Integer number = param.getNumber();
		Float price = param.getPrice();
		Float discount = param.getDiscount();
		
		
		if(commodityid == null || commodityid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "commodityid不能为空");
		}
		
		if(number == null || number < -1 || number == 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "number不能为0,-1表示无穷");
		}
		
		if(price == null || price < 0){
			price = 0f;
			param.setPrice(price);
		}
		
		if(discount == null || discount < 0){
			discount = 0f;
			param.setDiscount(discount);
		}
		
		
		shopMapper.insertIncrement(param);
		
	}

	public List<Commodity> queryFooCatagoryCommoditiesAll(CommodityCatagory param) {
		List<Commodity> result = new ArrayList<>();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long catagoryid = param.getCatagoryid();
		if(catagoryid == null || catagoryid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "catagoryid不能为空");
		}
		
		//查询该分类下所有未添加到商店的商品
		result = commodityMapper.queryFooCatagoryCommoditiesAll(catagoryid);
		return result;
	}

	/**
	 * 商店编辑商品
	 * @param param
	 */
	public void editNewShopCommodity(Shop param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long shopitemid = param.getShopitemid();
		if(shopitemid == null || shopitemid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "shopitemid不能为空");
		}
		
		shopMapper.updateByPrimaryKeySelective(param);
	}

	public void deleteNewShopCommodity(Shop param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long shopitemid = param.getShopitemid();
		if(shopitemid == null || shopitemid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "shopitemid不能为空");
		}
		
		shopMapper.deleteByPrimaryKey(shopitemid);
		
	}
}
