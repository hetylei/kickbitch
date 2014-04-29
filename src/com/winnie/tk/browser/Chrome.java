package com.winnie.tk.browser;

import com.winnie.pub.Config;
import com.winnie.pub.utils.PropertyUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-9-15
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class Chrome extends BaseBrowser {


    public Chrome(boolean incognito){
        ChromeOptions co = new ChromeOptions();

        if (incognito) {
            co.addArguments("--incognito");
            name = "chromeincognito";
        } else {

            name = "chrome";
        }

        co.addArguments("--user-data-dir=" + Config.pu.getValue("user-data-dir"));
        //co.addArguments("--new-window");
        //co.addArguments("--single-process");
        driver = new ChromeDriver(Config.getChromeDriverService(), co);

        //在chormedriver 2.4版本 问题已经解决
        driver.manage().timeouts().pageLoadTimeout(BaseBrowser.PAGELOADTIMEOUT, TimeUnit.SECONDS);
    }
    
    public Chrome(WebDriver dr) {
        driver = dr;
    }


}
