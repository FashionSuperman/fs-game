package com.fashionsuperman.fs.game.service.trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.mapper.CommodityCatagoryMapper;
import com.fashionsuperman.fs.game.dao.mapper.CommodityMapper;
import com.fashionsuperman.fs.game.facet.trade.message.AddCommodityParam;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCommodityList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

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

	/**
	 * 查询商品列表 web
	 * @param param
	 * @return
	 */
	public PageInfo queryCommodityList(MesQueryCommodityList param) {
		PageInfo result = new PageInfo();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		String commodityname = param.getCommodityname();
		String catagoryname = param.getCatagoryname();
		
		Page<?> page = PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("commodityname", commodityname);
		paramMap.put("catagoryname", catagoryname);
		
		List<Commodity> rows = commodityMapper.selectByParam(paramMap);
		
		result.setTotal(page.getTotal());
		result.setRows(rows);
		return result;
	}

	/**
	 * 编辑商品
	 * @param param
	 */
	public void editNewCommodity(Commodity param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		Long commodityid = param.getCommodityid();
		if(commodityid == null || commodityid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "commodityid不能为空");
		}
		commodityMapper.updateByPrimaryKeySelective(param);
	}
}
