package com.fashionsuperman.fs.game.service.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.entity.Package;
import com.fashionsuperman.fs.game.dao.entity.Shop;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.custom.CommodityCustom;
import com.fashionsuperman.fs.game.dao.entity.custom.ShopCustom;
import com.fashionsuperman.fs.game.dao.mapper.CommodityMapper;
import com.fashionsuperman.fs.game.dao.mapper.PackageMapper;
import com.fashionsuperman.fs.game.dao.mapper.ShopMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserMapper;
import com.fashionsuperman.fs.game.facet.trade.message.MesBuyShopCommodity;
import com.fashionsuperman.fs.game.facet.trade.message.MesEditPackageNum;
import com.fashionsuperman.fs.game.service.trade.message.MesAddCommodityToUserPackage;
import com.fashionsuperman.fs.game.service.trade.message.MesQueryCommodityList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ShopService {
	@Autowired
	private ShopMapper shopMapper;
	@Autowired
	private CommodityMapper commodityMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PackageService packageService;
	@Autowired
	private PackageMapper packageMapper;
	
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

	/**
	 * 购买商店商品
	 * @param param
	 */
	@Transactional
	public void buyShopCommodity(MesBuyShopCommodity param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		Long userid = param.getUserid();
		Long shopitemid = param.getShopitemid();
		Integer number = param.getNumber();
		if(userid == null || userid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "userid不能为空");
		}
		if(shopitemid == null || shopitemid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "shopitemid不能为空");
		}
		if(number == null || number < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "number不能为空");
		}
		//判断该商品是否在商店存在
		Shop shop = shopMapper.selectByPrimaryKey(shopitemid);
		if(shop == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该商品不存在");
		}
		//判断该商品库存
		int numberInDB = shop.getNumber();
		if(numberInDB < number){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该商品库存不足");
		}
		
		
		Float price = shop.getPrice();
		//判断折扣
		Float discount = shop.getDiscount();
		//0-1之间的数据,0.6表示六折
		if(discount != null && discount > 0 && discount < 1){
			price = price * discount;
		}
		
		//判断用户资产
		User user = userMapper.selectByPrimaryKey(userid);
		if(user == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该用户不存在");
		}
		Float funds = user.getFunds();
		if(funds == null || funds < price){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "您的资产不足,请先充值");
		}
		
		//购买开始
		//减少库存
		Shop shopUpdate = new Shop();
		shopUpdate.setShopitemid(shopitemid);
		shopUpdate.setNumber(numberInDB - number);
		shopMapper.updateByPrimaryKeySelective(shopUpdate);
		
		//加背包
		MesAddCommodityToUserPackage mesAddCommodityToUserPackage = new MesAddCommodityToUserPackage();
		mesAddCommodityToUserPackage.setCommodityid(shop.getCommodityid());
		mesAddCommodityToUserPackage.setNumber(number);
		mesAddCommodityToUserPackage.setUserid(userid);
		packageService.addCommodityToUserPackage(mesAddCommodityToUserPackage);
		
		//减资产
		User userUpdate = new User();
		userUpdate.setUserid(userid);
		userUpdate.setFunds(funds - price);
		userMapper.updateByPrimaryKeySelective(userUpdate);
		
	}

	/**
	 * 查询商店内某个商品分类下的所有商品
	 * @param param
	 * @return
	 */
	public List<CommodityCustom> queryFooCatagoryCommoditiesShop(CommodityCatagory param) {
		List<CommodityCustom> result = new ArrayList<>();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		Long catagoryid = param.getCatagoryid();
		if(catagoryid == null || catagoryid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "catagoryid不能为空");
		}
		
		//查询该分类下所有未添加到商店的商品
		result = commodityMapper.queryFooCatagoryCommoditiesShop(catagoryid);
		return result;
	}

	/**
	 * 修改用户背包数量,不处理资产
	 * @param param
	 */
	public void editPackageNum(MesEditPackageNum param) {
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		
		if(param.getPackageid() == null || param.getPackageid() < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "packageid不能为空");
		}
		if(param.getNumber() == null || param.getNumber() < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "number不能为空");
		}
		
		com.fashionsuperman.fs.game.dao.entity.Package record = new Package();
		record.setPackageid(param.getPackageid());
		record.setNumber(param.getNumber());
		packageMapper.updateByPrimaryKeySelective(record);
	}
}
