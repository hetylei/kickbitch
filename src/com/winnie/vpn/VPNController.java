package com.winnie.vpn;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.task.vo.Task;
import com.winnie.win32.WindowsDll;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 13-4-18
 * Time: 下午11:27
 * To change this template use File | Settings | File Templates.
 */
public class VPNController {
    public static VPN vpn;
    public static Logger logger = Logger.getLogger(VPNController.class);
    public static int MAXRETRYTIMES = 20;
    static {
        initHandles();
    }

    
    public static void initHandles() {
        vpn = new VPN91();
        vpn.initVPN();
    }


    
    public static void excuteCommand(String command) {
        if (command!=null) {
            if (command.toLowerCase().equals("next")) {
                vpn.next();
            } else if (command.toLowerCase().equals("nextcity")) {
                vpn.nextcity();
            }  else if (command.toLowerCase().startsWith("nextmatch")) {
                vpn.nextmatch(command.substring(9).trim());
            }

        }
    }



    public static void vpn(String command){
        //连接到可用为止
        vpn.vpnClose();
        excuteCommand(command);

        int retryTime = 0;
        while (!vpn.isConnected()) {
            vpn.connectVPN();
            vpn.waitForConnect();

            if (!vpn.isConnected()) {
                excuteCommand(command);
                //超过最大重试链接次数 重启VPN
                retryTime++;
                if (retryTime > MAXRETRYTIMES) {
                    initHandles();
                    retryTime = 0;
                }
            }
        }
        // 切换成功后 执行默认任务(任务名为 vpnnext 的可用任务 isuse=1)
        List<Task> taskList = IocFactory.getTBKDao().getTaskListByParam("id", "isDelete", 0, "isUse", 1, "taskName", "vpnnext");
        if (taskList.size() >0) {
            logger.info("found [" + taskList.size() + "] tasks work after vpnnext ...");

            for (Task t :taskList) {
                BBSJob.taskRun(t, false);
            }
        }
    }



    public static void main(String[] args) {
        try {
            //if (!VPNController.isConnected()) VPNController.connectVPN();
            //waitForConnect();
            //if (VPNController.isConnected()) VPNController.disconnectVPN();
            System.out.println(vpn.getCurrentArea());
            //VPNController.nextcity();

            Thread.sleep(2000);
            vpn.first();
            System.out.println(vpn.getCurrentArea());
            Thread.sleep(2000);
            vpn.next();
            System.out.println(vpn.getCurrentArea());
            Thread.sleep(2000);
            vpn.pre();
            System.out.println(vpn.getCurrentArea());
            Thread.sleep(2000);
            vpn.last();
            System.out.println(vpn.getCurrentArea());

            System.out.println("all test finished..");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
