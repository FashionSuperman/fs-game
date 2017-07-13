package com.fashionsuperman.fs.game.dubboxService.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.Rank;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.UserRelationshipKey;
import com.fashionsuperman.fs.game.dao.entity.custom.UserCustom;
import com.fashionsuperman.fs.game.dao.mapper.UserMapper;
import com.fashionsuperman.fs.game.facet.user.UserI;
import com.fashionsuperman.fs.game.facet.user.message.MesUserAddFriendByAccountName;
import com.fashionsuperman.fs.game.facet.user.message.UserLogin;
import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fashionsuperman.fs.game.service.rank.RankService;
import com.fashionsuperman.fs.game.service.user.UserService;

@Path("/User")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Service("UserServiceX")
public class UserServiceX implements UserI {
	@Autowired
	private UserService userService;
	@Autowired
	private UtilConstant UtilConstant;
	@Autowired
	private RankService rankService;
	
	@Autowired
	private com.fashionsuperman.fs.game.dubboxService.common.DubboxCookieComponent dubboxCookieComponent;
	@Autowired
	private UserMapper userMapper;
	
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
	
	@GET
	@Path("/loginwx")
	@Produces({MediaType.APPLICATION_JSON})
	@Override
	public User loginwx() throws IOException{
		User result = new User();
		HttpServletResponse httpServletResponse = (HttpServletResponse) RpcContext.getContext().getResponse();
		HttpServletRequest httpServletRequest = (HttpServletRequest) RpcContext.getContext().getRequest();
		
		String code = httpServletRequest.getParameter("code");
		
		result = userService.loginwx(code);
		
		//回写cookie
		Cookie cookie = new Cookie("sessionId", result.getAccountname());
		cookie.setMaxAge(30*24*60*60);
		cookie.setPath("/");
		httpServletResponse.addCookie(cookie);
		
		httpServletResponse.sendRedirect(UtilConstant.ifyoudogindex);
		
		return result;
	}


	@Override
	@POST
	@Path("/getLoginUserInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	public UserLogin getLoginUserInfo() throws BizException {
		UserLogin userLogin = dubboxCookieComponent.getLoginUser();
		
		if(userLogin == null){
			return new UserLogin();
		}
		
		//获取用户最高分
		Rank rank = rankService.getUserRankByAccountName(userLogin.getAccountname());
		
		if(rank == null){
			userLogin.setScore(0f);
		}else{
			userLogin.setScore(rank.getScore());
		}
		
		
		
		
		//获取用户资产 
		User user = userMapper.selectByForeignId(userLogin.getForeighid());
		userLogin.setFunds(user.getFunds());
		
		
		return userLogin;
		
	}
}
