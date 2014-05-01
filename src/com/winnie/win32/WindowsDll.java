package com.winnie.win32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;
import com.winnie.pub.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 13-4-17
 * Time: 下午1:05
 * To change this template use File | Settings | File Templates.
 */
public class WindowsDll {
    public static int CB_SETCURSEL = 0x014E;
    public static int CB_GETCOUNT = 0x0146;
    public static int BM_CLICK = 0x00F5;
    public static int CB_GETCURSEL = 0x0147;
    public static int CB_GETLBTEXT = 0x0148;

    public interface CallUser32  extends User32 {
        CallUser32 INSTANCE = (CallUser32)
                Native.loadLibrary("user32", CallUser32.class, W32APIOptions.DEFAULT_OPTIONS);

        LRESULT SendMessageA(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);
        HWND FindWindow(java.lang.String winClass, java.lang.String title);
        boolean EnumChildWindows(HWND hWnd, User32.WNDENUMPROC lpEnumFunc, com.sun.jna.Pointer data);
        int GetClassName(HWND hWnd, char[] lpClassName, int nMaxCount);
        int GetWindowText(HWND hWnd, char[] lpString, int nMaxCount);
        WinDef.HWND GetWindow(HWND hWnd, DWORD dword);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }
    }

    public interface CallUser  extends User32 {
        CallUser INSTANCE = (CallUser)
                Native.loadLibrary("user32", CallUser.class, W32APIOptions.DEFAULT_OPTIONS);

        //最后的Lparam可以定义任何参数，自动转换内存地址
        LRESULT SendMessageA(HWND hWnd, int Msg, WPARAM wParam, byte[]c);
        
    }


    /**
     * 检查windows程序是否正在运行
     * @param exe 程度可执行文件名 xxx.exe
     * @return
     */
    public static int processIsRunByExe(String exe) {
        String cmd = "tasklist /nh /FI \"IMAGENAME eq " + exe + "\"";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while((line=br.readLine()) != null){
                if(line.toLowerCase().contains(exe.toLowerCase())){
                    String[] lineArray = line.split(" ");
                    //结果从左开始第一个数字就是PID
                    for (String s:lineArray) {
                        if (!s.equals("") && s.matches("\\d*")) {
                            return Integer.parseInt(s);
                        }
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static void killProcessByExe(String exe) {
        try {
            Runtime.getRuntime().exec("taskkill /IM "+exe);

            //等待杀死进程
            int i = 0;
            while (processIsRunByExe(exe) > -1) {
                try {
                    Thread.sleep(100);
                    i++; 
                    if (i>100) {
                        i = 0;
                        killProcessByExe(exe);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public static void main(String[] args) {
        WindowsDll.killProcessByExe("notepad.exe");

    }

}
