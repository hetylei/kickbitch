package com.winnie.pub.acl.vo;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("sysOrg")
public class SysOrg implements Serializable{
    /**����*/
    @Column
    @Name
    private String sn;
    /**�ͻ�sm*/
    @Column
    private String customerSn;
    /**��������*/
    @Column
    private String orgName;
    /**�ϼ�����*/
    @Column
    private String parentSn;
    /**��������*/
    @Column
    private int orgLevel;


    public String getSn(){
        return sn;
    }
    public void setSn(String value){
        this.sn = value;
    }

    public String getCustomerSn(){
        return customerSn;
    }
    public void setCustomerSn(String value){
        this.customerSn = value;
    }

    public String getOrgName(){
        return orgName;
    }
    public void setOrgName(String value){
        this.orgName = value;
    }

    public String getParentSn(){
        return parentSn;
    }
    public void setParentSn(String value){
        this.parentSn = value;
    }

    public int getOrgLevel(){
        return orgLevel;
    }
    public void setOrgLevel(int value){
        this.orgLevel = value;
    }


}
