package com.fashionsuperman.fs.game.facet.biz;

import com.fashionsuperman.fs.game.facet.biz.message.MesJudgeCanPlay;
import com.fashionsuperman.fs.game.facet.biz.message.StatusCode;

/**
 * 个性化业务,单独接口定义
 * @description 
 * @author FashionSuperman
 * @date 2017年5月2日 上午11:26:38
 * @version 1.0
 */
public interface DogBizServiceI {
	/**
	 * 判断用户是否可以玩游戏(玩游戏道具是否在背包中有余量)
	 * @param mesJudgeCanPlay
	 * @return
	 */
	StatusCode judgeCanPlay(MesJudgeCanPlay mesJudgeCanPlay);
}
