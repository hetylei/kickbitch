package com.winnie.pub.file.vo;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("UploadFile")
public class UploadFile implements Serializable{
    /**����*/
    @Column
    @Name
    private String sn;
    /**�ϴ��ļ�ģ������*/
    @Column
    private String bizType;
    /**�ϴ��ļ�ģ��Sn*/
    @Column
    private String bizSn;
    /**�ϴ��ļ�ģ���ʶ*/
    @Column
    private String bizSign;
    /**�ϴ��ļ���*/
    @Column
    private String fileName;
    /**�ϴ��ļ�������*/
    @Column
    private String contentType;
    /**�ϴ��ļ�����*/
    @Column
    private double fileSize;
    /**�ϴ��ļ�·�������)*/
    @Column
    private String filePath;
    /**����ʱ��*/
    @Column
    private Date createTime;
    /**�����û�*/
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
