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
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: 下午1:32
 * click by cssSelector
 */
public class Com28 implements Plugin {
    public static Logger logger = Logger.getLogger(Com28.class);

    /**
     *
     * @param b
     * @param params 共5个参数 姓名 手机号 地址 留言 排序
     */
    @Override
    public void execute(Browser b, String[] params) {
        String currentWindow = b.getDriver().getWindowHandle();
        String xingming = params[0];
        String shouji = params[1];
        String dizhi = params[2];
        String liuyan = params[3];
        String sort = "asc"; //asc正序 desc倒序 random随机
        if (params.length>4) sort = params[4];

        //获取所有符合规则的链接
        String s = b.open("http://www.28.com");
        if (s != null) {
            List<WebElement> elementList= b.getDriver().findElements(By.xpath("//a[contains(@href,'bannerID=')]"));
           
            if (sort.equals("desc")) {
                Collections.reverse(elementList);
            } else if (sort.equals("random")) {
                Collections.shuffle(elementList);
            }

            //开始点击链接
            for (WebElement e : elementList)  {
                try {
                    if (e!=null) {
                        e.click();
                        logger.info("clicked by :" + e.getText());

                        String banner = "";
                        //切换到新窗口
                        for (String w : b.getDriver().getWindowHandles()) {
                            if (currentWindow.equals(w)) {
                                continue;
                            }
                            banner = w;
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

                        try {
                            //切换到提交资料的iframe
                            b.getDriver().switchTo().frame("gbook");
                            //提交资料

                            b.put("text_trueName", xingming);
                            b.put("text_telephone", shouji);
                            b.put("text_address", dizhi);
                            b.put("text_content", liuyan);
                            b.click("bt_submit");

                            String bannerSubmit = "";
                            //切换到新窗口
                            for (String w : b.getDriver().getWindowHandles()) {
                                if (currentWindow.equals(w) || banner.equals(w)) {
                                    continue;
                                }
                                bannerSubmit = w;
                                b.getDriver().switchTo().window(w);
                            }

                            logger.info("已经切换到提交结果窗口");
                            //等待页面加载完成
                            wait = new WebDriverWait(b.getDriver(),  BaseBrowser.PAGELOADTIMEOUT);
                            wait.until(new ExpectedCondition<WebElement>(){
                                @Override
                                public WebElement apply(WebDriver d) {
                                    try {
                                        return d.findElement(By.tagName("body"));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        //如果已经提交过会弹出重复提交提示
                                        try{
                                            d.switchTo().alert().accept();
                                            return new HtmlUnitWebElement(null,null);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                }
                            });
                            logger.info("提交结果窗口已经加载完毕");


                            //关闭所有新打开的窗口
                            try{
                                b.getDriver().switchTo().window(bannerSubmit).close();
                            } catch (Exception ee) {
                                //可能已经关闭掉了
                                ee.printStackTrace();
                            }
                        } catch (Exception e3) {
                            //可能没有提交留恋的界面
                            e3.printStackTrace();

                        }
                        b.getDriver().switchTo().window(banner).close();

                        b.getDriver().switchTo().window(currentWindow);

                    }
                } catch (NoSuchElementException ex) {
                    logger.debug("not find element by :" + s);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
}
