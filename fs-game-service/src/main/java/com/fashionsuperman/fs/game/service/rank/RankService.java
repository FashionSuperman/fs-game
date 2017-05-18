package com.fashionsuperman.fs.game.service.rank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Rank;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.custom.RankUser;
import com.fashionsuperman.fs.game.dao.mapper.RankMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserMapper;
import com.fashionsuperman.fs.game.facet.rank.message.MesGetRankUserList;
import com.fashionsuperman.fs.game.facet.rank.message.MesUpdateUserScore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class RankService {
	@Autowired
	private RankMapper rankMapper;
	@Autowired
	private UserMapper userMapper;
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
	/**
	 * 更新用户分数
	 * @param mes
	 */
	public void updateUserScore(MesUpdateUserScore mes){
		if(mes == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户信息不能为空");
		}
		
		Long userid = mes.getUserid();
		Float score = mes.getScore();
		
		if(userid == null || userid < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "userid不能为空");
		}
		
		if(score == null || score < 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "score不合法");
		}
		
		//获取该用户信息
		User user = userMapper.selectByPrimaryKey(userid);
		if(user == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该用户不存在");
		}
		//获取该用户的rank
		Rank rank = rankMapper.selectByPrimaryKey(userid);
		
		Rank rankUpdate = new Rank();
		if(rank != null){//更新
			rankUpdate.setUserid(userid);
			rankUpdate.setScore(score);
//			rankUpdate.setNickname(user.getNickname());
			rankMapper.updateByPrimaryKeySelective(rankUpdate);
		}else{//插入
			rankUpdate.setUserid(userid);
			rankUpdate.setScore(score);
			rankUpdate.setNickname(user.getNickname());
			rankMapper.insert(rankUpdate);
		}
		
	}
	
}
