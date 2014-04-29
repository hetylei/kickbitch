package com.winnie.pub.file.vo;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("UploadFile")
public class UploadFile implements Serializable{
    /**主键*/
    @Column
    @Name
    private String sn;
    /**上传文件模块类型*/
    @Column
    private String bizType;
    /**上传文件模块Sn*/
    @Column
    private String bizSn;
    /**上传文件模块标识*/
    @Column
    private String bizSign;
    /**上传文件名*/
    @Column
    private String fileName;
    /**上传文件名类型*/
    @Column
    private String contentType;
    /**上传文件长度*/
    @Column
    private double fileSize;
    /**上传文件路径（相对)*/
    @Column
    private String filePath;
    /**创建时间*/
    @Column
    private Date createTime;
    /**创建用户*/
    @Column
    private String createUser;


    public String getSn(){
        return sn;
    }
    public void setSn(String value){
        this.sn = value;
    }

    public String getBizType(){
        return bizType;
    }
    public void setBizType(String value){
        this.bizType = value;
    }

    public String getBizSn(){
        return bizSn;
    }
    public void setBizSn(String value){
        this.bizSn = value;
    }

    public String getBizSign(){
        return bizSign;
    }
    public void setBizSign(String value){
        this.bizSign = value;
    }

    public String getFileName(){
        return fileName;
    }
    public void setFileName(String value){
        this.fileName = value;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public double getFileSize(){
        return fileSize;
    }
    public void setFileSize(double value){
        this.fileSize = value;
    }

    public String getFilePath(){
        return filePath;
    }
    public void setFilePath(String value){
        this.filePath = value;
    }

    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date value){
        this.createTime = value;
    }

    public String getCreateUser(){
        return createUser;
    }
    public void setCreateUser(String value){
        this.createUser = value;
    }


}
