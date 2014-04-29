package com.winnie.tk.task.plugin;

import com.winnie.quartz.BBSJob;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: ����1:32
 * To change this template use File | Settings | File Templates.
 */
public class IframeBodySet implements Plugin {
    public static Logger logger = Logger.getLogger(IframeBodySet.class);

    /**
     * ���ı��ؼ���ֵ�������
     * @param b
     * @param params
     * ����1 iframe id
     * ����2 ����body��ֵ(body�����������ֵ ���� contenteditable="true"����
     */
    @Override
    public void execute(Browser b, String[] params) {

        String currentWindow = b.getDriver().getWindowHandle();
        String index = params[0];
        String v = "";
        for (int i=1;i<params.length;i++) v += params[i] + " ";
        if (b.name().equals("firefox")) {
            try {
                b.getDriver().switchTo().frame(new Integer(index));
                logger.debug("set to iframe index "+ new Integer(index));
            } catch (Exception ex) {
                b.getDriver().switchTo().frame(index);
                logger.debug("set to iframe:"+ index);
            }
            b.getDriver().findElement(By.tagName("body")).sendKeys(v);
            logger.debug("put body :" + v);
            b.getDriver().switchTo().window(currentWindow);
        } else {
            //��js ʵ��
            String js ="var element=window.document.getElementById('"+index+"'); " +
                    "idocument=element.contentDocument;" +
                    "idocument.body.innerHTML = '" + v +"';";
            ((JavascriptExecutor)b.getDriver()).executeScript(js);
        }
    }
}
