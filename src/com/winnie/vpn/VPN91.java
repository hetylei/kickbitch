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
 * Time: ����11:52
 * To change this template use File | Settings | File Templates.
 */
public class VPN91 implements VPN {
    //��������
    public static WinDef.HWND main;
    //��¼��ť���
    public static WinDef.HWND login;
    //���Ӱ�ť���
    public static WinDef.HWND connect;
    //�Ͽ���ť���
    public static WinDef.HWND disconnect;
    //�����б���
    public static WinDef.HWND area;
    //״̬��ʾ���
    public static WinDef.HWND state;

    public static Logger logger = Logger.getLogger(VPNController.class);


    public void initVPN() {
        connect = null;
        int i = 0;

        logger.info("���� - " + Config.pu.getValue("91vpn") + " ���ڳ�ʼ��...");
        //ȷ��ֻ��һ��ʵ�����У�ɱ�����н���
        kill();

        //��������
        try {
            Runtime.getRuntime().exec("cmd.exe /c \""+Config.pu.getValue("91vpnexe")+"\"");
            logger.info("���� - " + Config.pu.getValue("91vpn") + " ������ϣ�׼����¼...");
            //�ȴ������ɹ�
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

        //��¼
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
                    if (new String(lpWindowText).trim().equals("��¼") && new String(lpClassName).trim().equals("Button") ) {
                        if (login == null ) {
                            //�����и�BUG ��¼�����������е�¼��BUTTON����һ�������
                            login  = hwnd;
                            logger.info("�ҵ�����¼����ť - " + Config.pu.getValue("91vpn"));
                        }

                    }
                    return true;
                }
            }, null);
            if (login != null) {
                WindowsDll.CallUser32.INSTANCE.SendMessageA(login, WindowsDll.BM_CLICK, null, null);
                logger.info("�������¼����ť - " + Config.pu.getValue("91vpn"));
            } else {
                logger.info("δ���ҵ�����¼����ť - " + Config.pu.getValue("91vpn"));
            }
        } else {
            logger.info("�޷��ҵ��������� - " + Config.pu.getValue("91vpn"));
        }

        i = 0;
        while (connect ==null) {
            //��¼�Ժ�
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
                        if (new String(lpWindowText).trim().equals("����")) {
                            connect  = hwnd;
                        } else if (new String(lpWindowText).trim().equals("�Ͽ�")) {
                            disconnect  = hwnd;
                        } else if (new String(lpWindowText).trim().equals("�ֲ�����")) {
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
                //��ֹ��¼����ס
                i++;
                if (i>20) {
                    initVPN();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        logger.info("���� - " + Config.pu.getValue("91vpn") + " ��¼�ɹ�...");
        logger.info("------������------");
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
        //�ر���ʯ�����Ի���  �����
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
        //��������һ���ʹ�ͷ��ʼ
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
        WinDef.HWND dia = WindowsDll.CallUser32.INSTANCE.FindWindow(null , "����");
        if (dia != null) {
            WindowsDll.CallUser32.INSTANCE.EnumChildWindows(dia, new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                    char[] lpWindowText = new char[255];
                    WindowsDll.CallUser32.INSTANCE.GetWindowText(hwnd, lpWindowText, 254);
                    if (new String(lpWindowText).trim().equals("�ر�")) {
                        WindowsDll.CallUser32.INSTANCE.SendMessageA(hwnd, WindowsDll.BM_CLICK, null, null);
                        logger.info(getCurrentArea() + " Ϊ��ʯ��������");
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
        logger.info("���� - " + Config.pu.getValue("91vpn") + " �Ѿ�ɱ������");
    }





}
