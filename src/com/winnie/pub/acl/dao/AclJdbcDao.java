package com.winnie.pub.acl.dao;

import com.winnie.pub.acl.vo.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

/**
 * Created by DaoCreator (daoJdbc.jt)
 */
public class AclJdbcDao implements AclDao{
    private static Logger logger = Logger.getLogger(AclJdbcDao.class);
    private Dao dao;

    public AclJdbcDao(Dao dao){
        logger.debug("Dao :"+this.getClass().getName());
        this.dao = dao;
    }

    //#appendbegin
    //==============SysUser==============
    String SysUser_QUERY_SQL = "select * from SysUser t ";

    private String createSysUserFilter(Map<String, Object> param) {
        //如果添加查询条件 需要修改此处的filter
        String filter = " where 1=1 ";
        if (param.get("sn") != null && !param.get("sn").equals("")) {
            filter += " and t.sn = @sn ";
        }
        if (param.get("customerSn") != null && !param.get("customerSn").equals("")) {
            filter += " and t.customerSn = @customerSn ";
        }
        if (param.get("loginName") != null && !param.get("loginName").equals("")) {
            filter += " and t.loginName = @loginName ";
        }
        if (param.get("userName") != null && !param.get("userName").equals("")) {
            filter += " and t.userName = @userName ";
        }
        if (param.get("password") != null && !param.get("password").equals("")) {
            filter += " and t.password = @password ";
        }
        if (param.get("orgSn") != null && !param.get("orgSn").equals("")) {
            filter += " and t.orgSn = @orgSn ";
        }

        return filter;
    }

    public void insertSysUser(SysUser vo) {
        dao.insert(vo);
    }

    public void updateSysUser(SysUser vo) {
        dao.update(vo);
    }

    public void deleteSysUser(SysUser vo) {
        dao.delete(vo);
    }

    public void deleteSysUserByPrimaryKey(String sn ) {
        dao.delete(SysUser.class, sn);
    }

