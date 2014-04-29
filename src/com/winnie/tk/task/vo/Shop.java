package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("Shop")
public class Shop implements Serializable{
    /**����*/
    @Column
    @Id
    private int id;
    /**����������*/
    @Column
    private String shopUrl;
    /**��������*/
    @Column
    private String shopName;
    /**������*/
    @Column
    private String wangwang;
    /**��������*/
    @Column
    private String userRateUrl;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public String getShopUrl(){
        return shopUrl;
    }
    public void setShopUrl(String value){
        this.shopUrl = value;
    }

    public String getShopName(){
        return shopName;
    }
    public void setShopName(String value){
        this.shopName = value;
    }

    public String getWangwang(){
        return wangwang;
    }
    public void setWangwang(String value){
        this.wangwang = value;
    }

    public String getUserRateUrl(){
        return userRateUrl;
    }
    public void setUserRateUrl(String value){
        this.userRateUrl = value;
    }


}
