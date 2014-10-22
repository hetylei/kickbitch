package com.winnie.tk.browser;

import com.winnie.quartz.BBSJob;
import com.winnie.vpn.VPNController;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-9-16
 * Time: 下午12:17
 * 命令集
 *
 */
public class Executer {
    public static Logger logger = Logger.getLogger(Executer.class);

    public static Browser browser = null;
    public static void run(List<String> commands) throws Exception {
        try
        {
            for (String command : commands)
                runLine(browser, command);
            if (browser != null) browser.close();
        } catch (Exception e) {
            if (browser != null) browser.close();
            throw e;
        }
    }

    public static void run(String[] commands) throws Exception {
        try
        {
            for (String command : commands)
                runLine(browser, command);
            if (browser != null) browser.close();
        } catch (Exception e) {
            if (browser != null) browser.close();
            throw e;
        }
    }
    
    public static void runLine(Browser browser, String command) throws Exception {
        if (command.startsWith("#")) {
            //注释 跳过
        } else if (command.startsWith("use ")) {
            String b = command.substring(4);
            Executer.browser = BrowserFactory.get(b);
        } else if (command.startsWith("open ")) {
            //打开前还没有初始化 使用默认浏览器
            if (browser == null || browser.getDriver() == null) {
                Executer.browser = BrowserFactory.current();
            }

            Executer.browser.open(command.substring(5));
        }  else if (command.startsWith("opennew ")) {
            if (Executer.browser != null) Executer.browser.close();
            Executer.browser = BrowserFactory.current();

            Executer.browser.open(command.substring(8));
        } else if (command.startsWith("click ")) {
            browser.click(command.substring(6));
        } else if (command.startsWith("clickbycss ")) {
            browser.clickByCSS(command.substring(11));
        } else if (command.startsWith("clickbyxpath ")) {
            browser.clickByXPath(command.substring(13));
        } else if (command.startsWith("submit ")) {
            browser.submit(command.substring(7));
        } else if (command.startsWith("put ")) {
            browser.put(command.substring(4, command.indexOf(" ", 4)), command.substring(command.indexOf(" ", 4) + 1));
        } else if (command.startsWith("waitfortext ")) {
            browser.waitForText(command.substring(12));
        } else if (command.startsWith("waitforvalue ")) {
            browser.waitForValue(command.substring(13, command.indexOf(" ", 13)), command.substring(command.indexOf(" ", 13) + 1));
        } else if (command.startsWith("waitforsecond ")) {
            browser.waitForSecond(Integer.parseInt(command.substring(14)));
        } else if (command.startsWith("execnoex ")) {
            try
            {
                runLine(browser,  command.substring(9));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (command.startsWith("plugin ")) {
            String plugin = command.substring(7, command.indexOf(" ", 7));
            String[] params = command.substring(7+1+plugin.length()).split(" ");
            
            browser.plugin(plugin, params);
        } else if (command.startsWith("vpnclose")) {
            VPNController.vpn.vpnClose();
        } else if (command.startsWith("vpn ")) {
            VPNController.vpn(command.substring(4));
        } else if (command.startsWith("close")) {
            if (Executer.browser != null) Executer.browser.close();
        } else {
            throw new Exception("unknown command : " + command);
        }
    }
}
