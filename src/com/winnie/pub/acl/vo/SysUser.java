package com.winnie.pub.acl.vo;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("sysUser")
public class SysUser implements Serializable{
    /**主键*/
    @Column
    @Name
    private String sn;
    /**客户*/
    @Column
    private String customerSn;
    /**登录名*/
    @Column
    private String loginName;
    /**用户名*/
    @Column
    private String userName;
    /**密码*/
    @Column
    private String password;
    /**公司*/
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
