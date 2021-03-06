package com.fashionsuperman.fs.game.dao.entity;

import java.util.Date;

public class UserOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.OrderId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private String orderid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.UserId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.CommodityId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private Long commodityid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.Number
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private Integer number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.Note
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.CreateDate
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private Date createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UserOrder.DealFlag
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    private String dealflag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.OrderId
     *
     * @return the value of UserOrder.OrderId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.OrderId
     *
     * @param orderid the value for UserOrder.OrderId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.UserId
     *
     * @return the value of UserOrder.UserId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.UserId
     *
     * @param userid the value for UserOrder.UserId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.CommodityId
     *
     * @return the value of UserOrder.CommodityId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public Long getCommodityid() {
        return commodityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.CommodityId
     *
     * @param commodityid the value for UserOrder.CommodityId
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setCommodityid(Long commodityid) {
        this.commodityid = commodityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.Number
     *
     * @return the value of UserOrder.Number
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.Number
     *
     * @param number the value for UserOrder.Number
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.Note
     *
     * @return the value of UserOrder.Note
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.Note
     *
     * @param note the value for UserOrder.Note
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.CreateDate
     *
     * @return the value of UserOrder.CreateDate
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.CreateDate
     *
     * @param createdate the value for UserOrder.CreateDate
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UserOrder.DealFlag
     *
     * @return the value of UserOrder.DealFlag
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public String getDealflag() {
        return dealflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UserOrder.DealFlag
     *
     * @param dealflag the value for UserOrder.DealFlag
     *
     * @mbggenerated Wed Jul 05 10:21:18 CST 2017
     */
    public void setDealflag(String dealflag) {
        this.dealflag = dealflag;
    }
}