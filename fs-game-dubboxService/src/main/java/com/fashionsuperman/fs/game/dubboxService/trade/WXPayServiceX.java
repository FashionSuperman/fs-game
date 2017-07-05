package com.fashionsuperman.fs.game.dubboxService.trade;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.fashionsuperman.fs.game.facet.trade.WXPayI;
import com.fashionsuperman.fs.game.facet.trade.message.MesPayCallback;
import com.fashionsuperman.fs.game.facet.trade.message.ResPayCallback;
@Path("/WXPay")
@Service("WXPayServiceX")
@Produces(MediaType.TEXT_XML)
public class WXPayServiceX implements WXPayI{

	@POST
	@OPTIONS
	@Path("/payCallback")
	@Consumes({MediaType.TEXT_XML})
	@Override
	public ResPayCallback payCallback(MesPayCallback mesPayCallback) {
		
		ResPayCallback result = new ResPayCallback();
		
		result.setReturn_code("123");
		result.setReturn_msg("hello");
		return result;
	}

}
