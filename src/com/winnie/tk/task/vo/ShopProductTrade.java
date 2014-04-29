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
    /**����*/
    @Column
    @Id
    private int id;
    /**��ƷID*/
    @Column
    private int productId;
    /**����ʱ������*/
    @Column
    private String tradeName;
    /**����ʱ��*/
    @Column
    private String tradeTime;
    /**��������*/
    @Column
    private int tradeCount;
    /**���׵���*/
    @Column
    private double tradePrice;
    /**��ǰ�۳�����*/
    @Column
    private int saleCounts;
    /**��ǰ�۳�����*/
    @Column
    private int orderCounts;
    /**��ǰ��������*/
    @Column
    private int rateCounts;
    /**�������*/
    @Column
    private String wangwang;
    /**��������ȼ�*/
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
