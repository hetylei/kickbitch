package com.winnie.pub.file.dao;

import com.winnie.pub.file.vo.*;

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
public class FileJdbcDao implements FileDao{
    private static Logger logger = Logger.getLogger(FileJdbcDao.class);
    private Dao dao;

    public FileJdbcDao(Dao dao){
        logger.debug("Dao :"+this.getClass().getName());
        this.dao = dao;
    }

    //#appendbegin
    //==============UploadFile==============
    String UploadFile_QUERY_SQL = "select * from UploadFile t ";

    private String createUploadFileFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("sn") != null && !param.get("sn").equals("")) {
            filter += " and t.sn = @sn ";
        }
        if (param.get("bizType") != null && !param.get("bizType").equals("")) {
            filter += " and t.bizType = @bizType ";
        }
        if (param.get("bizSn") != null && !param.get("bizSn").equals("")) {
            filter += " and t.bizSn = @bizSn ";
        }
        if (param.get("bizSign") != null && !param.get("bizSign").equals("")) {
            filter += " and t.bizSign = @bizSign ";
        }
        if (param.get("fileName") != null && !param.get("fileName").equals("")) {
            filter += " and t.fileName = @fileName ";
        }
        if (param.get("fileSize") != null && !param.get("fileSize").equals("")) {
            filter += " and t.fileSize = @fileSize ";
        }
        if (param.get("filePath") != null && !param.get("filePath").equals("")) {
            filter += " and t.filePath = @filePath ";
        }
        if (param.get("createTime") != null && !param.get("createTime").equals("")) {
            filter += " and t.createTime = @createTime ";
        }
        if (param.get("createUser") != null && !param.get("createUser").equals("")) {
            filter += " and t.createUser = @createUser ";
        }

        return filter;
    }

    private String createUploadFileOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public void insertUploadFile(UploadFile vo) {
        dao.insert(vo);
    }

    public void updateUploadFile(UploadFile vo) {
        dao.update(vo);
    }

    public void deleteUploadFile(UploadFile vo) {
        dao.delete(vo);
    }

    public void deleteUploadFileByPrimaryKey(String sn ) {
        dao.delete(UploadFile.class, sn);
    }

    public void deleteUploadFileByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        deleteUploadFileByParam(map);
    }

    public void deleteUploadFileByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createUploadFileFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from UploadFile " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
        } 
    }

    public UploadFile getUploadFileByPrimaryKey(String sn ) {
        String sql= UploadFile_QUERY_SQL + " where t.sn = @sn  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", sn);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(UploadFile.class));
        dao.execute(s);
        return s.getObject(UploadFile.class);
    }

    public int getUploadFileCountByPrimaryKey(String sn ) {
        String sql="select count(sn) from UploadFile t where t.sn = @sn   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", sn);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getUploadFileCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getUploadFileCountByParam(map);
    }

    public int getUploadFileCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createUploadFileFilter
        String filter = createUploadFileFilter(param);
        String sql = "select count(sn) from UploadFile t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<UploadFile> getUploadFileListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getUploadFileListByParam(orderBy, map);
    }

    public List<UploadFile> getUploadFileListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getUploadFileListByParam(orderBy, map, page, count);
    }

    public List<UploadFile> getUploadFileListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createUploadFileFilter
        String filter = createUploadFileFilter(param);
        String sql = "select * from (" + UploadFile_QUERY_SQL + filter + ") dataall " + createUploadFileOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(UploadFile.class));
        dao.execute(s);
        return s.getList(UploadFile.class);
    }

    public List<UploadFile> getUploadFileListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createUploadFileFilter
        String filter = createUploadFileFilter(param);
        String sql =  "select * from (" + UploadFile_QUERY_SQL + filter + ") dataall " + createUploadFileOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(UploadFile.class));
        dao.execute(s);
        return s.getList(UploadFile.class);


    }

    public List<UploadFile> getAllUploadFileList(String orderBy){
	    String sql = UploadFile_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createUploadFileOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(UploadFile.class));
        dao.execute(s);
        return s.getList(UploadFile.class);
    }
    //==============end of UploadFile==============
    //#appendend
}
