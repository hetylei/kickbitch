package com.winnie.pub.acl.vo;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("sysOrg")
public class SysOrg implements Serializable{
    /**主键*/
    @Column
    @Name
    private String sn;
    /**客户sm*/
    @Column
    private String customerSn;
    /**机构名称*/
    @Column
    private String orgName;
    /**上级机构*/
    @Column
    private String parentSn;
    /**机构级别*/
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
