package com.winnie.test.dao;

import com.winnie.test.vo.*;

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
public class ComputerJdbcDao implements ComputerDao{
    private static Logger logger = Logger.getLogger(ComputerJdbcDao.class);
    private Dao dao;

    public ComputerJdbcDao(Dao dao){
        logger.debug("Dao :"+this.getClass().getName());
        this.dao = dao;
    }

    //#appendbegin
    //==============Computer==============
    String Computer_QUERY_SQL = "select * from Computer t ";

    private String createComputerFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("cpu") != null && !param.get("cpu").equals("")) {
            filter += " and t.cpu = @cpu ";
        }
        if (param.get("memory") != null && !param.get("memory").equals("")) {
            filter += " and t.memory = @memory ";
        }
        if (param.get("hardDisk") != null && !param.get("hardDisk").equals("")) {
            filter += " and t.hardDisk = @hardDisk ";
        }
        if (param.get("keyBoard") != null && !param.get("keyBoard").equals("")) {
            filter += " and t.keyBoard = @keyBoard ";
        }
        if (param.get("mouse") != null && !param.get("mouse").equals("")) {
            filter += " and t.mouse = @mouse ";
        }
        if (param.get("cpuCoreCount") != null && !param.get("cpuCoreCount").equals("")) {
            filter += " and t.cpuCoreCount = @cpuCoreCount ";
        }
        if (param.get("madeDate") != null && !param.get("madeDate").equals("")) {
            filter += " and t.madeDate = @madeDate ";
        }

        return filter;
    }

    private String createComputerOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public Computer insertComputer(Computer vo) {
        return dao.insert(vo);
    }

    public int updateComputer(Computer vo) {
        return dao.update(vo);
    }

    public int updateComputerIgnoreNull(Computer vo) {
        return dao.update(vo);
    }

    public int deleteComputer(Computer vo) {
        return dao.delete(vo);
    }

    public int deleteComputerByPrimaryKey(String id ) {
        return dao.delete(Computer.class, id);
    }

    public int deleteComputerByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteComputerByParam(map);
    }

    public int deleteComputerByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createComputerFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from Computer t " + filter;
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public Computer getComputerByPrimaryKey(String id ) {
        String sql= Computer_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Computer.class));
        dao.execute(s);
        return s.getObject(Computer.class);
    }

    public int getComputerCountByPrimaryKey(String id ) {
        String sql="select count(id) from Computer t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getComputerCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getComputerCountByParam(map);
    }

    public int getComputerCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createComputerFilter
        String filter = createComputerFilter(param);
        String sql = "select count(id) from Computer t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<Computer> getComputerListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getComputerListByParam(orderBy, map);
    }

    public List<Computer> getComputerListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getComputerListByParam(orderBy, map, page, count);
    }

    public List<Computer> getComputerListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createComputerFilter
        String filter = createComputerFilter(param);
        String sql = "select * from (" + Computer_QUERY_SQL + filter + ") dataall " + createComputerOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Computer.class));
        dao.execute(s);
        return s.getList(Computer.class);
    }

    public List<Computer> getComputerListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        if (orderBy == null || orderBy.equals("")) orderBy = " id " ;
        if (page<1) page =1;
        
        //赋值总记录数
        int recordCount = this.getComputerCountByParam(param);
        //赋值总页面数
        int pageCount = (recordCount+count-1) / count;
        if (page>pageCount) page = pageCount;
        //申明“剩余记录”
        int residualRecord = recordCount - (page-1)*count;

        //to add filter modify createComputerFilter
        String filter = createComputerFilter(param);
        String sql;
        
        //当第一页时，直接一次top语句
        if (page==1 || pageCount <=1) {
            sql = "select top "+count+" * from ("+Computer_QUERY_SQL + filter+") tt ";    
        } else if (page == pageCount) {
            //当最后一页时，对剩余记录进行查询操作
            sql= "select * from (select top " +residualRecord+" * from ("+
                    Computer_QUERY_SQL + filter+") tt order by "+orderBy+" desc) ttt " +
                "order by "+orderBy+" asc";
        } else {
            //否则，top两次翻转。首先,通过降序排列在整张表中筛选出所有剩余记录，然后在对这些记录进行翻转排序取出前@pageSize条记录。
            sql= "select top "+count+" * from (select top "+residualRecord +" * from ("+
                    Computer_QUERY_SQL + filter +") tt order by "+orderBy+" desc) ttt " +
                    " order by "+ orderBy+" asc";
        }

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Computer.class));
        dao.execute(s);
        return s.getList(Computer.class);


    }

    public List<Computer> getAllComputerList(String orderBy){
	    String sql = Computer_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createComputerOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Computer.class));
        dao.execute(s);
        return s.getList(Computer.class);
    }
    //==============end of Computer==============
    //#appendend
}