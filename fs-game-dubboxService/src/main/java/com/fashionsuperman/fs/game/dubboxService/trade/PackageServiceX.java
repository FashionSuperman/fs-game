package com.fashionsuperman.fs.game.dubboxService.trade;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.trade.PackageI;
import com.fashionsuperman.fs.game.facet.trade.message.MesGetUserPackageList;
import com.fashionsuperman.fs.game.service.trade.PackageService;
@Path("/Package")
@Service("PackageServiceX")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
public class PackageServiceX implements PackageI {
	@Autowired
	private PackageService packageService;
	/**
	 * 获取用户背包列表
	 */
	@Override
	@POST
	@Path("/getUserPackageList")
	@Consumes(MediaType.APPLICATION_JSON)
	public PageInfo getUserPackageList(MesGetUserPackageList param) throws BizException {
		return packageService.getUserPackageList(param);
	}

}
