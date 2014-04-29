package com.winnie.vpn;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.task.vo.Task;
import com.winnie.win32.WindowsDll;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 14-3-19
 * Time: ÏÂÎç11:48
 * To change this template use File | Settings | File Templates.
 */
public interface VPN {

    public void initVPN() ;
    public void connectVPN();
    public void disconnectVPN() ;

    public boolean isConnected ();

    public void waitForConnect () ;

    public void waitForDisconnect ();

    public void first() ;

    public void last() ;

    public void pre();

    public void next() ;

    public void nextcity() ;

    public void nextmatch(String m) ;

    public String getCurrentArea() ;


    public void vpnClose();

    public void kill();

}
