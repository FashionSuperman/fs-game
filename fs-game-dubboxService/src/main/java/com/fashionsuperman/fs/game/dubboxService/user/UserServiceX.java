package com.fashionsuperman.fs.game.dubboxService.user;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.UserRelationshipKey;
import com.fashionsuperman.fs.game.dao.entity.custom.UserCustom;
import com.fashionsuperman.fs.game.facet.user.UserI;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserList;
import com.fashionsuperman.fs.game.facet.user.message.MesUserAddFriendByAccountName;
import com.fashionsuperman.fs.game.facet.user.message.ResLoginwx;
import com.fashionsuperman.fs.game.service.user.UserService;

@Path("/User")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("UserServiceX")
public class UserServiceX implements UserI {
	@Autowired
	private UserService userService;
	@POST
	@Path("/registeUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public User registeUser(User user) {
		userService.registeUser(user);
		return user;
	}
	
	
	@POST
	@Path("/userLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public User userLogin(User user) {
		return userService.userLogin(user);
	}

	@POST
	@Path("/userAddFriend")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public UserRelationshipKey userAddFriend(UserRelationshipKey param) {
		return userService.userAddFriend(param);
	}

	
	@POST
	@Path("/getUserFriends")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public List<UserCustom> getUserFriends(User user) {
		return userService.getUserFriends(user);
	}

	@POST
	@Path("/userAddFriendByAccountName")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public MesUserAddFriendByAccountName userAddFriendByAccountName(MesUserAddFriendByAccountName param) {
		return userService.userAddFriendByAccountName(param);
	}
	
//	@POST
//	@Path("/getUserList")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Override
//	public PageInfo getUserList(MesGetUserList param){
//		return userService.getUserList(param);
//	}
	@GET
	@Path("/loginwx")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public User loginwx(){
		User result = new User();
		HttpServletResponse httpServletResponse = (HttpServletResponse) RpcContext.getContext().getResponse();
		HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
		
		String code = httpServletRequest.getParameter("code");
		
		result = userService.loginwx(code);
		
		//回写cookie
		Cookie cookie = new Cookie("sessionId", result.getAccountname());
		cookie.setMaxAge(30*12*60*60);
		cookie.setPath("/");
		httpServletResponse.addCookie(cookie);
		
		
		return result;
	}
}
