package com.fashionsuperman.fs.game.service.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionsuperman.fs.game.dao.mapper.UserPackageMapper;
@Service
public class DogBizService {
	@Autowired
	private UserPackageMapper userPackageMapper;
	
	/**
	 * 获取用户背包中某件商品的数量
	 * @param paramMap
	 */
	public int getUserFooCommodityNum(Map<String, Object> paramMap){
		return userPackageMapper.getUserFooCommodityNum(paramMap);
	}
}
