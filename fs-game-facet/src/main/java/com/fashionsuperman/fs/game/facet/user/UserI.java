package com.fashionsuperman.fs.game.facet.user;

import java.util.List;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.dao.entity.User;
import com.fashionsuperman.fs.game.dao.entity.UserRelationshipKey;
import com.fashionsuperman.fs.game.dao.entity.custom.UserCustom;
import com.fashionsuperman.fs.game.facet.user.message.MesUserAddFriendByAccountName;
import com.fashionsuperman.fs.game.facet.user.message.ResLoginwx;

/**
 * 用户接口定义
 * @description 
 * @author FashionSuperman
 * @date 2017年3月22日 下午2:10:00
 * @version 1.0
 */
public interface UserI {
	/**
	 * 注册用户
	 * @param user
	 */
	User registeUser(User user) throws BizException;
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User userLogin(User user) throws BizException;
	/**
	 * 用户添加好友
	 * @param param
	 */
	UserRelationshipKey userAddFriend(UserRelationshipKey param) throws BizException;
	
	/**
	 * 通过账号名添加好友
	 * @param param
	 * @return
	 */
	MesUserAddFriendByAccountName userAddFriendByAccountName(MesUserAddFriendByAccountName param) throws BizException;
	
	/**
	 * 获取用户好友列表(带有用户分数)
	 * @param user
	 * @return
	 */
	List<UserCustom> getUserFriends(User user) throws BizException;
	
	/**
	 * 管理后台获取用户列表
	 * @param param
	 * @return
	 */
//	public PageInfo getUserList(MesGetUserList param);
	
	
	/**
	 * 用户授权微信登录回调
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public User loginwx() throws BizException;
}
