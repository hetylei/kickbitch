package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("ShopProductTrade")
public class ShopProductTrade implements Serializable{
    /**主键*/
    @Column
    @Id
    private int id;
    /**产品ID*/
    @Column
    private int productId;
    /**交易时的名称*/
    @Column
    private String tradeName;
    /**交易时间*/
    @Column
    private String tradeTime;
    /**交易数量*/
    @Column
    private int tradeCount;
    /**交易单价*/
    @Column
    private double tradePrice;
    /**当前售出数量*/
    @Column
    private int saleCounts;
    /**当前售出笔数*/
    @Column
    private int orderCounts;
    /**当前评论数量*/
    @Column
    private int rateCounts;
    /**买家旺旺*/
    @Column
    private String wangwang;
    /**买家旺旺等级*/
    @Column
    private String wangwangRater;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public int getProductId(){
        return productId;
    }
    public void setProductId(int value){
        this.productId = value;
    }

    public String getTradeName(){
        return tradeName;
    }
    public void setTradeName(String value){
        this.tradeName = value;
    }

    public String getTradeTime(){
        return tradeTime;
    }
    public void setTradeTime(String value){
        this.tradeTime = value;
    }

    public int getTradeCount(){
        return tradeCount;
    }
    public void setTradeCount(int value){
        this.tradeCount = value;
    }

    public double getTradePrice(){
        return tradePrice;
    }
    public void setTradePrice(double value){
        this.tradePrice = value;
    }

    public int getSaleCounts(){
        return saleCounts;
    }
    public void setSaleCounts(int value){
        this.saleCounts = value;
    }

    public int getOrderCounts(){
        return orderCounts;
    }
    public void setOrderCounts(int value){
        this.orderCounts = value;
    }

    public int getRateCounts(){
        return rateCounts;
    }
    public void setRateCounts(int value){
        this.rateCounts = value;
    }

    public String getWangwang(){
        return wangwang;
    }
    public void setWangwang(String value){
        this.wangwang = value;
    }

    public String getWangwangRater(){
        return wangwangRater;
    }
    public void setWangwangRater(String value){
        this.wangwangRater = value;
    }


}
