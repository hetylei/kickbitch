package com.winnie.vpn;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;
import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.task.vo.Task;
import com.winnie.win32.WindowsDll;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 14-3-19
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
public class VPN91 implements VPN {
    //主程序句柄
    public static WinDef.HWND main;
    //登录按钮句柄
    public static WinDef.HWND login;
    //链接按钮句柄
    public static WinDef.HWND connect;
    //断开按钮句柄
    public static WinDef.HWND disconnect;
    //地区列表句柄
    public static WinDef.HWND area;
    //状态提示句柄
    public static WinDef.HWND state;

    public static Logger logger = Logger.getLogger(VPNController.class);


    public void initVPN() {
        connect = null;
        int i = 0;

        logger.info("代理 - " + Config.pu.getValue("91vpn") + " 正在初始化...");
        //确保只有一个实例运行，杀死现有进程
        kill();

        //启动程序
        try {
            Runtime.getRuntime().exec("cmd.exe /c \""+Config.pu.getValue("91vpnexe")+"\"");
            logger.info("代理 - " + Config.pu.getValue("91vpn") + " 启动完毕，准备登录...");
            //等待启动成功
            main = WindowsDll.CallUser32.INSTANCE.FindWindow(null , Config.pu.getValue("91vpn"));
            while (main == null) {
                try {
                    Thread.sleep(1000);
                    i++;
                    if (i>20) {
                        initVPN();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                main = WindowsDll.CallUser32.INSTANCE.FindWindow(null , Config.pu.getValue("91vpn"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //登录
        main = WindowsDll.CallUser32.INSTANCE.FindWindow(null , Config.pu.getValue("91vpn"));
        if (main != null) {
            login = null;
            WindowsDll.CallUser32.INSTANCE.EnumChildWindows(main, new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                    char[] lpClassName = new char[255];
                    char[] lpWindowText = new char[255];
                    WindowsDll.CallUser32.INSTANCE.GetClassName(hwnd, lpClassName, 254);
                    WindowsDll.CallUser32.INSTANCE.GetWindowText(hwnd, lpWindowText, 254);
                    if (new String(lpWindowText).trim().equals("登录") && new String(lpClassName).trim().equals("Button") ) {
                        if (login == null ) {
                            //这里有个BUG 登录窗口有两个叫登录的BUTTON，第一个是真的
                            login  = hwnd;
                            logger.info("找到【登录】按钮 - " + Config.pu.getValue("91vpn"));
                        }

                    }
                    return true;
                }
            }, null);
            if (login != null) {
                WindowsDll.CallUser32.INSTANCE.SendMessageA(login, WindowsDll.BM_CLICK, null, null);
                logger.info("点击【登录】按钮 - " + Config.pu.getValue("91vpn"));
            } else {
                logger.info("未能找到【登录】按钮 - " + Config.pu.getValue("91vpn"));
            }
        } else {
            logger.info("无法找到代理运行 - " + Config.pu.getValue("91vpn"));
        }

        i = 0;
        while (connect ==null) {
            //登录以后
            main = WindowsDll.CallUser32.INSTANCE.FindWindow(null , Config.pu.getValue("91vpn"));
            if (main != null) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                WindowsDll.CallUser32.INSTANCE.EnumChildWindows(main, new WinUser.WNDENUMPROC() {
                    @Override
                    public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                        char[] lpClassName = new char[255];
                        char[] lpWindowText = new char[255];
                        WindowsDll.CallUser32.INSTANCE.GetClassName(hwnd, lpClassName, 254);
                        WindowsDll.CallUser32.INSTANCE.GetWindowText(hwnd, lpWindowText, 254);
                        if (new String(lpWindowText).trim().equals("连接")) {
                            connect  = hwnd;
                        } else if (new String(lpWindowText).trim().equals("断开")) {
                            disconnect  = hwnd;
                        } else if (new String(lpWindowText).trim().equals("局部代理")) {
                            state  = WindowsDll.CallUser32.INSTANCE.GetWindow(hwnd, new WinDef.DWORD(User32.GW_HWNDNEXT));
                        } else  if (new String(lpClassName).trim().equals("ComboBox")) {
                            int itemCount = WindowsDll.CallUser32.INSTANCE.SendMessageA(hwnd, 0x0146, null, null).intValue();
                            if (itemCount>30)  area = hwnd;
                        }
                        return true;
                    }
                }, null);
            }
            try {
                Thread.sleep(3000);
                //防止登录出错卡住
                i++;
                if (i>20) {
                    initVPN();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        logger.info("代理 - " + Config.pu.getValue("91vpn") + " 登录成功...");
        logger.info("------捕获句柄------");
        logger.info("connect:" + connect);
        logger.info("disconnect:" + disconnect);
        logger.info("state:" + state);
        logger.info("area:" + area);
        logger.info("-------------------");

    }

    public void connectVPN() {
        WindowsDll.CallUser32.INSTANCE.SendMessageA(connect, WindowsDll.BM_CLICK, null, null);
        logger.info("connecting " + getCurrentArea() + "...");
    }

    public void disconnectVPN() {
        WindowsDll.CallUser32.INSTANCE.SendMessageA(disconnect, WindowsDll.BM_CLICK, null, null);
        logger.info("disconnecting " + getCurrentArea() + "...");
    }

    public boolean isConnected () {
        char[] lpWindowText = new char[255];
        WindowsDll.CallUser32.INSTANCE.GetWindowText(state, lpWindowText, 254);
        return new String(lpWindowText).toLowerCase().startsWith("connected");
    }

    public void waitForConnect () {
        int waitTimes = 0;
        while (!isConnected()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            waitTimes++;
            if (waitTimes > 15) {
                logger.info("cann't connect " + getCurrentArea() + "...");
                return;
            }
        }
        //关闭钻石升级对话框  如果有
        closeUpdateDialog();
        logger.info("connected " + getCurrentArea() + "...");
    }

    public void waitForDisconnect () {
        int waitTimes = 0;
        while (isConnected()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            waitTimes++;
            if (waitTimes > 30) {
                logger.info("cann't disconnect " + getCurrentArea() + "...");
                return;
            }
        }
        logger.info("disconnected " + getCurrentArea() + "...");
    }

    public void first() {
        WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_SETCURSEL, new WinDef.WPARAM(0), null);
    }

    public void last() {
        int itemCount = WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_GETCOUNT, null, null).intValue();
        WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_SETCURSEL, new WinDef.WPARAM(itemCount -1), null);
    }

    public void pre() {
        int cursel = WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_GETCURSEL, null, null).intValue();
        if (cursel > 0)
            WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_SETCURSEL, new WinDef.WPARAM(cursel - 1), null);
    }

