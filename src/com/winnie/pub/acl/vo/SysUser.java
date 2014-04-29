package com.winnie.pub.acl.vo;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("sysUser")
public class SysUser implements Serializable{
    /**����*/
    @Column
    @Name
    private String sn;
    /**�ͻ�*/
    @Column
    private String customerSn;
    /**��¼��*/
    @Column
    private String loginName;
    /**�û���*/
    @Column
    private String userName;
    /**����*/
    @Column
    private String password;
    /**��˾*/
    @Column
    private String orgSn;


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

    public String getLoginName(){
        return loginName;
    }
    public void setLoginName(String value){
        this.loginName = value;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String value){
        this.userName = value;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String value){
        this.password = value;
    }

    public String getOrgSn(){
        return orgSn;
    }
    public void setOrgSn(String value){
        this.orgSn = value;
    }


}
