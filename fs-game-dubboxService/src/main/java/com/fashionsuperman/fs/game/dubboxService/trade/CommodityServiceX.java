package com.fashionsuperman.fs.game.dubboxService.trade;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionsuperman.fs.game.dao.entity.Commodity;
import com.fashionsuperman.fs.game.facet.trade.CommodityI;
import com.fashionsuperman.fs.game.facet.trade.message.AddCommodityParam;
import com.fashionsuperman.fs.game.service.trade.CommodityService;

@Path("/Commodity")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("CommodityServiceX")
public class CommodityServiceX implements CommodityI {
	@Autowired
	private CommodityService commodityService;

	@POST
	@Path("/addCommodity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public AddCommodityParam addCommodity(AddCommodityParam addCommodityParam) {
		commodityService.addCommodity(addCommodityParam);
		return addCommodityParam;
	}

	@POST
	@Path("/deleteCommodity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public int deleteCommodity(Commodity commodity) {
		return commodityService.deleteCommodity(commodity);
	}

}
