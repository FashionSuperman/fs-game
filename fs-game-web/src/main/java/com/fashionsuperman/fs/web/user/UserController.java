package com.fashionsuperman.fs.web.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.CommodityCatagory;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.facet.trade.message.MesBuyShopCommodity;
import com.fashionsuperman.fs.game.facet.trade.message.MesEditPackageNum;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserList;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserPackageList;
import com.fashionsuperman.fs.game.facet.user.message.MesLogin;
import com.fashionsuperman.fs.game.service.trade.CommodityCatagoryService;
import com.fashionsuperman.fs.game.service.trade.ShopService;
import com.fashionsuperman.fs.game.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private CommodityCatagoryService commodityCatagoryService;
	@Autowired
	private ShopService shopService;
	
	@ResponseBody
	@RequestMapping("/login")
	public void login(@RequestBody MesLogin mes){
		if(mes == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "参数不能为空");
		}
		String accountname = mes.getAccountname();
		String password = mes.getPassword();
		
		if(StringUtil.isNullOrEmpty(accountname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "accountname不能为空");
		}
		if(StringUtil.isNullOrEmpty(password)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "password不能为空");
		}
		
		if("ifyoudog".equals(accountname) && "ifyoudog_fs".equals(password)){
			httpServletRequest.getSession().setAttribute("userId", "ifyoudog");
		}else{
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "非法用户");
		}
	}
	
	@RequestMapping("/userInit")
	public ModelAndView userInit(){
		return new ModelAndView("user/user");
	}
	
	@ResponseBody
	@RequestMapping("/getUserList")
	public PageInfo getUserList(@RequestBody MesGetUserList param){
		return userService.getUserList(param);
	}
	
	@ResponseBody
	@RequestMapping("/registeUser")
	public void registeUser(@RequestBody User user){
		userService.registeUser(user);
	}
	
	@ResponseBody
	@RequestMapping("/updateUser")
	public void updateUser(@RequestBody User user){
		userService.updateUser(user);
	}
	
	@RequestMapping("/userPackageInit")
	public ModelAndView userPackageInit(){
		ModelAndView result = new ModelAndView("user/userPackage");
		String userId = request.getParameter("userId");
		result.addObject("userId", userId);
		//查询所有商品分类
		List<CommodityCatagory> commodityCatagoryList = commodityCatagoryService.getAllCatagory();
		result.addObject("commodityCatagoryList", commodityCatagoryList);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getUserPackageList")
	public PageInfo getUserPackageList(@RequestBody MesGetUserPackageList user){
		//查询该用户的背包信息
		PageInfo resGetUserPackageLists = userService.getUserPackageList(user.getUserid());
		return resGetUserPackageLists;
	}
	
	/**
	 * 用户背包添加商品
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/buyShopCommodity")
	public MesBuyShopCommodity buyShopCommodity(@RequestBody MesBuyShopCommodity param){
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请求参数不能为空");
		}
		shopService.buyShopCommodity(param);
		return param;
	}
	
	/**
	 * 编辑用户背包数量,不扣资产
	 */
	@ResponseBody
	@RequestMapping("/editPackageNum")
	public void editPackageNum(@RequestBody MesEditPackageNum param){
		shopService.editPackageNum(param);
	}
}
