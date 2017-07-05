package com.fashionsuperman.fs.game.dubboxService.trade;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.trade.OrderI;
import com.fashionsuperman.fs.game.facet.trade.message.MesGenerateUserOrder;
import com.fashionsuperman.fs.game.facet.trade.message.ResGenerateUserOrder;
import com.fashionsuperman.fs.game.service.trade.OrderService;
@Path("/Order")
@Service("OrderServiceX")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public class OrderServiceX implements OrderI {
	@Autowired
	private OrderService orderService;

	@Override
	@POST
	@Path("/generateUserOrder")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResGenerateUserOrder generateUserOrder(MesGenerateUserOrder mesGenerateUserOrder) throws BizException {
		ResGenerateUserOrder result = null;
		
		result = orderService.generateUserOrder(mesGenerateUserOrder);
		return result;
	}

}
