package com.fashionsuperman.fs.game.service.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fashionSuperman.fs.core.common.PageInfo;
import com.fashionSuperman.fs.core.constant.StatusCode;
import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionSuperman.fs.core.util.HttpClientUtil;
import com.fashionSuperman.fs.core.util.JedisUtil;
import com.fashionSuperman.fs.core.util.StringUtil;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.UserRelationshipKey;
import com.fashionsuperman.fs.game.dao.entity.custom.UserCustom;
import com.fashionsuperman.fs.game.dao.mapper.UserMapper;
import com.fashionsuperman.fs.game.dao.mapper.UserRelationshipMapper;
import com.fashionsuperman.fs.game.facet.user.message.GetAccessTokenResponse;
import com.fashionsuperman.fs.game.facet.user.message.GetUserinfoResponse;
import com.fashionsuperman.fs.game.facet.user.message.MesGetUserList;
import com.fashionsuperman.fs.game.facet.user.message.MesUserAddFriendByAccountName;
import com.fashionsuperman.fs.game.service.common.UtilConstant;
import com.fashionsuperman.fs.game.service.constant.ForeignType;
import com.fashionsuperman.fs.game.service.trade.PackageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * 用户服务类
 * @description 
 * @author FashionSuperman
 * @date 2017年3月22日 上午8:43:04
 * @version 1.0
 */
@Service
public class UserService {
	@Autowired
	private UtilConstant UtilConstant;
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRelationshipMapper userRelationshipMapper;
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private JedisUtil jedisUtil;
	
//	private ObjectMapper objectMapper;
	
	Logger logger = LogManager.getLogger(UserService.class);
	
	private int timeOut = 30 * 12 * 60 * 60;//30天过期
	
	/**
	 * 注册用户
	 * @param user
	 */
	public void registeUser(User user){
		if(user == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户信息不能为空");
		}
		String accountname = user.getAccountname();
		if(StringUtil.isNullOrEmpty(accountname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "accountname不能为空");
		}
		//查询该用户账号是否已经存在
		Map<String, Object> param = new HashMap<>();
		param.put("accountname", accountname);
		List<User> userExist = userMapper.selectByParam(param);
		if(userExist != null && userExist.size() != 0){
//			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "该用户已经存在");
			return;
		}
		
		String nickname = user.getNickname();
		if(StringUtil.isNullOrEmpty(nickname)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "nickname不能为空");
		}
		
		String foreighid = user.getForeighid();
		String foreightype = user.getForeightype();
		String password = user.getPassword();
		if(StringUtil.isNotEmpty(foreighid)){
			foreightype = StringUtil.isNullOrEmpty(foreightype)?ForeignType.wx:foreightype;
			password = "foreign";
			user.setPassword(password);
		}
		
		user.setFunds((user.getFunds() == null || user.getFunds() < 0f) ?  0f : user.getFunds());
		user.setCreatedate(new Date());
		
