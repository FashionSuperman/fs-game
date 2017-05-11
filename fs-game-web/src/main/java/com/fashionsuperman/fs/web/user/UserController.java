package com.fashionsuperman.fs.web.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserList;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserPackageList;
import com.fashionsuperman.fs.game.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	
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
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getUserPackageList")
	public PageInfo getUserPackageList(@RequestBody MesGetUserPackageList user){
		//查询该用户的背包信息
		PageInfo resGetUserPackageLists = userService.getUserPackageList(user.getUserid());
		return resGetUserPackageLists;
	}
}
