package com.winnie.tk.task.plugin;

import com.winnie.quartz.BBSJob;
import com.winnie.tk.browser.BaseBrowser;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: 下午1:32
 * click by cssSelector
 */
public class ClickByXPath implements Plugin {
    public static Logger logger = Logger.getLogger(ClickByXPath.class);
    @Override
    public void execute(Browser b, String[] params) {
        String currentWindow = b.getDriver().getWindowHandle();
        String paramsOneLine = "";
        for (String p : params) paramsOneLine += p + " ";

        try {
            WebElement e = b.getDriver().findElement(By.xpath(paramsOneLine));
            if (e!=null) {
                logger.info("clicked element by :" + paramsOneLine);
                //点击成功了，设置标记
                BBSJob.currentSuccessTimes++;
                BBSJob.log += paramsOneLine+"/";
                e.click();


                //切换到新窗口
                for (String w : b.getDriver().getWindowHandles()) {
                    if (currentWindow.equals(w)) {
                        continue;
                    }
                    b.getDriver().switchTo().window(w);
                }
                //等待页面加载完成
                WebDriverWait wait = new WebDriverWait(b.getDriver(),  BaseBrowser.PAGELOADTIMEOUT);
                wait.until(new ExpectedCondition<WebElement>(){
                    @Override
                    public WebElement apply(WebDriver d) {
                        try {
                            return d.findElement(By.tagName("body"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            return null;
                        }
                    }
                });

                //关闭新打开的窗口
                for (String w : b.getDriver().getWindowHandles()) {
                    if (currentWindow.equals(w)) {
                        continue;
                    }
                    b.getDriver().switchTo().window(w).close();
                }
                b.getDriver().switchTo().window(currentWindow);
            }
        } catch (NoSuchElementException e) {
            logger.debug("not find element by :" + paramsOneLine);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
