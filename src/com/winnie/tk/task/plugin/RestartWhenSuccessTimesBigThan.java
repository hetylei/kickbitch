package com.winnie.tk.task.plugin;

import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Executer;
import com.winnie.tk.browser.Plugin;
import com.winnie.tk.task.vo.TaskLog;
import com.winnie.vpn.VPNController;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: ����1:32
 * To change this template use File | Settings | File Templates.
 */
public class RestartWhenSuccessTimesBigThan implements Plugin {
    public static Logger logger = Logger.getLogger(RestartWhenSuccessTimesBigThan.class);

    /**
     *
     * @param b
     * @param params
     * ����1 ����N��
     * ����2 ִ������ID
     */
    @Override
    public void execute(Browser b, String[] params) {
        int successTimesBigThan = Integer.parseInt(params[0]);
        int taskId = Integer.parseInt(params[1]);
        if (BBSJob.currentSuccessTimes > successTimesBigThan) {
            logger.info("�ϴγɹ�ִ�д��� ��" + BBSJob.currentSuccessTimes);
            logger.info("����ִ������ ��" + taskId);

            TaskLog l = new TaskLog();
            l.setTaskId(taskId);
            l.setTaskLog("��Чִ�д���:" + BBSJob.currentSuccessTimes + "|" + BBSJob.log);
            l.setTaskTime(Calendar.getInstance().getTime());
            IocFactory.getTBKDao().insertTaskLog(l);


            Executer.browser.close();
            Executer.browser = null;
            BBSJob.doOne(taskId);
        } else {

            logger.info("�ϴγɹ�ִ�д��� ��" + BBSJob.currentSuccessTimes + " ���������");
        }
    }
}
