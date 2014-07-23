package com.winnie.tk.browser;

import com.winnie.pub.Config;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 12-11-24
 * Time: 下午9:35
 * To change this template use File | Settings | File Templates.
 */
public class BaseBrowser implements Browser {
    private Logger loggger = Logger.getLogger(BaseBrowser.class.getName());
    public static int PAGELOADTIMEOUT = Integer.parseInt(Config.pu.getValue("pageloadtimeout"));
    protected WebDriver driver;

    protected String name;
    
    private WebElement findElement(String idName) {
        WebElement w = null;
        try
        {
            loggger.debug("findElement by id : " + idName);
            //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            w = driver.findElement(By.id(idName));
            loggger.debug("foundElement by id : " + idName);
        } catch (Exception ex) {
            //找不到会有异常抛出
            loggger.debug("not foundElement by id : " + idName);
            loggger.debug("findElement by name : " + idName);
            //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            w = driver.findElement(By.name(idName));
            loggger.debug("foundElement by name : " + idName);
        }    
        return w;
    }

    private WebElement findElementByCss(String css) {
        WebElement w = null;
        try
        {
            loggger.debug("findElement by css : " + css);
            //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            w = driver.findElement(By.cssSelector(css));
            loggger.debug("foundElement by css : " + css);
        } catch (Exception ex) {
            return null;
        }
        return w;
    }

    private WebElement findElementByXPath(String xpath) {
        WebElement w = null;
        try
        {
            loggger.debug("findElement by xpath : " + xpath);
            //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            w = driver.findElement(By.xpath(xpath));
            loggger.debug("foundElement by xpath : " + xpath);
        } catch (Exception ex) {
            return null;
        }
        return w;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String open(String url) {
        loggger.debug("open " + url);
        try {
            //driver.manage().timeouts().implicitlyWait(BaseBrowser.PAGELOADTIMEOUT, TimeUnit.SECONDS);
            driver.get(url);
            loggger.debug("opened " + url);
            return driver.getPageSource();

        } catch (TimeoutException te) {
            loggger.debug("open failed " + url);
            return null;
        } catch (SessionNotFoundException se) {
            //处理IE的异常
            loggger.debug("SessionNotFoundException restart driver... ");
            Executer.browser = BrowserFactory.get(name);
            return Executer.browser.open(url);
        }
    }

    @Override
    public String click(String idName) {
        WebElement w = findElement(idName);
        
        if (w != null) {
            try
            {
                loggger.debug("click : " + idName);
                w.click();
                loggger.debug("clicked : " + idName);
                
                return driver.getPageSource();
            } catch (TimeoutException te) {
                //打开超时
                loggger.debug("TimeoutException ... ");    
                te.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String clickByCSS(String css) {
        WebElement w = findElementByCss(css);

        if (w != null) {
            try
            {
                String currentWindow = driver.getWindowHandle();
                loggger.debug("click by css : " + css);
                w.click();
                loggger.debug("clicked by css : " + css);

                //切换到新窗口
                for (String h : driver.getWindowHandles()) {
                    if (currentWindow.equals(h)) {
                        continue;
                    }
                    driver.switchTo().window(h);
                }
                return driver.getPageSource();
            } catch (TimeoutException te) {
                //打开超时
                loggger.debug("TimeoutException ... ");
                te.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String clickByXPath(String xpath) {
        WebElement w = findElementByXPath(xpath);

        if (w != null) {
            try
            {
                String currentWindow = driver.getWindowHandle();
                loggger.debug("click by xpath : " + xpath);
                w.click();
                loggger.debug("clicked by xpath : " + xpath);
                //切换到新窗口
                for (String h : driver.getWindowHandles()) {
                    if (currentWindow.equals(h)) {
                        continue;
                    }
                    driver.switchTo().window(h);
                }

                return driver.getPageSource();
            } catch (TimeoutException te) {
                //打开超时
                loggger.debug("TimeoutException ... ");
                te.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String submit(String idName) {
        WebElement w = findElement(idName);

        if (w != null) {
            try{
                loggger.debug("click : " + idName);
                w.submit();
                loggger.debug("clicked : " + idName);

                return driver.getPageSource();
            } catch (TimeoutException te) {
                //打开超时
                loggger.debug("TimeoutException ... ");
                te.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean put(String idName, String value) {
        WebElement w = findElement(idName);

        if (w != null) {
            loggger.debug("sendKeys " + idName + " : " + value);
            w.clear();
            w.sendKeys(value);
            loggger.debug("sentKeys "  + idName + " : " + value);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void waitForValue(final String idName, final String value) {
        loggger.debug("waitForValue " + idName + " : " + value);
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                WebElement e = d.findElement(By.id(idName));

                if ((value == null || value.equals("") || value.equals("null")) && (e.getAttribute("value") == null || e.getAttribute("value").equals("") ))  {
                    return e;
                } else if (e.getAttribute("value").equals(value)){
                    return e;
                }
                return null;
            }
        });
        loggger.debug("end waitForValue " + idName + " : " + value);
    }

    @Override
    public void waitForText(String key) throws InterruptedException {
        loggger.debug("waitForText : " + key);
        while (!driver.getPageSource().toLowerCase().contains(key.toLowerCase())) {
            WebDriverWait wait = new WebDriverWait(driver,1);
            wait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver d) {
                    return d.findElement(By.id("ooxx123456789"));
                }
            });
        }
    }

    @Override
    public void waitForSecond(int second) throws InterruptedException {
        loggger.debug("waitForSecond : " + second);
        Thread.sleep(second*1000);
    }

    @Override
    public void plugin(String pluginClass, String[] params) throws InterruptedException {
        Class c = null;
        try {
            c = Class.forName(pluginClass);
            Plugin p = (Plugin) c.newInstance();

            Method execute = c.getMethod("execute", Browser.class, String[].class);

            Object[] args = new Object[2];
            args[0] = this;
            args[1] = params;
            execute.invoke(p, args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public WebDriver getDriver() {
        return this.driver;
    }


    @Override
    public void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }

    }

}
