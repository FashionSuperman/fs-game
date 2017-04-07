package com.fashionsuperman.fs.game.service.trade.commoditycatagory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.mapper.CommodityCatagoryMapper;

/**
 * 商品分类服务
 * @description 
 * @author FashionSuperman
 * @date 2017年3月27日 上午10:01:05
 * @version 1.0
 */
@Service
public class CommodityCatagoryService {
	@Autowired
	private CommodityCatagoryMapper commodityCatagoryMapper;
	/**
	 * 添加商品分类
	 * @param commodityCatagory
	 */
	public void addCommodityCatagory(CommodityCatagory commodityCatagory){
		if(commodityCatagory == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "商品分类信息不能为空");
		}
		
		String catagoryname = commodityCatagory.getCatagoryname();
		if(StringUtil.isNullOrEmpty(catagoryname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "catagoryname不能为空");
		}
		
		//查询该分类是否已经存在
		
		CommodityCatagory commodityCatagoryExist = commodityCatagoryMapper.selectByCatagoryName(catagoryname);
		if(commodityCatagoryExist == null){
			return;
		}
		
		commodityCatagory.setCatagoryid(null);
		
		commodityCatagoryMapper.insertIncrement(commodityCatagory);
	}
	
	
}
