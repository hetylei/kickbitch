package com.winnie.pub.acl.dao;

import com.winnie.pub.acl.vo.*;

import java.util.Map;
import java.util.List;

/**
 * Created by DaoCreator (daoInterface.jt)
 */
public interface AclDao {
//#appendbegin
    //==============SysUser==============
    public void insertSysUser(SysUser vo);
    public void updateSysUser(SysUser vo);

    public void deleteSysUser(SysUser vo);
    public void deleteSysUserByPrimaryKey(String sn );
    public void deleteSysUserByParam(Object... param);
    public void deleteSysUserByParam(Map<String, Object> param);

    public SysUser getSysUserByPrimaryKey(String sn );
    
    public int getSysUserCountByPrimaryKey(String sn );
    public int getSysUserCountByParam(Object... param);
    public int getSysUserCountByParam(Map<String, Object> param);

    public List<SysUser> getSysUserListByParam(Object... param);
    public List<SysUser> getSysUserListByParam(int page, int count, Object... param);
    public List<SysUser> getSysUserListByParam(Map<String, Object> param);
    public List<SysUser> getSysUserListByParam(Map<String, Object> param, int page, int count);
    public List<SysUser> getAllSysUserList();	
    //==============end of SysUser==============	
//#appendend   

    //==============SysOrg==============
    public void insertSysOrg(SysOrg vo);
    public void updateSysOrg(SysOrg vo);

    public void deleteSysOrg(SysOrg vo);
    public void deleteSysOrgByPrimaryKey(String sn );
    public void deleteSysOrgByParam(Object... param);
    public void deleteSysOrgByParam(Map<String, Object> param);

    public SysOrg getSysOrgByPrimaryKey(String sn );
    
    public int getSysOrgCountByPrimaryKey(String sn );
    public int getSysOrgCountByParam(Object... param);
    public int getSysOrgCountByParam(Map<String, Object> param);

    public List<SysOrg> getSysOrgListByParam(Object... param);
    public List<SysOrg> getSysOrgListByParam(int page, int count, Object... param);
    public List<SysOrg> getSysOrgListByParam(Map<String, Object> param);
    public List<SysOrg> getSysOrgListByParam(Map<String, Object> param, int page, int count);
    public List<SysOrg> getAllSysOrgList();	
    //==============end of SysOrg==============	


}
