package com.winnie.tk.task.plugin;

import com.winnie.pub.IocFactory;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Executer;
import com.winnie.tk.browser.Plugin;
import com.winnie.tk.task.vo.HotKeys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: ÏÂÎç2:08
 * To change this template use File | Settings | File Templates.
 */
public class SearchUpKey implements Plugin {

    /**
     *
     */
    @Override
    public void execute(Browser b, String[] params) {
        Calendar c = Calendar.getInstance();
        b.open(params[0]);

        for (int i=1;i<=162;i++) {
            WebElement e = b.getDriver().findElement(By.cssSelector("a[href*='searchup.title." + i + "&']"));

            HotKeys hk = new HotKeys();
            hk.setKeyKind(HotKeys.UP);
            hk.setKeyWord(e.getText());
            hk.setUrl(params[0]);
            hk.setCatalog(params[1]);
            hk.setSortno(i);
            hk.setDownloadDate(c.getTime());
            IocFactory.getTBKDao().insertHotKeys(hk);

            if (i % 30 == 0) {
                e = b.getDriver().findElement(By.cssSelector("a[class*='page-next']"));
                e.click();

                try {
                    b.waitForText("id=\"J_" + (i+1) + "\"");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
