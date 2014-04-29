package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("ShopProduct")
public class ShopProduct implements Serializable{
    /**主键*/
    @Column
    @Id
    private int id;
    /**店铺ID*/
    @Column
    private int shopId;
    /**产品ID*/
    @Column
    private int itemId;
    /**产品名称*/
    @Column
    private String productName;
    /**监控状态*/
    @Column
    private int logState;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public int getShopId(){
        return shopId;
    }
    public void setShopId(int value){
        this.shopId = value;
    }

    public int getItemId(){
        return itemId;
    }
    public void setItemId(int value){
        this.itemId = value;
    }

    public String getProductName(){
        return productName;
    }
    public void setProductName(String value){
        this.productName = value;
    }

    public int getLogState(){
        return logState;
    }
    public void setLogState(int value){
        this.logState = value;
    }


}
