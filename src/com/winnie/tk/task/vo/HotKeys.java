package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("HotKeys")
public class HotKeys implements Serializable{
    public static String UP = "上升";
    public static String HOT = "热门";
    /**主键*/
    @Column
    @Id
    private int id;
    /**关键词类型*/
    @Column
    private String keyKind;
    /**下载日期*/
    @Column
    private Date downloadDate;
    /**地址*/
    @Column
    private String url;
    /**类目*/
    @Column
    private String catalog;
    /**关键词*/
    @Column
    private String keyWord;
    /**排序号*/
    @Column
    private int sortno;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public String getKeyKind(){
        return keyKind;
    }
    public void setKeyKind(String value){
        this.keyKind = value;
    }

    public Date getDownloadDate(){
        return downloadDate;
    }
    public void setDownloadDate(Date value){
        this.downloadDate = value;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String value){
        this.url = value;
    }

    public String getCatalog(){
        return catalog;
    }
    public void setCatalog(String value){
        this.catalog = value;
    }

    public String getKeyWord(){
        return keyWord;
    }
    public void setKeyWord(String value){
        this.keyWord = value;
    }

    public int getSortno(){
        return sortno;
    }
    public void setSortno(int value){
        this.sortno = value;
    }


}
