package com.winnie.tk.task.plugin;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
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

import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: ����1:32
 * click by cssSelector
 */
public class ClickBy implements Plugin {
    public static Logger logger = Logger.getLogger(ClickBy.class);
    @Override
    public void execute(Browser b, String[] params) {
        
        String paramsOneLine = "";
        for (String p : params) paramsOneLine += p + " ";
        String[] src = paramsOneLine.split("\\,");
        for (String s : src)  {
            run(b, s);
        }
    }    
    
    public boolean run(Browser b, String css) {
        String currentWindow = b.getDriver().getWindowHandle();
        try {
            WebElement e = b.getDriver().findElement(By.cssSelector(css));
            if (e!=null) {
                logger.info("clicked by :" + css);
                //����ɹ��ˣ����ñ��
                BBSJob.currentSuccessTimes++;
                BBSJob.log += css+"/";

                e.click();


                //�л����´���
                for (String w : b.getDriver().getWindowHandles()) {
                    if (currentWindow.equals(w)) {
                        continue;
                    }
                    b.getDriver().switchTo().window(w);
                }
                //�ȴ�ҳ��������
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

                //�ر��´򿪵Ĵ���
                for (String w : b.getDriver().getWindowHandles()) {
                    if (currentWindow.equals(w)) {
                        continue;
                    }
                    b.getDriver().switchTo().window(w).close();
                }
                b.getDriver().switchTo().window(currentWindow);
                return true;

            }
        } catch (NoSuchElementException e) {
            logger.debug("not find element by :" + css);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
