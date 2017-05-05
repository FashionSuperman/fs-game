package com.fashionsuperman.fs.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserList;
import com.fashionsuperman.fs.game.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
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
	@RequestMapping("registeUser")
	public void registeUser(@RequestBody User user){
		userService.registeUser(user);
	}
}
