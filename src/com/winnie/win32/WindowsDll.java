package com.winnie.win32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;

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

    public static void main(String[] args) {
        WinDef.HWND hwnd = CallUser32.INSTANCE.FindWindow(null , "91VPN网游加速器商业版 - 3.5.2");
        if (hwnd != null) {
            CallUser32.INSTANCE.EnumChildWindows(hwnd, new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(WinDef.HWND hwnd, Pointer pointer) {
                    char[] lpClassName = new char[255];
                    char[] lpWindowText = new char[255];
                    CallUser32.INSTANCE.GetClassName(hwnd, lpClassName, 254);
                    CallUser32.INSTANCE.GetWindowText(hwnd, lpWindowText, 254);
                    System.out.println(new String(lpClassName).trim() + " - " + new String(lpWindowText).trim());
                    return true;
                }
            }, null);
        }

    }
}
