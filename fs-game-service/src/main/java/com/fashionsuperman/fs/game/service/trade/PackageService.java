package com.fashionsuperman.fs.game.service.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Package;
import com.fashionsuperman.fs.game.dao.entity.UserPackageKey;
import com.fashionsuperman.fs.game.dao.mapper.PackageMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserPackageMapper;
import com.fashionsuperman.fs.game.service.trade.message.MesAddCommodityToUserPackage;

@Service
public class PackageService {
	@Autowired
	private PackageMapper packageMapper;
	@Autowired
	private UserPackageMapper userPackageMapper;
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
		
		//添加到背包
		Package packageAdd = new Package();
		packageAdd.setCommodityid(commodityid);
		packageAdd.setNumber(number);
		packageMapper.insertIncrement(packageAdd);
		
		//查询最新添加的背包条目
		packageAdd = packageMapper.selectMaxId();
		
		//关联到用户
		UserPackageKey userPackageKey = new UserPackageKey();
		userPackageKey.setPackageid(packageAdd.getPackageid());
		userPackageKey.setUserid(userid);
		userPackageMapper.insert(userPackageKey);
	}
}
