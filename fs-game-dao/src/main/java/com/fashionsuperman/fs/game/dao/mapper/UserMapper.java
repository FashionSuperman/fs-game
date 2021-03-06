package com.fashionsuperman.fs.game.dao.mapper;

import java.util.List;
import java.util.Map;

import com.fashionsuperman.fs.game.dao.entity.User;

public interface UserMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Wed Mar 22 09:47:29 CST 2017
	 */
	int deleteByPrimaryKey(Long userid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Wed Mar 22 09:47:29 CST 2017
	 */
	int insert(User record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Wed Mar 22 09:47:29 CST 2017
	 */
	int insertSelective(User record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Wed Mar 22 09:47:29 CST 2017
	 */
	User selectByPrimaryKey(Long userid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Wed Mar 22 09:47:29 CST 2017
	 */
	int updateByPrimaryKeySelective(User record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Wed Mar 22 09:47:29 CST 2017
	 */
	int updateByPrimaryKey(User record);
	/**
	 * 根据参数查询用户
	 * @param param
	 * @return
	 */
	List<User> selectByParam(Map<String, Object> param);
	/**
	 * 主键递增插入
	 * @param user
	 * @return
	 */
	int insertIncrement(User user);

	/**
	 * 根据openId查询用户
	 * @param openid
	 * @return
	 */
	User selectByForeignId(String openid);
}