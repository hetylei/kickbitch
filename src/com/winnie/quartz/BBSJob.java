package com.winnie.quartz;


import com.winnie.pub.IocFactory;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.BrowserFactory;
import com.winnie.tk.browser.Executer;
import com.winnie.tk.task.dao.TBKDao;
import com.winnie.tk.task.vo.Task;
import com.winnie.tk.task.vo.TaskLog;
import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-3-4
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
public class BBSJob implements Job {
    public static Logger logger = Logger.getLogger(BBSJob.class);
    public static boolean jobrun = false;
    public static int currentSuccessTimes = 0;
    public static String log = "";
    public static boolean holdbrowser = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (jobrun) {
            return;
        }
        jobrun = true;
        //logger.info("BBSJob Start...");
        try{
            TBKDao tkdao = IocFactory.getTBKDao();
            Calendar c = Calendar.getInstance();
            String taskTime = new SimpleDateFormat("HH:mm").format(c.getTime());
            
            List<Task> taskList = tkdao.getTaskListByParam("id", "isDelete", 0, "isUse", 1, "taskTime", taskTime);
            if (taskList.size() >0) {
                logger.info("found [" + taskList.size() + "] tasks work on " + taskTime + "...");
                
                for (Task t :taskList) {
                    taskRun(t, true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jobrun = false;
    }
    
    public static void taskRun(Task t, boolean resetCount) {
        if (resetCount) {
            currentSuccessTimes = 0;
            log = "";
        }
        try{
            try {
                Executer.run(t.getTaskCommand().split("\r\n"));

                String taskFullTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                t.setLastState("[" +taskFullTime + "][ok]");

                IocFactory.getTBKDao().updateTaskIgnoreNull(t);

            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);

                String taskFullTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
                t.setLastState("[" +taskFullTime + "][error]" + sw.toString());
                IocFactory.getTBKDao().updateTaskIgnoreNull(t);

                System.out.println(sw.toString());
            }
            if (currentSuccessTimes > 0 && resetCount) {
                TaskLog l = new TaskLog();
                l.setTaskId(t.getId());
                l.setTaskLog("有效执行次数:" + currentSuccessTimes + "|" + log);
                l.setTaskTime(Calendar.getInstance().getTime());
                IocFactory.getTBKDao().insertTaskLog(l);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (resetCount) currentSuccessTimes = 0;//清0一下 防止递归调用 计数
    }
    
    public static void doOne(int id) {
        /*
        if (jobrun) {

            return;
        }
        */
        //jobrun = true;

        logger.info("BBSJob Start...");
        try{
            logger.info("run tasks work id :"+id);
            List<Task> taskList = IocFactory.getTBKDao().getTaskListByParam("id", "id", id);
            if (taskList.size() >0) {
                for (Task t :taskList) {
                    taskRun(t, true);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //jobrun = false;
    }
    
    public static void doAllNow() {
        if (jobrun) {
            return;
        }
        jobrun = true;
        logger.info("BBSJob Start...");
        try{
            logger.info("finding all tasks work");
            List<Task> taskList = IocFactory.getTBKDao().getTaskListByParam("id", "isDelete", 0, "isUse", 1);
            if (taskList.size() >0) {
                for (Task t :taskList) {
                    taskRun(t, true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jobrun = false;
    }
}