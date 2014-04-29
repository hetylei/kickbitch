package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("ShopProductRate")
public class ShopProductRate implements Serializable{
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
    /**��������*/
    @Column
    private int tradeCount;
    /**���׵���*/
    @Column
    private double tradePrice;
    /**�������*/
    @Column
    private String wangwang;
    /**��������ȼ�*/
    @Column
    private String wangwangRater;
    /**��������*/
    @Column
    private String rateKind;
    /**�������*/
    @Column
    private String userRate;
    /**����ʱ��*/
    @Column
    private String rateTime;


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

    public String getRateKind(){
        return rateKind;
    }
    public void setRateKind(String value){
        this.rateKind = value;
    }

    public String getUserRate(){
        return userRate;
    }
    public void setUserRate(String value){
        this.userRate = value;
    }

    public String getRateTime(){
        return rateTime;
    }
    public void setRateTime(String value){
        this.rateTime = value;
    }


}
