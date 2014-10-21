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
 * Time: ����1:32
 * click by cssSelector
 */
public class Com28 implements Plugin {
    public static Logger logger = Logger.getLogger(Com28.class);

    /**
     *
     * @param b
     * @param params ��5������ ���� �ֻ��� ��ַ ���� ����
     */
    @Override
    public void execute(Browser b, String[] params) {
        String currentWindow = b.getDriver().getWindowHandle();
        String xingming = params[0];
        String shouji = params[1];
        String dizhi = params[2];
        String liuyan = params[3];
        String sort = "asc"; //asc���� desc���� random���
        if (params.length>4) sort = params[4];

        //��ȡ���з��Ϲ��������
        String s = b.open("http://www.28.com");
        if (s != null) {
            List<WebElement> elementList= b.getDriver().findElements(By.xpath("//a[contains(@href,'bannerID=')]"));
           
            if (sort.equals("desc")) {
                Collections.reverse(elementList);
            } else if (sort.equals("random")) {
                Collections.shuffle(elementList);
            }

            //��ʼ�������
            for (WebElement e : elementList)  {
                try {
                    if (e!=null) {
                        e.click();
                        logger.info("clicked by :" + e.getText());

                        String banner = "";
                        //�л����´���
                        for (String w : b.getDriver().getWindowHandles()) {
                            if (currentWindow.equals(w)) {
                                continue;
                            }
                            banner = w;
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

                        try {
                            //�л����ύ���ϵ�iframe
                            b.getDriver().switchTo().frame("gbook");
                            //�ύ����

                            b.put("text_trueName", xingming);
                            b.put("text_telephone", shouji);
                            b.put("text_address", dizhi);
                            b.put("text_content", liuyan);
                            b.click("bt_submit");

                            String bannerSubmit = "";
                            //�л����´���
                            for (String w : b.getDriver().getWindowHandles()) {
                                if (currentWindow.equals(w) || banner.equals(w)) {
                                    continue;
                                }
                                bannerSubmit = w;
                                b.getDriver().switchTo().window(w);
                            }

                            logger.info("�Ѿ��л����ύ�������");
                            //�ȴ�ҳ��������
                            wait = new WebDriverWait(b.getDriver(),  BaseBrowser.PAGELOADTIMEOUT);
                            wait.until(new ExpectedCondition<WebElement>(){
                                @Override
                                public WebElement apply(WebDriver d) {
                                    try {
                                        return d.findElement(By.tagName("body"));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        //����Ѿ��ύ���ᵯ���ظ��ύ��ʾ
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
                            logger.info("�ύ��������Ѿ��������");


                            //�ر������´򿪵Ĵ���
                            try{
                                b.getDriver().switchTo().window(bannerSubmit).close();
                            } catch (Exception ee) {
                                //�����Ѿ��رյ���
                                ee.printStackTrace();
                            }
                        } catch (Exception e3) {
                            //����û���ύ�����Ľ���
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
