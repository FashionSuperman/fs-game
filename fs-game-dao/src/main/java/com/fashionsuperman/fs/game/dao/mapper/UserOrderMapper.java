package com.fashionsuperman.fs.game.dao.mapper;

import com.fashionsuperman.fs.game.dao.entity.UserOrder;

public interface UserOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserOrder
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    int deleteByPrimaryKey(String orderid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserOrder
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    int insert(UserOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserOrder
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    int insertSelective(UserOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserOrder
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    UserOrder selectByPrimaryKey(String orderid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserOrder
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    int updateByPrimaryKeySelective(UserOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table UserOrder
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    int updateByPrimaryKey(UserOrder record);
}