package com.fashionsuperman.fs.game.service.trade;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Package;
import com.fashionsuperman.fs.game.dao.entity.UserPackageKey;
import com.fashionsuperman.fs.game.dao.entity.custom.UserPackageListCustom;
import com.fashionsuperman.fs.game.dao.mapper.PackageMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserPackageMapper;
import com.fashionsuperman.fs.game.facet.trade.message.MesGetUserPackageList;
import com.fashionsuperman.fs.game.facet.user.message.ResGetUserPackageList;
import com.fashionsuperman.fs.game.service.common.SequenceService;
import com.fashionsuperman.fs.game.service.trade.message.MesAddCommodityToUserPackage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class PackageService {
	@Autowired
	private PackageMapper packageMapper;
	@Autowired
	private UserPackageMapper userPackageMapper;
	@Autowired
	private SequenceService sequenceService;
	/**
	 * 添加商品到用户背包
	 * @param param
	 */
	@Transactional
	public void addCommodityToUserPackage(MesAddCommodityToUserPackage param){
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		Long userid = param.getUserid();
		Long commodityid = param.getCommodityid();
		Integer number = param.getNumber();
		if(userid == null || userid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "userid不能为空");
		}
		if(commodityid == null || commodityid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "commodityid不能为空");
		}
		if(number == null || number <= 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "number不能为空");
		}
		
		//TODO 如果用户背包中已经有该商品,更新该商品的
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("commodityid", commodityid);
		
		Package package1 = userPackageMapper.selectFooUserFooCommodity(paramMap);
		if(package1 != null){//用户已经购买了该商品,更新该商品的背包数量
			Package recordupdate = new Package();
			recordupdate.setPackageid(package1.getPackageid());
			recordupdate.setNumber(package1.getNumber() + number);
			packageMapper.updateByPrimaryKeySelective(recordupdate);
		}else{
			//通过序列函数获取背包id
			Long packageId = sequenceService.nextval();
			
			//添加到背包
			Package packageAdd = new Package();
			packageAdd.setCommodityid(commodityid);
			packageAdd.setNumber(number);
			packageAdd.setPackageid(packageId);
			packageMapper.insert(packageAdd);
			
			//查询最新添加的背包条目
//			packageAdd = packageMapper.selectMaxId(userid);
			
			//关联到用户
			UserPackageKey userPackageKey = new UserPackageKey();
			userPackageKey.setPackageid(packageAdd.getPackageid());
			userPackageKey.setUserid(userid);
			userPackageMapper.insert(userPackageKey);
		}
		
		
		
	}
	/**
	 * 获取用户背包列表
	 * @param param
	 * @return
	 */
	public PageInfo getUserPackageList(MesGetUserPackageList param) {
		PageInfo result = new PageInfo();
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		//设置分页
		Page<?> page = PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
		
		Long userid = param.getUserid();
		Long catagoryid = param.getCatagoryid();
		String commodityname = param.getCommodityname();
		
		if(userid == null || userid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户id不能为空");
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("catagoryid", catagoryid);
		paramMap.put("commodityname", commodityname);
		
		List<UserPackageListCustom> userPackageListCustoms = userPackageMapper.getUserPackageList(paramMap);
		
		result.setRows(userPackageListCustoms);
		result.setTotal(page.getTotal());
		
		
		return result;
	}
	/**
	 * 获取用户背包列表  管理后台用
	 * @param userId
	 */
	public List<ResGetUserPackageList> getUserPackageList2(Integer userId) {
		List<ResGetUserPackageList> result = new ArrayList<ResGetUserPackageList>();
		if(userId == null || userId < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户id不能为空");
		}
		ResGetUserPackageList temp;
		List<com.fashionsuperman.fs.game.dao.entity.custom.ResGetUserPackageList> userPackageListCustoms = userPackageMapper.getUserPackageList2(userId);
		for(com.fashionsuperman.fs.game.dao.entity.custom.ResGetUserPackageList rgup : userPackageListCustoms){
			temp = new ResGetUserPackageList();
			try {
				BeanUtils.copyProperties(temp, rgup);
				result.add(temp);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