		//注册
		userMapper.insertIncrement(user);
	}
	
	/**
	 * 用户登录 获取用户信息
	 * @param user
	 * @return
	 */
	public User userLogin(User user){
		User result = null;
		if(user == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户信息不能为空");
		}
		Map<String, Object> param;
		Long userid = user.getUserid();
		String password = user.getPassword();
		String foreighid = user.getForeighid();
		String foreightype = user.getForeightype();
		String accountname = user.getAccountname();
		if(userid != null){//通过id登录
			if(StringUtil.isNullOrEmpty(password)){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "password不能为空");
			}
			result = userMapper.selectByPrimaryKey(userid);
			if(result == null){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户不存在");
			}
		}else if(StringUtil.isNotEmpty(foreighid)){//通过第三方平台登录
			if(StringUtil.isNullOrEmpty(foreighid)){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请传递登录标志");
			}
			
			if(StringUtil.isNullOrEmpty(foreightype)){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "foreightype不能为空");
			}
			param = new HashMap<>();
			param.put("foreighid", foreighid);
			param.put("foreightype", foreightype);
			List<User> users = userMapper.selectByParam(param);
			if(users != null && users.size() == 1){
				result = users.get(0);
			}else{
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户不存在");
			}
		}else if(StringUtil.isNotEmpty(accountname)){//通过accountname登录
			param = new HashMap<>();
			param.put("accountname", accountname);
			List<User> users = userMapper.selectByParam(param);
			if(users != null && users.size() == 1){
				result = users.get(0);
			}else{
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户不存在");
			}
			
		}
		return result;
	}
	
	/**
	 * 用户添加好友
	 * @param param
	 */
	public UserRelationshipKey userAddFriend(UserRelationshipKey param){
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "参数不能为空");
		}
		Long auserid = param.getAuserid();
		Long buserid = param.getBuserid();
		if(auserid == null || buserid == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请传递正确的用户id");
		}
		
		//查询两个用户是否存在
		User aUser = userMapper.selectByPrimaryKey(auserid);
		User bUser = userMapper.selectByPrimaryKey(buserid);
		
		if(aUser == null || bUser == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "要添加的用户不存在");
		}
		
		userRelationshipMapper.insert(param);//A添加B
		
		param.setAuserid(buserid);
		param.setBuserid(auserid);
		userRelationshipMapper.insert(param);//B添加A
		
		return param;
	}
	
	/**
	 * 通过账户名用户添加好友
	 * @param param
	 * @return
	 */
	public MesUserAddFriendByAccountName userAddFriendByAccountName(MesUserAddFriendByAccountName param){
		if(param == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "参数不能为空");
		}
		String accountNameA = param.getAccountNameA();
		String accountNameB = param.getAccountNameB();
		if(StringUtil.isNullOrEmpty(accountNameA) || StringUtil.isNullOrEmpty(accountNameB)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请传递账号名");
		}
		
		User user1;
		User user2;
		
		//查询用户是否存在
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("accountname", accountNameA);
		List<User> users = userMapper.selectByParam(paramMap);
		if(users == null || users.size() != 1){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, accountNameA + " 用户不存在");
		}
		user1 = users.get(0);
		paramMap.put("accountname", accountNameB);
		users = userMapper.selectByParam(paramMap);
		if(users == null || users.size() != 1){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, accountNameB + " 用户不存在");
		}
		user2 = users.get(0);
		
		UserRelationshipKey userRelationshipKey = new UserRelationshipKey();
		
		userRelationshipKey.setAuserid(user1.getUserid());
		userRelationshipKey.setBuserid(user2.getUserid());
		userRelationshipMapper.insert(userRelationshipKey);//A添加B
		
		
		userRelationshipKey.setAuserid(user2.getUserid());
		userRelationshipKey.setBuserid(user1.getUserid());
		userRelationshipMapper.insert(userRelationshipKey);//B添加A
		
		
		return param;
	}
	
	/**
	 * 获取用户好友列表
	 * @param user
	 * @return
	 */
	public List<UserCustom> getUserFriends(User user){
		List<UserCustom> result = new ArrayList<>();
		
		if(user == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "参数不能为空");
		}
		Long userid = user.getUserid();
		String accountName = user.getAccountname();
		if(userid == null && StringUtil.isNullOrEmpty(accountName)){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "请传递正确的用户id或accountName");
		}
		if(userid != null){
			//查询用户好友列表
			result = userRelationshipMapper.selectUserFriends(userid);
		}else{
			//根据账号名获取用户
			Map<String, Object> param = new HashMap<>();
			param.put("accountname", accountName);
			List<User> users = userMapper.selectByParam(param);
			if(users == null || users.size() == 0){
				throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "用户不存在");
			}
			userid = users.get(0).getUserid();
			result = userRelationshipMapper.selectUserFriends(userid);
		}
		
		return result;
	}
	
	/**
	 * 管理后台  获取用户列表
	 * @param param
	 * @return
	 */
	public PageInfo getUserList(MesGetUserList param){
		PageInfo result = new PageInfo();
		
		Page<?> page = PageHelper.startPage(param.getCurrentPage() <= 0 ? 1 : param.getCurrentPage(), param.getPageSize() <= 0 ? 10 : param.getPageSize());
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("accountname", param.getAccountname());
		paramMap.put("nickname", param.getNickname());
		
		List<User> userList = userMapper.selectByParam(paramMap);
		
		result.setTotal(page.getTotal());
		result.setRows(userList);
		
		return result;
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user){
		if(user == null){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "参数不能为空");
		}
		
		if(user.getUserid() == null || user.getUserid() <= 0){
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "userid不能为空");
		}
		
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 获取用户背包列表
	 * @param userId
	 * @return
	 */
	public PageInfo getUserPackageList(Long userId){
		return packageService.getUserPackageList2(userId);
	}

	/**
	 * 用户授权微信登录回调服务
	 * @param code
	 * @return
	 */
	public User loginwx(String code) {
		User result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		//1通过code获取网页授权access_token
//		String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx324313fc147c74e2&secret=0309d8292d03a12d9651cf49d12848a2&code="+code+"&grant_type=authorization_code";
		String getAccessTokenUrl = UtilConstant.getAccessTokenUrl;
		getAccessTokenUrl = String.format(getAccessTokenUrl, UtilConstant.appid,UtilConstant.secret,code);
		
//		String getUserinfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
		String getUserinfoUrl = UtilConstant.getUserinfoUrl;
		
//		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String getAccessTokenUrlJson = HttpClientUtil.doGet(getAccessTokenUrl);
		try {
			GetAccessTokenResponse getAccessTokenResponse = objectMapper.readValue(getAccessTokenUrlJson, GetAccessTokenResponse.class);
			
			//2通过access_token获取用户基本信息
			String access_token = getAccessTokenResponse.getAccess_token();
			String openid = getAccessTokenResponse.getOpenid();
			getUserinfoUrl = String.format(getUserinfoUrl, access_token,openid);
			String getUserinfoUrlJson = HttpClientUtil.doGet(getUserinfoUrl);
			GetUserinfoResponse getUserinfoResponse = objectMapper.readValue(getUserinfoUrlJson, GetUserinfoResponse.class);
			
			//3保存基本信息到数据库
			User user = new User();
			user.setAccountname(getUserinfoResponse.getUnionid());
			user.setNickname(getUserinfoResponse.getNickname());
			user.setForeighid(getUserinfoResponse.getUnionid());
			this.registeUser(user);
			result = user;
			
			//4保存基本信息到redis
			String userString = objectMapper.writeValueAsString(user);
			this.jedisUtil.STRINGS.set(user.getAccountname(), userString);
			this.jedisUtil.KEYS.expired(user.getAccountname(), this.timeOut);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("调用微信获取token返回 : " + getAccessTokenUrlJson);
			throw new BizException(StatusCode.FAILURE_AUTHENTICATE, "登录微信失败");
		}
		
		return result;
	}
}
