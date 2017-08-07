package com.fashionsuperman.fs.game.dubboxService.rank;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionsuperman.fs.game.dubboxService.common.DubboxCookieComponent;
import com.fashionsuperman.fs.game.facet.rank.RankServiceI;
import com.fashionsuperman.fs.game.facet.rank.message.MesUpdateUserScore;
import com.fashionsuperman.fs.game.facet.user.message.UserLogin;
import com.fashionsuperman.fs.game.service.rank.RankService;

@Path("/Rank")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("RankServiceX")
public class RankServiceX implements RankServiceI{
	@Autowired
	private RankService rankService;
	@Autowired
	private DubboxCookieComponent dubboxCookieComponent;
	/**
	 * 更新用户分数
	 */
	@POST
	@GET
	@Path("/updateUserScore")
	@Consumes(MediaType.APPLICATION_JSON)
	public MesUpdateUserScore updateUserScore(MesUpdateUserScore mes){
		UserLogin user = dubboxCookieComponent.getLoginUser();
		if(user != null){
			mes.setUserid(user.getUserid());
			rankService.updateUserScore(mes);
		}
		
		return mes;
	}
}
