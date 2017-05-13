package com.fashionsuperman.fs.web.trade;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.entity.Shop;
import com.fashionsuperman.fs.game.dao.entity.custom.CommodityCustom;
import com.fashionsuperman.fs.game.facet.trade.message.AddCommodityParam;
import com.fashionsuperman.fs.game.service.trade.CommodityCatagoryService;
import com.fashionsuperman.fs.game.service.trade.CommodityService;
import com.fashionsuperman.fs.game.service.trade.ShopService;
import com.fashionsuperman.fs.game.service.trade.message.MesAddNewCatagory;
import com.fashionsuperman.fs.game.service.trade.message.MesDeleteCatagory;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCatagoryList;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCommodityList;

@Controller
@RequestMapping("/trade")
public class TradeController {
	@Autowired
	private CommodityCatagoryService commodityCatagoryService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ShopService shopService;
	
	@RequestMapping("/catagoryInit")
	public ModelAndView catagoryInit(){
		ModelAndView result = new ModelAndView("trade/catagory");
		return result;
	}
	
	/**
	 * 查询商品分类列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryCatagoryList")
	public PageInfo queryCatagoryList(@RequestBody MesQueryCatagoryList param){
		return commodityCatagoryService.queryCatagoryList(param);
	}
	
	/**
	 * 添加/编辑 分类
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/addNewCatagory")
	public void addNewCatagory(@RequestBody MesAddNewCatagory param){
		commodityCatagoryService.addNewCatagory(param);
	}
	
	/**
	 * 删除商品分类
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/deleteCatagory")
	public void deleteCatagory(@RequestBody MesDeleteCatagory param){
		commodityCatagoryService.deleteCatagory(param);
	}
	
	/**
	 * 查询商品分类
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryCatagory")
	public CommodityCatagory queryCatagory(@RequestBody MesDeleteCatagory param){
		return commodityCatagoryService.queryCatagory(param);
	}
	
	//商品相关
	/**
	 * 商品列表初始化
	 * @return
	 */
	@RequestMapping("/commodityInit")
	public ModelAndView commodityInit(){
		ModelAndView result = new ModelAndView("trade/commodity");
		//查询所有商品分类
		List<CommodityCatagory> commodityCatagoryList = commodityCatagoryService.getAllCatagory();
		result.addObject("commodityCatagoryList", commodityCatagoryList);
		return result;
	}
	
	/**
	 * 添加商品
	 */
	@ResponseBody
	@RequestMapping("/addNewCommodity")
	public void addNewCommodity(@RequestBody Commodity param){
		AddCommodityParam addCommodityParam = new AddCommodityParam();
		try {
			BeanUtils.copyProperties(addCommodityParam, param);
			commodityService.addCommodity(addCommodityParam);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "bean转换异常");
		}
	}
	
	/**
	 * 查询商品列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryCommodityList")
	public PageInfo queryCommodityList(@RequestBody MesQueryCommodityList param){
		return commodityService.queryCommodityList(param);
	}
	
	/**
	 * 编辑商品
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/editNewCommodity")
	public void editNewCommodity(@RequestBody Commodity param){
		commodityService.editNewCommodity(param);
	}
	
	/**
	 * 删除商品
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/deleteCommodity")
	public void deleteCommodity(@RequestBody Commodity param){
		commodityService.deleteCommodity(param);
	}
	
	//商店相关
	@RequestMapping("/shopInit")
	public ModelAndView shopInit(){
		ModelAndView result = new ModelAndView("trade/shop");
		//查询商品分类
		List<CommodityCatagory> commodityCatagoryList = commodityCatagoryService.getAllCatagory();
		result.addObject("commodityCatagoryList", commodityCatagoryList);
		
		return result;
	}
	
	/**
	 * 查询商店商品列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryShopCommodityList")
	public PageInfo queryShopCommodityList(@RequestBody MesQueryCommodityList param){
		return shopService.queryShopCommodityList(param);
	}
	
	/**
	 * 查询某个商品分类下的商品列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryFooCatagoryCommodities")
	public List<Commodity> queryFooCatagoryCommodities(@RequestBody CommodityCatagory param){
		return shopService.queryFooCatagoryCommodities(param);
	}
	
	@ResponseBody
	@RequestMapping("/queryFooCatagoryCommoditiesAll")
	public List<Commodity> queryFooCatagoryCommoditiesAll(@RequestBody CommodityCatagory param){
		return shopService.queryFooCatagoryCommoditiesAll(param);
	}
	
	/**
	 * 查询商店内某个分类下的所有商品
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryFooCatagoryCommoditiesShop")
	public List<CommodityCustom> queryFooCatagoryCommoditiesShop(@RequestBody CommodityCatagory param){
		return shopService.queryFooCatagoryCommoditiesShop(param);
	}
	
	/**
	 * 商店添加商品
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/addNewShopCommodity")
	public void addNewShopCommodity(@RequestBody Shop param){
		shopService.addNewShopCommodity(param);
	}
	
	/**
	 * 商店编辑商品
	 * @param param
	 */
	@ResponseBody
	@RequestMapping("/editNewShopCommodity")
	public void editNewShopCommodity(@RequestBody Shop param){
		shopService.editNewShopCommodity(param);
	}
	
	@ResponseBody
	@RequestMapping("/deleteNewShopCommodity")
	public void deleteNewShopCommodity(@RequestBody Shop param){
		shopService.deleteNewShopCommodity(param);
	}
}
