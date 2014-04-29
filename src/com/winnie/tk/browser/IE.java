package com.winnie.tk.browser;

import com.winnie.pub.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.concurrent.TimeUnit;


/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 12-11-24
 * Time: ÏÂÎç9:29
 * To change this template use File | Settings | File Templates.
 */
public class IE extends BaseBrowser {
    public IE(){
        driver = new InternetExplorerDriver(Config.getIEDriverService());
        driver.manage().timeouts().pageLoadTimeout(BaseBrowser.PAGELOADTIMEOUT, TimeUnit.SECONDS);
        name = "ie";
    }

    public IE(WebDriver dr) {
        driver = dr;
    }
}
