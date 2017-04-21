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
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.mapper.CommodityCatagoryMapper;
import com.fashionsuperman.fs.game.service.trade.message.MesAddNewCatagory;
import com.fashionsuperman.fs.game.service.trade.message.MesDeleteCatagory;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCatagoryList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

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
		if(commodityCatagoryExist != null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该分类已经存在");
		}
		
		commodityCatagory.setCatagoryname(catagoryname);
		
		commodityCatagoryMapper.insertIncrement(commodityCatagory);
	}
	
	/**
	 * 查询商品分类列表 支持分页  web后台用
	 * @param param
	 * @return
	 */
	public PageInfo queryCatagoryList(MesQueryCatagoryList param){
		PageInfo result = new PageInfo();
		
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Page<?> page = PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
		
		String catagoryname = param.getCatagoryname();
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("catagoryname", catagoryname);
		
		List<CommodityCatagory> commodityCatagories = commodityCatagoryMapper.selectByParam(paramMap);
		if(commodityCatagories == null || commodityCatagories.size() == 0){
			result.setRows(new ArrayList<CommodityCatagory>());
			result.setTotal(0);
		}else{
			result.setRows(commodityCatagories);
			result.setTotal(page.getTotal());
		}
		
		return result;
	}
	
	/**
	 * 获取所有商品分类
	 * @return
	 */
	public List<CommodityCatagory> getAllCatagory(){
		List<CommodityCatagory> commodityCatagories = commodityCatagoryMapper.selectByParam(null);
		return commodityCatagories;
	}

	/**
	 * 添加/编辑  分类
	 * @param param
	 */
	public void addNewCatagory(MesAddNewCatagory param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		String catagoryname = param.getCatagoryname();
		Long catagoryId = param.getCatagoryId();
		
		if(StringUtil.isNullOrEmpty(catagoryname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "catagoryname不能为空");
		}
		
		CommodityCatagory commodityCatagory = new CommodityCatagory();
		commodityCatagory.setCatagoryname(catagoryname);
		if(catagoryId != null && catagoryId >= 0){//更新操作
			//查询该分类是否已经存在
			CommodityCatagory commodityCatagoryExist = commodityCatagoryMapper.selectByCatagoryName(catagoryname);
			if(commodityCatagoryExist != null){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该分类已经存在");
			}
			
			commodityCatagory.setCatagoryid(catagoryId);
			commodityCatagoryMapper.updateByPrimaryKeySelective(commodityCatagory);
		}else{//添加操作
			addCommodityCatagory(commodityCatagory);
		}
	}
	
	/**
	 * 删除商品分类
	 * @param param
	 */
	public void deleteCatagory(MesDeleteCatagory param){
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		Long catagoryId = param.getCatagoryId();
		commodityCatagoryMapper.deleteByPrimaryKey(catagoryId);
	}
	
	/**
	 * 查询商品分类
	 * @param param
	 * @return
	 */
	public CommodityCatagory queryCatagory(MesDeleteCatagory param){
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		return commodityCatagoryMapper.selectByPrimaryKey(param.getCatagoryId());
	}
	
}
