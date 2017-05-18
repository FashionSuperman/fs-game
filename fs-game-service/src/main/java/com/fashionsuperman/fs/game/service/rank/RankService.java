package com.fashionsuperman.fs.game.service.rank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionsuperman.fs.game.dao.entity.custom.RankUser;
import com.fashionsuperman.fs.game.dao.mapper.RankMapper;
import com.fashionsuperman.fs.game.facet.rank.message.MesGetRankUserList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class RankService {
	@Autowired
	private RankMapper rankMapper;
	/**
	 * 获取用户排名列表
	 * @return
	 */
	public PageInfo getUserList(MesGetRankUserList mes) {
		PageInfo result = new PageInfo();
		
		Page<?> page = PageHelper.startPage(mes.getCurrentPage(), mes.getPageSize());
		
		List<RankUser> rankUserList = rankMapper.getUserList();
		
		result.setTotal(page.getTotal());
		result.setRows(rankUserList);
		
		return result;
	}
	
}
