package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("P4PKeys")
public class P4PKeys implements Serializable{
    /**主键*/
    @Column
    @Id
    private int id;
    /**直通车主图关键词*/
    @Column
    private String p4pids;
    @Column
    private String catalog;
    @Column
    private String itemId;
    @Column
    private String shopId;
    @Column
    private String shopName;
    @Column
    private String itemName;
    @Column
    private int isUse;




    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public String getP4pids(){
        return p4pids;
    }
    public void setP4pids(String value){
        this.p4pids = value;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int use) {
        isUse = use;
    }
}