    public void next() {
        int cursel = WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_GETCURSEL, null, null).intValue();
        int itemCount = WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_GETCOUNT, null, null).intValue();
        //如果是最后一个就从头开始
        if (cursel < itemCount -1 )
            WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_SETCURSEL, new WinDef.WPARAM(cursel + 1), null);
        else
            WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_SETCURSEL, new WinDef.WPARAM(0), null);
    }

    public void nextcity() {
        String currentcity = getCurrentArea().substring(0,2);
        while (getCurrentArea().substring(0,2).equals(currentcity))
            next();
    }

    public void nextmatch(String m) {
        next();
        String[] citys = m.split("\\,") ;
        for (String city : citys) {
            if (getCurrentArea().contains(city)) return;
        }
        nextmatch(m);
    }

    public String getCurrentArea() {
        byte[] lpWindowText = new byte[255];
        WindowsDll.CallUser.INSTANCE.SendMessageA(area, WindowsDll.CB_GETLBTEXT,
                new WinDef.WPARAM(WindowsDll.CallUser32.INSTANCE.SendMessageA(area, WindowsDll.CB_GETCURSEL, null, null).intValue()),
                lpWindowText);

        return new String(lpWindowText).trim();
    }



    public void closeUpdateDialog() {
        WinDef.HWND dia = WindowsDll.CallUser32.INSTANCE.FindWindow(null , "升级");
        if (dia != null) {
            WindowsDll.CallUser32.INSTANCE.EnumChildWindows(dia, new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                    char[] lpWindowText = new char[255];
                    WindowsDll.CallUser32.INSTANCE.GetWindowText(hwnd, lpWindowText, 254);
                    if (new String(lpWindowText).trim().equals("关闭")) {
                        WindowsDll.CallUser32.INSTANCE.SendMessageA(hwnd, WindowsDll.BM_CLICK, null, null);
                        logger.info(getCurrentArea() + " 为钻石服务器。");
                    }
                    return true;
                }
            }, null);
        }
    }

    public void vpnClose(){
        disconnectVPN();
        waitForDisconnect();
    }

    public void kill() {
        WindowsDll.killProcessByExe("91vpn.exe");
        logger.info("代理 - " + Config.pu.getValue("91vpn") + " 已经杀死进程");
    }





}
