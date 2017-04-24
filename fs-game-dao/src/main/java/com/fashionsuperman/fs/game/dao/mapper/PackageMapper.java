package com.fashionsuperman.fs.game.dao.mapper;

import com.fashionsuperman.fs.game.dao.entity.Package;

public interface PackageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table package
     *
     * @mbggenerated Mon Apr 24 20:53:54 CST 2017
     */
    int deleteByPrimaryKey(Long packageid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table package
     *
     * @mbggenerated Mon Apr 24 20:53:54 CST 2017
     */
    int insert(Package record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table package
     *
     * @mbggenerated Mon Apr 24 20:53:54 CST 2017
     */
    int insertSelective(Package record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table package
     *
     * @mbggenerated Mon Apr 24 20:53:54 CST 2017
     */
    Package selectByPrimaryKey(Long packageid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table package
     *
     * @mbggenerated Mon Apr 24 20:53:54 CST 2017
     */
    int updateByPrimaryKeySelective(Package record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table package
     *
     * @mbggenerated Mon Apr 24 20:53:54 CST 2017
     */
    int updateByPrimaryKey(Package record);

	int insertIncrement(Package packageAdd);

	/**
	 * 获取最新插入的数据
	 * @return
	 */
	Package selectMaxId();
}