package com.fashionsuperman.fs.web.rank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionsuperman.fs.game.facet.rank.message.MesGetRankUserList;
import com.fashionsuperman.fs.game.service.rank.RankService;

@Controller
@RequestMapping("/rank")
public class RankController {
	@Autowired
	private RankService rankService;
	@RequestMapping("/rankInit")
	public ModelAndView rankInit(){
		ModelAndView result = new ModelAndView("rank/rank");
		return result;
	}
	
	@RequestMapping("/getUserList")
	@ResponseBody
	public PageInfo getUserList(@RequestBody MesGetRankUserList mes){
		return rankService.getUserList(mes);
	}
}
