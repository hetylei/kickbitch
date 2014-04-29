package com.winnie.tk.browser;

import com.winnie.pub.Config;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 14-1-26
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
public class Firefox  extends BaseBrowser  {
    public Firefox(){
        //使用本机用户文件
        FirefoxProfile profile = new FirefoxProfile(new File(Config.pu.getValue("ffuserprofile")));
        //关闭图片加载
        //profile.setPreference("permissions.default.image", 2);
        //关闭flash加载
        //profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().pageLoadTimeout(BaseBrowser.PAGELOADTIMEOUT, TimeUnit.SECONDS);
        name = "firefox";
    }

    public Firefox(boolean incognito){
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(BaseBrowser.PAGELOADTIMEOUT, TimeUnit.SECONDS);
        name = "firefox";
    }
}
