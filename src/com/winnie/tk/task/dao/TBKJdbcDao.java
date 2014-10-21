package com.winnie.tk.task.dao;

import com.winnie.tk.task.vo.*;

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
public class TBKJdbcDao implements TBKDao{
    private static Logger logger = Logger.getLogger(TBKJdbcDao.class);
    private Dao dao;

    public TBKJdbcDao(Dao dao){
        logger.debug("Dao :"+this.getClass().getName());
        this.dao = dao;
    }

    //#appendbegin
    //==============TaskLog==============
    String TaskLog_QUERY_SQL = "select * from TaskLog t ";

    private String createTaskLogFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("taskId") != null && !param.get("taskId").equals("")) {
            filter += " and t.taskId = @taskId ";
        }
        if (param.get("taskTime") != null && !param.get("taskTime").equals("")) {
            filter += " and t.taskTime = @taskTime ";
        }
        if (param.get("taskLog") != null && !param.get("taskLog").equals("")) {
            filter += " and t.taskLog = @taskLog ";
        }

        return filter;
    }

    private String createTaskLogOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public TaskLog insertTaskLog(TaskLog vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateTaskLog(TaskLog vo) {
        return dao.update(vo);
    }

    public int updateTaskLogIgnoreNull(TaskLog vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteTaskLog(TaskLog vo) {
        return dao.delete(vo);
    }

    public int deleteTaskLogByPrimaryKey(String id ) {
        return dao.delete(TaskLog.class, id);
    }

    public int deleteTaskLogByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteTaskLogByParam(map);
    }

    public int deleteTaskLogByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createTaskLogFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from TaskLog " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public TaskLog getTaskLogByPrimaryKey(String id ) {
        String sql= TaskLog_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(TaskLog.class));
        dao.execute(s);
        return s.getObject(TaskLog.class);
    }

    public int getTaskLogCountByPrimaryKey(String id ) {
        String sql="select count(id) from TaskLog t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getTaskLogCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getTaskLogCountByParam(map);
    }

    public int getTaskLogCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createTaskLogFilter
        String filter = createTaskLogFilter(param);
        String sql = "select count(id) from TaskLog t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<TaskLog> getTaskLogListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getTaskLogListByParam(orderBy, map);
    }

    public List<TaskLog> getTaskLogListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getTaskLogListByParam(orderBy, map, page, count);
    }

    public List<TaskLog> getTaskLogListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createTaskLogFilter
        String filter = createTaskLogFilter(param);
        String sql = "select * from (" + TaskLog_QUERY_SQL + filter + ") dataall " + createTaskLogOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(TaskLog.class));
        dao.execute(s);
        return s.getList(TaskLog.class);
    }

    public List<TaskLog> getTaskLogListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createTaskLogFilter
        String filter = createTaskLogFilter(param);
        String sql =  "select * from (" + TaskLog_QUERY_SQL + filter + ") dataall " + createTaskLogOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(TaskLog.class));
        dao.execute(s);
        return s.getList(TaskLog.class);


    }

    public List<TaskLog> getAllTaskLogList(String orderBy){
	    String sql = TaskLog_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createTaskLogOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(TaskLog.class));
        dao.execute(s);
        return s.getList(TaskLog.class);
    }
    //==============end of TaskLog==============
    //#appendend

    //==============Task==============
    String Task_QUERY_SQL = "select * from Task t ";

    private String createTaskFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("taskName") != null && !param.get("taskName").equals("")) {
            filter += " and t.taskName = @taskName ";
        }
        if (param.get("taskWeekDay") != null && !param.get("taskWeekDay").equals("")) {
            filter += " and t.taskWeekDay = @taskWeekDay ";
        }
        if (param.get("taskDay") != null && !param.get("taskDay").equals("")) {
            filter += " and t.taskDay = @taskDay ";
        }
        if (param.get("taskTime") != null && !param.get("taskTime").equals("")) {
            param.put("taskTimeLike", "%" + param.get("taskTime") + "%");
            filter += " and t.taskTime like @taskTimeLike ";
        }
        if (param.get("taskCommand") != null && !param.get("taskCommand").equals("")) {
            filter += " and t.taskCommand = @taskCommand ";
        }
        if (param.get("isUse") != null && !param.get("isUse").equals("")) {
            filter += " and t.isUse = @isUse ";
        }
        if (param.get("isDelete") != null && !param.get("isDelete").equals("")) {
            filter += " and t.isDelete = @isDelete ";
        }

        return filter;
    }

    private String createTaskOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public Task insertTask(Task vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateTask(Task vo) {
        return dao.update(vo);
    }

    public int updateTaskIgnoreNull(Task vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteTask(Task vo) {
        return dao.delete(vo);
    }

    public int deleteTaskByPrimaryKey(String id ) {
        return dao.delete(Task.class, id);
    }

    public int deleteTaskByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteTaskByParam(map);
    }

    public int deleteTaskByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createTaskFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from Task " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public Task getTaskByPrimaryKey(String id ) {
        String sql= Task_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Task.class));
        dao.execute(s);
        return s.getObject(Task.class);
    }

    public int getTaskCountByPrimaryKey(String id ) {
        String sql="select count(id) from Task t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("sn", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getTaskCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getTaskCountByParam(map);
    }

    public int getTaskCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createTaskFilter
        String filter = createTaskFilter(param);
        String sql = "select count(id) from Task t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<Task> getTaskListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getTaskListByParam(orderBy, map);
    }

    public List<Task> getTaskListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getTaskListByParam(orderBy, map, page, count);
    }

    public List<Task> getTaskListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createTaskFilter
        String filter = createTaskFilter(param);
        String sql = "select * from (" + Task_QUERY_SQL + filter + ") dataall " + createTaskOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Task.class));
        dao.execute(s);
        return s.getList(Task.class);
    }

    public List<Task> getTaskListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createTaskFilter
        String filter = createTaskFilter(param);
        String sql =  "select * from (" + Task_QUERY_SQL + filter + ") dataall " + createTaskOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Task.class));
        dao.execute(s);
        return s.getList(Task.class);


    }

    public List<Task> getAllTaskList(String orderBy){
	    String sql = Task_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createTaskOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Task.class));
        dao.execute(s);
        return s.getList(Task.class);
    }
    //==============end of Task==============
    


    //==============HotKeys==============
    String HotKeys_QUERY_SQL = "select * from HotKeys t ";

    private String createHotKeysFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("keyKind") != null && !param.get("keyKind").equals("")) {
            filter += " and t.keyKind = @keyKind ";
        }
        if (param.get("downloadDate") != null && !param.get("downloadDate").equals("")) {
            filter += " and t.downloadDate = @downloadDate ";
        }
        if (param.get("url") != null && !param.get("url").equals("")) {
            filter += " and t.url = @url ";
        }
        if (param.get("catalog") != null && !param.get("catalog").equals("")) {
            filter += " and t.catalog = @catalog ";
        }
        if (param.get("keyWord") != null && !param.get("keyWord").equals("")) {
            filter += " and t.keyWord = @keyWord ";
        }
        if (param.get("sortno") != null && !param.get("sortno").equals("")) {
            filter += " and t.sortno = @sortno ";
        }

        return filter;
    }

    private String createHotKeysOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public HotKeys insertHotKeys(HotKeys vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateHotKeys(HotKeys vo) {
        return dao.update(vo);
    }

    public int updateHotKeysIgnoreNull(HotKeys vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteHotKeys(HotKeys vo) {
        return dao.delete(vo);
    }

    public int deleteHotKeysByPrimaryKey(String id ) {
        return dao.delete(HotKeys.class, id);
    }

    public int deleteHotKeysByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteHotKeysByParam(map);
    }

    public int deleteHotKeysByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createHotKeysFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from HotKeys " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public HotKeys getHotKeysByPrimaryKey(String id ) {
        String sql= HotKeys_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(HotKeys.class));
        dao.execute(s);
        return s.getObject(HotKeys.class);
    }

    public int getHotKeysCountByPrimaryKey(String id ) {
        String sql="select count(id) from HotKeys t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getHotKeysCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getHotKeysCountByParam(map);
    }

    public int getHotKeysCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createHotKeysFilter
        String filter = createHotKeysFilter(param);
        String sql = "select count(id) from HotKeys t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<HotKeys> getHotKeysListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getHotKeysListByParam(orderBy, map);
    }

    public List<HotKeys> getHotKeysListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getHotKeysListByParam(orderBy, map, page, count);
    }

    public List<HotKeys> getHotKeysListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createHotKeysFilter
        String filter = createHotKeysFilter(param);
        String sql = "select * from (" + HotKeys_QUERY_SQL + filter + ") dataall " + createHotKeysOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(HotKeys.class));
        dao.execute(s);
        return s.getList(HotKeys.class);
    }

    public List<HotKeys> getHotKeysListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createHotKeysFilter
        String filter = createHotKeysFilter(param);
        String sql =  "select * from (" + HotKeys_QUERY_SQL + filter + ") dataall " + createHotKeysOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(HotKeys.class));
        dao.execute(s);
        return s.getList(HotKeys.class);


    }

    public List<HotKeys> getAllHotKeysList(String orderBy){
	    String sql = HotKeys_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createHotKeysOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(HotKeys.class));
        dao.execute(s);
        return s.getList(HotKeys.class);
    }
    //==============end of HotKeys==============
    


    //==============P4PKeys==============
    String P4PKeys_QUERY_SQL = "select * from P4PKeys t ";

    private String createP4PKeysFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("p4pids") != null && !param.get("p4pids").equals("")) {
            filter += " and t.p4pids = @p4pids ";
        }

        return filter;
    }

    private String createP4PKeysOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public P4PKeys insertP4PKeys(P4PKeys vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateP4PKeys(P4PKeys vo) {
        return dao.update(vo);
    }

    public int updateP4PKeysIgnoreNull(P4PKeys vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteP4PKeys(P4PKeys vo) {
        return dao.delete(vo);
    }

    public int deleteP4PKeysByPrimaryKey(String id ) {
        return dao.delete(P4PKeys.class, id);
    }

    public int deleteP4PKeysByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteP4PKeysByParam(map);
    }

    public int deleteP4PKeysByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createP4PKeysFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from P4PKeys " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public P4PKeys getP4PKeysByPrimaryKey(String id ) {
        String sql= P4PKeys_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(P4PKeys.class));
        dao.execute(s);
        return s.getObject(P4PKeys.class);
    }

    public int getP4PKeysCountByPrimaryKey(String id ) {
        String sql="select count(id) from P4PKeys t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getP4PKeysCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getP4PKeysCountByParam(map);
    }

    public int getP4PKeysCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createP4PKeysFilter
        String filter = createP4PKeysFilter(param);
        String sql = "select count(id) from P4PKeys t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<P4PKeys> getP4PKeysListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getP4PKeysListByParam(orderBy, map);
    }

    public List<P4PKeys> getP4PKeysListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getP4PKeysListByParam(orderBy, map, page, count);
    }

    public List<P4PKeys> getP4PKeysListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createP4PKeysFilter
        String filter = createP4PKeysFilter(param);
        String sql = "select * from (" + P4PKeys_QUERY_SQL + filter + ") dataall " + createP4PKeysOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(P4PKeys.class));
        dao.execute(s);
        return s.getList(P4PKeys.class);
    }

    public List<P4PKeys> getP4PKeysListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createP4PKeysFilter
        String filter = createP4PKeysFilter(param);
        String sql =  "select * from (" + P4PKeys_QUERY_SQL + filter + ") dataall " + createP4PKeysOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(P4PKeys.class));
        dao.execute(s);
        return s.getList(P4PKeys.class);


    }

    public List<P4PKeys> getAllP4PKeysList(String orderBy){
	    String sql = P4PKeys_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createP4PKeysOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(P4PKeys.class));
        dao.execute(s);
        return s.getList(P4PKeys.class);
    }
    //==============end of P4PKeys==============
    


    //==============Shop==============
    String Shop_QUERY_SQL = "select * from Shop t ";

    private String createShopFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("shopUrl") != null && !param.get("shopUrl").equals("")) {
            filter += " and t.shopUrl = @shopUrl ";
        }
        if (param.get("shopName") != null && !param.get("shopName").equals("")) {
            filter += " and t.shopName = @shopName ";
        }
        if (param.get("wangwang") != null && !param.get("wangwang").equals("")) {
            filter += " and t.wangwang = @wangwang ";
        }
        if (param.get("userRateUrl") != null && !param.get("userRateUrl").equals("")) {
            filter += " and t.userRateUrl = @userRateUrl ";
        }

        return filter;
    }

    private String createShopOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public Shop insertShop(Shop vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateShop(Shop vo) {
        return dao.update(vo);
    }

    public int updateShopIgnoreNull(Shop vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteShop(Shop vo) {
        return dao.delete(vo);
    }

    public int deleteShopByPrimaryKey(String id ) {
        return dao.delete(Shop.class, id);
    }

    public int deleteShopByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteShopByParam(map);
    }

    public int deleteShopByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createShopFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from Shop " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public Shop getShopByPrimaryKey(String id ) {
        String sql= Shop_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Shop.class));
        dao.execute(s);
        return s.getObject(Shop.class);
    }

    public int getShopCountByPrimaryKey(String id ) {
        String sql="select count(id) from Shop t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getShopCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopCountByParam(map);
    }

    public int getShopCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createShopFilter
        String filter = createShopFilter(param);
        String sql = "select count(id) from Shop t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<Shop> getShopListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopListByParam(orderBy, map);
    }

    public List<Shop> getShopListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopListByParam(orderBy, map, page, count);
    }

    public List<Shop> getShopListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createShopFilter
        String filter = createShopFilter(param);
        String sql = "select * from (" + Shop_QUERY_SQL + filter + ") dataall " + createShopOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Shop.class));
        dao.execute(s);
        return s.getList(Shop.class);
    }

    public List<Shop> getShopListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createShopFilter
        String filter = createShopFilter(param);
        String sql =  "select * from (" + Shop_QUERY_SQL + filter + ") dataall " + createShopOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Shop.class));
        dao.execute(s);
        return s.getList(Shop.class);


    }

    public List<Shop> getAllShopList(String orderBy){
	    String sql = Shop_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createShopOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(Shop.class));
        dao.execute(s);
        return s.getList(Shop.class);
    }
    //==============end of Shop==============
    


    //==============ShopProduct==============
    String ShopProduct_QUERY_SQL = "select * from ShopProduct t ";

    private String createShopProductFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("shopId") != null && !param.get("shopId").equals("")) {
            filter += " and t.shopId = @shopId ";
        }
        if (param.get("itemId") != null && !param.get("itemId").equals("")) {
            filter += " and t.itemId = @itemId ";
        }
        if (param.get("productName") != null && !param.get("productName").equals("")) {
            filter += " and t.productName = @productName ";
        }
        if (param.get("logState") != null && !param.get("logState").equals("")) {
            filter += " and t.logState = @logState ";
        }

        return filter;
    }

    private String createShopProductOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public ShopProduct insertShopProduct(ShopProduct vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateShopProduct(ShopProduct vo) {
        return dao.update(vo);
    }

    public int updateShopProductIgnoreNull(ShopProduct vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteShopProduct(ShopProduct vo) {
        return dao.delete(vo);
    }

    public int deleteShopProductByPrimaryKey(String id ) {
        return dao.delete(ShopProduct.class, id);
    }

    public int deleteShopProductByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteShopProductByParam(map);
    }

    public int deleteShopProductByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createShopProductFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from ShopProduct " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public ShopProduct getShopProductByPrimaryKey(String id ) {
        String sql= ShopProduct_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProduct.class));
        dao.execute(s);
        return s.getObject(ShopProduct.class);
    }

    public int getShopProductCountByPrimaryKey(String id ) {
        String sql="select count(id) from ShopProduct t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getShopProductCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductCountByParam(map);
    }

    public int getShopProductCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createShopProductFilter
        String filter = createShopProductFilter(param);
        String sql = "select count(id) from ShopProduct t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<ShopProduct> getShopProductListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductListByParam(orderBy, map);
    }

    public List<ShopProduct> getShopProductListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductListByParam(orderBy, map, page, count);
    }

    public List<ShopProduct> getShopProductListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createShopProductFilter
        String filter = createShopProductFilter(param);
        String sql = "select * from (" + ShopProduct_QUERY_SQL + filter + ") dataall " + createShopProductOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProduct.class));
        dao.execute(s);
        return s.getList(ShopProduct.class);
    }

    public List<ShopProduct> getShopProductListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createShopProductFilter
        String filter = createShopProductFilter(param);
        String sql =  "select * from (" + ShopProduct_QUERY_SQL + filter + ") dataall " + createShopProductOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProduct.class));
        dao.execute(s);
        return s.getList(ShopProduct.class);


    }

    public List<ShopProduct> getAllShopProductList(String orderBy){
	    String sql = ShopProduct_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createShopProductOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProduct.class));
        dao.execute(s);
        return s.getList(ShopProduct.class);
    }
    //==============end of ShopProduct==============
    


    //==============ShopProductTrade==============
    String ShopProductTrade_QUERY_SQL = "select * from ShopProductTrade t ";

    private String createShopProductTradeFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("productId") != null && !param.get("productId").equals("")) {
            filter += " and t.productId = @productId ";
        }
        if (param.get("tradeName") != null && !param.get("tradeName").equals("")) {
            filter += " and t.tradeName = @tradeName ";
        }
        if (param.get("tradeTime") != null && !param.get("tradeTime").equals("")) {
            filter += " and t.tradeTime = @tradeTime ";
        }
        if (param.get("tradeCount") != null && !param.get("tradeCount").equals("")) {
            filter += " and t.tradeCount = @tradeCount ";
        }
        if (param.get("tradePrice") != null && !param.get("tradePrice").equals("")) {
            filter += " and t.tradePrice = @tradePrice ";
        }
        if (param.get("saleCounts") != null && !param.get("saleCounts").equals("")) {
            filter += " and t.saleCounts = @saleCounts ";
        }
        if (param.get("orderCounts") != null && !param.get("orderCounts").equals("")) {
            filter += " and t.orderCounts = @orderCounts ";
        }
        if (param.get("rateCounts") != null && !param.get("rateCounts").equals("")) {
            filter += " and t.rateCounts = @rateCounts ";
        }
        if (param.get("wangwang") != null && !param.get("wangwang").equals("")) {
            filter += " and t.wangwang = @wangwang ";
        }
        if (param.get("wangwangRater") != null && !param.get("wangwangRater").equals("")) {
            filter += " and t.wangwangRater = @wangwangRater ";
        }

        return filter;
    }

    private String createShopProductTradeOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public ShopProductTrade insertShopProductTrade(ShopProductTrade vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateShopProductTrade(ShopProductTrade vo) {
        return dao.update(vo);
    }

    public int updateShopProductTradeIgnoreNull(ShopProductTrade vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteShopProductTrade(ShopProductTrade vo) {
        return dao.delete(vo);
    }

    public int deleteShopProductTradeByPrimaryKey(String id ) {
        return dao.delete(ShopProductTrade.class, id);
    }

    public int deleteShopProductTradeByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteShopProductTradeByParam(map);
    }

    public int deleteShopProductTradeByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createShopProductTradeFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from ShopProductTrade " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public ShopProductTrade getShopProductTradeByPrimaryKey(String id ) {
        String sql= ShopProductTrade_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductTrade.class));
        dao.execute(s);
        return s.getObject(ShopProductTrade.class);
    }

    public int getShopProductTradeCountByPrimaryKey(String id ) {
        String sql="select count(id) from ShopProductTrade t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getShopProductTradeCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductTradeCountByParam(map);
    }

    public int getShopProductTradeCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createShopProductTradeFilter
        String filter = createShopProductTradeFilter(param);
        String sql = "select count(id) from ShopProductTrade t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<ShopProductTrade> getShopProductTradeListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductTradeListByParam(orderBy, map);
    }

    public List<ShopProductTrade> getShopProductTradeListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductTradeListByParam(orderBy, map, page, count);
    }

    public List<ShopProductTrade> getShopProductTradeListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createShopProductTradeFilter
        String filter = createShopProductTradeFilter(param);
        String sql = "select * from (" + ShopProductTrade_QUERY_SQL + filter + ") dataall " + createShopProductTradeOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductTrade.class));
        dao.execute(s);
        return s.getList(ShopProductTrade.class);
    }

    public List<ShopProductTrade> getShopProductTradeListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createShopProductTradeFilter
        String filter = createShopProductTradeFilter(param);
        String sql =  "select * from (" + ShopProductTrade_QUERY_SQL + filter + ") dataall " + createShopProductTradeOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductTrade.class));
        dao.execute(s);
        return s.getList(ShopProductTrade.class);


    }

    public List<ShopProductTrade> getAllShopProductTradeList(String orderBy){
	    String sql = ShopProductTrade_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createShopProductTradeOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductTrade.class));
        dao.execute(s);
        return s.getList(ShopProductTrade.class);
    }
    //==============end of ShopProductTrade==============
    


    //==============ShopRate==============
    String ShopRate_QUERY_SQL = "select * from ShopRate t ";

    private String createShopRateFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("shopId") != null && !param.get("shopId").equals("")) {
            filter += " and t.shopId = @shopId ";
        }
        if (param.get("itemId") != null && !param.get("itemId").equals("")) {
            filter += " and t.itemId = @itemId ";
        }
        if (param.get("tradeName") != null && !param.get("tradeName").equals("")) {
            filter += " and t.tradeName = @tradeName ";
        }
        if (param.get("tradeCount") != null && !param.get("tradeCount").equals("")) {
            filter += " and t.tradeCount = @tradeCount ";
        }
        if (param.get("tradePrice") != null && !param.get("tradePrice").equals("")) {
            filter += " and t.tradePrice = @tradePrice ";
        }
        if (param.get("wangwang") != null && !param.get("wangwang").equals("")) {
            filter += " and t.wangwang = @wangwang ";
        }
        if (param.get("wangwangRater") != null && !param.get("wangwangRater").equals("")) {
            filter += " and t.wangwangRater = @wangwangRater ";
        }
        if (param.get("rateKind") != null && !param.get("rateKind").equals("")) {
            filter += " and t.rateKind = @rateKind ";
        }
        if (param.get("salerRate") != null && !param.get("salerRate").equals("")) {
            filter += " and t.salerRate = @salerRate ";
        }
        if (param.get("rateTime") != null && !param.get("rateTime").equals("")) {
            filter += " and t.rateTime = @rateTime ";
        }

        return filter;
    }

    private String createShopRateOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public ShopRate insertShopRate(ShopRate vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateShopRate(ShopRate vo) {
        return dao.update(vo);
    }

    public int updateShopRateIgnoreNull(ShopRate vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteShopRate(ShopRate vo) {
        return dao.delete(vo);
    }

    public int deleteShopRateByPrimaryKey(String id ) {
        return dao.delete(ShopRate.class, id);
    }

    public int deleteShopRateByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteShopRateByParam(map);
    }

    public int deleteShopRateByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createShopRateFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from ShopRate " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public ShopRate getShopRateByPrimaryKey(String id ) {
        String sql= ShopRate_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopRate.class));
        dao.execute(s);
        return s.getObject(ShopRate.class);
    }

    public int getShopRateCountByPrimaryKey(String id ) {
        String sql="select count(id) from ShopRate t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getShopRateCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopRateCountByParam(map);
    }

    public int getShopRateCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createShopRateFilter
        String filter = createShopRateFilter(param);
        String sql = "select count(id) from ShopRate t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<ShopRate> getShopRateListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopRateListByParam(orderBy, map);
    }

    public List<ShopRate> getShopRateListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopRateListByParam(orderBy, map, page, count);
    }

    public List<ShopRate> getShopRateListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createShopRateFilter
        String filter = createShopRateFilter(param);
        String sql = "select * from (" + ShopRate_QUERY_SQL + filter + ") dataall " + createShopRateOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopRate.class));
        dao.execute(s);
        return s.getList(ShopRate.class);
    }

    public List<ShopRate> getShopRateListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createShopRateFilter
        String filter = createShopRateFilter(param);
        String sql =  "select * from (" + ShopRate_QUERY_SQL + filter + ") dataall " + createShopRateOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopRate.class));
        dao.execute(s);
        return s.getList(ShopRate.class);


    }

    public List<ShopRate> getAllShopRateList(String orderBy){
	    String sql = ShopRate_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createShopRateOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopRate.class));
        dao.execute(s);
        return s.getList(ShopRate.class);
    }
    //==============end of ShopRate==============
    


    //==============ShopProductRate==============
    String ShopProductRate_QUERY_SQL = "select * from ShopProductRate t ";

    private String createShopProductRateFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("productId") != null && !param.get("productId").equals("")) {
            filter += " and t.productId = @productId ";
        }
        if (param.get("tradeName") != null && !param.get("tradeName").equals("")) {
            filter += " and t.tradeName = @tradeName ";
        }
        if (param.get("tradeCount") != null && !param.get("tradeCount").equals("")) {
            filter += " and t.tradeCount = @tradeCount ";
        }
        if (param.get("tradePrice") != null && !param.get("tradePrice").equals("")) {
            filter += " and t.tradePrice = @tradePrice ";
        }
        if (param.get("wangwang") != null && !param.get("wangwang").equals("")) {
            filter += " and t.wangwang = @wangwang ";
        }
        if (param.get("wangwangRater") != null && !param.get("wangwangRater").equals("")) {
            filter += " and t.wangwangRater = @wangwangRater ";
        }
        if (param.get("rateKind") != null && !param.get("rateKind").equals("")) {
            filter += " and t.rateKind = @rateKind ";
        }
        if (param.get("userRate") != null && !param.get("userRate").equals("")) {
            filter += " and t.userRate = @userRate ";
        }
        if (param.get("rateTime") != null && !param.get("rateTime").equals("")) {
            filter += " and t.rateTime = @rateTime ";
        }

        return filter;
    }

    private String createShopProductRateOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public ShopProductRate insertShopProductRate(ShopProductRate vo) {
        dao.insert(vo);
        return vo;
    }

    public int updateShopProductRate(ShopProductRate vo) {
        return dao.update(vo);
    }

    public int updateShopProductRateIgnoreNull(ShopProductRate vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deleteShopProductRate(ShopProductRate vo) {
        return dao.delete(vo);
    }

    public int deleteShopProductRateByPrimaryKey(String id ) {
        return dao.delete(ShopProductRate.class, id);
    }

    public int deleteShopProductRateByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deleteShopProductRateByParam(map);
    }

    public int deleteShopProductRateByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createShopProductRateFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from ShopProductRate " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public ShopProductRate getShopProductRateByPrimaryKey(String id ) {
        String sql= ShopProductRate_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductRate.class));
        dao.execute(s);
        return s.getObject(ShopProductRate.class);
    }

    public int getShopProductRateCountByPrimaryKey(String id ) {
        String sql="select count(id) from ShopProductRate t where t.id = @id   ";
        Sql s = Sqls.create(sql);
        //todo:
        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getShopProductRateCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductRateCountByParam(map);
    }

    public int getShopProductRateCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createShopProductRateFilter
        String filter = createShopProductRateFilter(param);
        String sql = "select count(id) from ShopProductRate t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<ShopProductRate> getShopProductRateListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductRateListByParam(orderBy, map);
    }

    public List<ShopProductRate> getShopProductRateListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getShopProductRateListByParam(orderBy, map, page, count);
    }

    public List<ShopProductRate> getShopProductRateListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createShopProductRateFilter
        String filter = createShopProductRateFilter(param);
        String sql = "select * from (" + ShopProductRate_QUERY_SQL + filter + ") dataall " + createShopProductRateOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductRate.class));
        dao.execute(s);
        return s.getList(ShopProductRate.class);
    }

    public List<ShopProductRate> getShopProductRateListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createShopProductRateFilter
        String filter = createShopProductRateFilter(param);
        String sql =  "select * from (" + ShopProductRate_QUERY_SQL + filter + ") dataall " + createShopProductRateOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductRate.class));
        dao.execute(s);
        return s.getList(ShopProductRate.class);


    }

    public List<ShopProductRate> getAllShopProductRateList(String orderBy){
	    String sql = ShopProductRate_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createShopProductRateOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(ShopProductRate.class));
        dao.execute(s);
        return s.getList(ShopProductRate.class);
    }
    //==============end of ShopProductRate==============
    


    //==============PublicLog==============
    String PublicLog_QUERY_SQL = "select * from PublicLog t ";

    private String createPublicLogFilter(Map<String, Object> param) {
        //to add filter,modify var filter
        String filter = " where 1=1 ";
        if (param.get("id") != null && !param.get("id").equals("")) {
            filter += " and t.id = @id ";
        }
        if (param.get("taskId") != null && !param.get("taskId").equals("")) {
            filter += " and t.taskId = @taskId ";
        }
        if (param.get("taskTime") != null && !param.get("taskTime").equals("")) {
            filter += " and t.taskTime = @taskTime ";
        }
        if (param.get("taskKey") != null && !param.get("taskKey").equals("")) {
            filter += " and t.taskKey = @taskKey ";
        }
        if (param.get("taskUrl") != null && !param.get("taskUrl").equals("")) {
            filter += " and t.taskUrl = @taskUrl ";
        }
        if (param.get("pLog") != null && !param.get("pLog").equals("")) {
            filter += " and t.pLog = @pLog ";
        }

        return filter;
    }

    private String createPublicLogOrderBy(String orderBy) {
        if (orderBy != null && !orderBy.equals(""))
            return " order by " + orderBy;
        else return "";
    }

    public PublicLog insertPublicLog(PublicLog vo) {
        dao.insert(vo);
        return vo;
    }

    public int updatePublicLog(PublicLog vo) {
        return dao.update(vo);
    }

    public int updatePublicLogIgnoreNull(PublicLog vo) {
        return dao.updateIgnoreNull(vo);
    }

    public int deletePublicLog(PublicLog vo) {
        return dao.delete(vo);
    }

    public int deletePublicLogByPrimaryKey(String id ) {
        return dao.delete(PublicLog.class, id);
    }

    public int deletePublicLogByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return deletePublicLogByParam(map);
    }

    public int deletePublicLogByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        String filter = createPublicLogFilter(param);
        //don't delete when no filter
        if (!filter.trim().toLowerCase().equals("where 1=1")) {
            String sql = "delete from PublicLog " + filter.replace("t.", "");
            Sql s = Sqls.create(sql);
            s.params().putAll(param);
            dao.execute(s);
            return s.getUpdateCount();
        }
        return 0;
    }

    public PublicLog getPublicLogByPrimaryKey(String id ) {
        String sql= PublicLog_QUERY_SQL + " where t.id = @id  ";
        Sql s = Sqls.create(sql);

        s.params().set("id", id);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(PublicLog.class));
        dao.execute(s);
        return s.getObject(PublicLog.class);
    }

    public int getPublicLogCountByPrimaryKey(String id ) {
        String sql="select count(id) from PublicLog t where t.id = @id   ";
        Sql s = Sqls.create(sql);

        s.params().set("id", id);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public int getPublicLogCountByParam(Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i<param.length; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getPublicLogCountByParam(map);
    }

    public int getPublicLogCountByParam(Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add fileter modify createPublicLogFilter
        String filter = createPublicLogFilter(param);
        String sql = "select count(id) from PublicLog t " + filter;
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.integer());
        dao.execute(s);
        return s.getInt();
    }

    public List<PublicLog> getPublicLogListByParam(String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getPublicLogListByParam(orderBy, map);
    }

    public List<PublicLog> getPublicLogListByParam(int page, int count, String orderBy, Object... param) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 0 ; i < param.length ; i += 2) {
            map.put(param[i].toString(), param[i + 1]);
        }

        return getPublicLogListByParam(orderBy, map, page, count);
    }

    public List<PublicLog> getPublicLogListByParam(String orderBy, Map<String, Object> param) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        //to add filter modify createPublicLogFilter
        String filter = createPublicLogFilter(param);
        String sql = "select * from (" + PublicLog_QUERY_SQL + filter + ") dataall " + createPublicLogOrderBy(orderBy);
        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(PublicLog.class));
        dao.execute(s);
        return s.getList(PublicLog.class);
    }

    public List<PublicLog> getPublicLogListByParam(String orderBy, Map<String, Object> param, int page, int count) {
        if (param == null) {
            param = new HashMap<String, Object>();
        }
        param.put("row0", (page - 1) * count);
        param.put("row1", count);

        //to add filter modify createPublicLogFilter
        String filter = createPublicLogFilter(param);
        String sql =  "select * from (" + PublicLog_QUERY_SQL + filter + ") dataall " + createPublicLogOrderBy(orderBy) + " limit @row0, @row1";

        Sql s = Sqls.create(sql);
        s.params().putAll(param);
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(PublicLog.class));
        dao.execute(s);
        return s.getList(PublicLog.class);


    }

    public List<PublicLog> getAllPublicLogList(String orderBy){
	    String sql = PublicLog_QUERY_SQL;
		Sql s = Sqls.create("select * from (" + sql + ") dataall " + createPublicLogOrderBy(orderBy));
        s.setCallback(Sqls.callback.entities());
        s.setEntity(dao.getEntity(PublicLog.class));
        dao.execute(s);
        return s.getList(PublicLog.class);
    }
    //==============end of PublicLog==============
    

}