    public void deleteSysUserByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        deleteSysUserByParam(map);
    }

    public void deleteSysUserByParam(Map<String, Object> param) {
        String filter = createSysUserFilter(param);
        //没有任何条件 禁止删除 防止误删除所有数据
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from SysUser t " + filter;
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
        } 
    }

    public SysUser getSysUserByPrimaryKey(String sn ) {
        String sql= SysUser_QUERY_SQL + " where t.sn = @sn  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", sn);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysUser.class));
        dao.execute(s);
        return s.getObject(SysUser.class);
    }

    public int getSysUserCountByPrimaryKey(String sn ) {
        String sql="select count(sn) from SysUser t where t.sn = @sn   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", sn);
        dao.execute(s);
        return s.getInt();
    }

    public int getSysUserCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getSysUserCountByParam(map);
    }

    public int getSysUserCountByParam(Map<String, Object> param) {
        //如果添加查询条件 需要修改 createSysUserFilter
        String filter = createSysUserFilter(param);
        String sql = "select count(sn) from SysUser t " + filter;
        Sql s = Sqls.create(sql);
        s.setCallback(Sqls.callback.integer());
        s.params().putAll(param);
        dao.execute(s);
        return s.getInt();
    }

    public List<SysUser> getSysUserListByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getSysUserListByParam(map);
    }

    public List<SysUser> getSysUserListByParam(int page, int count, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getSysUserListByParam(map, page, count);
    }

    public List<SysUser> getSysUserListByParam(Map<String, Object> param) {
        //如果添加查询条件 需要修改 createSysUserFilter
        String filter = createSysUserFilter(param);
        String sql = SysUser_QUERY_SQL + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysUser.class));
        dao.execute(s);
        return s.getList(SysUser.class);
    }

    public List<SysUser> getSysUserListByParam(Map<String, Object> param, int page, int count) {
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //如果添加查询条件 需要修改 createSysUserFilter
        String filter = createSysUserFilter(param);
        String sql =  SysUser_QUERY_SQL + filter + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysUser.class));
        dao.execute(s);
        return s.getList(SysUser.class);


    }

    public List<SysUser> getAllSysUserList(){
	    String sql = SysUser_QUERY_SQL;
		Sql s = Sqls.create(sql);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysUser.class));
        dao.execute(s);
        return s.getList(SysUser.class);
    }
    //==============end of SysUser==============
    //#appendend

    //==============SysOrg==============
    String SysOrg_QUERY_SQL = "select * from SysOrg t ";

    private String createSysOrgFilter(Map<String, Object> param) {
        //如果添加查询条件 需要修改此处的filter
        String filter = " where 1=1 ";
        if (param.get("sn") != null && !param.get("sn").equals("")) {
            filter += " and t.sn = @sn ";
        }
        if (param.get("customerSn") != null && !param.get("customerSn").equals("")) {
            filter += " and t.customerSn = @customerSn ";
        }
        if (param.get("orgName") != null && !param.get("orgName").equals("")) {
            filter += " and t.orgName = @orgName ";
        }
        if (param.get("parentSn") != null && !param.get("parentSn").equals("")) {
            filter += " and t.parentSn = @parentSn ";
        }
        if (param.get("orgLevel") != null && !param.get("orgLevel").equals("")) {
            filter += " and t.orgLevel = @orgLevel ";
        }

        return filter;
    }

    public void insertSysOrg(SysOrg vo) {
        dao.insert(vo);
    }

    public void updateSysOrg(SysOrg vo) {
        dao.update(vo);
    }

    public void deleteSysOrg(SysOrg vo) {
        dao.delete(vo);
    }

    public void deleteSysOrgByPrimaryKey(String sn ) {
        dao.delete(SysOrg.class, sn);
    }

    public void deleteSysOrgByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        deleteSysOrgByParam(map);
    }

    public void deleteSysOrgByParam(Map<String, Object> param) {
        String filter = createSysOrgFilter(param);
        //没有任何条件 禁止删除 防止误删除所有数据
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from SysOrg t " + filter;
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
        } 
    }

    public SysOrg getSysOrgByPrimaryKey(String sn ) {
        String sql= SysOrg_QUERY_SQL + " where t.sn = @sn  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", sn);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysOrg.class));
        dao.execute(s);
        return s.getObject(SysOrg.class);
    }

    public int getSysOrgCountByPrimaryKey(String sn ) {
        String sql="select count(sn) from SysOrg t where t.sn = @sn   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", sn);
        dao.execute(s);
        return s.getInt();
    }

    public int getSysOrgCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getSysOrgCountByParam(map);
    }

    public int getSysOrgCountByParam(Map<String, Object> param) {
        //如果添加查询条件 需要修改 createSysOrgFilter
        String filter = createSysOrgFilter(param);
        String sql = "select count(sn) from SysOrg t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        dao.execute(s);
        return s.getInt();
    }

    public List<SysOrg> getSysOrgListByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getSysOrgListByParam(map);
    }

    public List<SysOrg> getSysOrgListByParam(int page, int count, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getSysOrgListByParam(map, page, count);
    }

    public List<SysOrg> getSysOrgListByParam(Map<String, Object> param) {
        //如果添加查询条件 需要修改 createSysOrgFilter
        String filter = createSysOrgFilter(param);
        String sql = SysOrg_QUERY_SQL + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysOrg.class));
        dao.execute(s);
        return s.getList(SysOrg.class);
    }

    public List<SysOrg> getSysOrgListByParam(Map<String, Object> param, int page, int count) {
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //如果添加查询条件 需要修改 createSysOrgFilter
        String filter = createSysOrgFilter(param);
        String sql =  SysOrg_QUERY_SQL + filter + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysOrg.class));
        dao.execute(s);
        return s.getList(SysOrg.class);


    }

    public List<SysOrg> getAllSysOrgList(){
	    String sql = SysOrg_QUERY_SQL;
		Sql s = Sqls.create(sql);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(SysOrg.class));
        dao.execute(s);
        return s.getList(SysOrg.class);
    }
    //==============end of SysOrg==============
    

}
