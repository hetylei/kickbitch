package com.winnie.vpn;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.winnie.pub.Config;
import com.winnie.win32.WindowsDll;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 14-3-19
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
public class VPNLongMa implements VPN {
    //主程序句柄
    public static WinDef.HWND main;
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
        main = WindowsDll.CallUser32.INSTANCE.FindWindow(null , Config.pu.getValue("longma"));

        if (main != null) {
            WindowsDll.CallUser32.INSTANCE.EnumChildWindows(main, new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                    char[] lpClassName = new char[255];
                    char[] lpWindowText = new char[255];
                    WindowsDll.CallUser32.INSTANCE.GetClassName(hwnd, lpClassName, 254);
                    WindowsDll.CallUser32.INSTANCE.GetWindowText(hwnd, lpWindowText, 254);
                    if (new String(lpWindowText).trim().equals("连  接")) {
                        connect  = hwnd;
                    } else if (new String(lpWindowText).trim().equals("断  开")) {
                        disconnect  = hwnd;
                    } else  if (new String(lpWindowText).trim().equals("批量测速")) {
                        area = WindowsDll.CallUser32.INSTANCE.GetWindow(hwnd, new WinDef.DWORD(User32.GW_HWNDNEXT));;
                    }
                    return true;
                }
            }, null);
        } else {
            logger.info("无法找到代理运行 - " + Config.pu.getValue("longma"));
        }
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

    }

    public void vpnClose(){
        disconnectVPN();
        waitForDisconnect();
    }

    public void kill() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
