package com.winnie.tk.task.plugin;

import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;
import com.winnie.tk.task.dao.TBKDao;
import com.winnie.tk.task.vo.P4PKeys;
import com.winnie.tk.task.vo.TaskLog;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: ����1:32
 * To change this template use File | Settings | File Templates.
 */
public class P4PClickLog implements Plugin {
    public static Logger logger = Logger.getLogger(P4PClickLog.class);
    public static List<TaskLog> logList;
    @Override
    public void execute(Browser b, String[] params) {
        if (params.length == 1 && params[0].startsWith("start")) {
            //��ʼ��¼log ��ʼ��
            logList = new ArrayList<TaskLog> ();
        }  else if (params.length == 1 && params[0].startsWith("end")) {
            //������¼log д�����ݿ���ջ���
            if (logList != null) {
                TBKDao dao = IocFactory.getTBKDao();
                for (TaskLog l : logList) {
                    dao.insertTaskLog(l);
                }
                logList.clear();
            }
        }
    }

    public static void log(TaskLog l) {
        if (logList != null) logList.add(l);
    }

}
