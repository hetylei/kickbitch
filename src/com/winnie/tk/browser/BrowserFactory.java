package com.winnie.tk.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-9-15
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
public class BrowserFactory {

    /**
     * firefox
     * firefoxincognito 隐身模式 无cookie
     * ie
     * chrome
     * chromeincognito 隐身模式 无cookie
     */
    private static String current = "firefox"; //默认浏览器，会被最后一次调用更改
    public static Browser chrome() {
        return new Chrome(false);
    }

    public static Browser chromeIncognito() {
        return new Chrome(true);
    }

    public static Browser ie() {
        return new IE();
    }

    public static Browser firefox() {
        return new Firefox();
    }

    public static Browser firefoxIncognito() {
        return new Firefox(true);
    }

    public static Browser current() {
        return get(current);
    }
    
    public static Browser get(String name) {
        current = name;
        if (name.toLowerCase().equals("ie")) {
            return ie();
        } else if (name.toLowerCase().equals("chrome"))  {
            return chrome();
        } else if (name.toLowerCase().equals("chromeincognito"))  {
            return chromeIncognito();
        } else if (name.toLowerCase().equals("firefox"))  {
            return firefox();
        } else if (name.toLowerCase().equals("firefoxincognito"))  {
            return firefoxIncognito();
        } else {
            current = "firefox";
            return firefox();
        }
    }
}
