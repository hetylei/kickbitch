package com.winnie.tk.task.plugin;

import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.browser.BaseBrowser;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;
import com.winnie.tk.task.vo.Shop;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: 下午1:32
 * click by cssSelector
 */
public class GetShopRate implements Plugin {
    public static Logger logger = Logger.getLogger(GetShopRate.class);
    @Override
    public void execute(Browser b, String[] params) {

        String paramsOneLine = "";
        for (String p : params) paramsOneLine += p + " ";
        String[] src = paramsOneLine.split("\\,");
        for (String s : src) {
            List<Shop> shopList;
            if (s.trim().equals("all")) {
                shopList = IocFactory.getTBKDao().getAllShopList("");
            } else {
                shopList = IocFactory.getTBKDao().getShopListByParam("", "id", s);
            }
            for (Shop shop : shopList) {
                saveShopRate(b, shop);        
            }
        }

    }
    
    private void saveShopRate(Browser b, Shop shop) {
        //用淘宝账号登录
        //b.open("https://login.taobao.com/member/login.jhtml");
        //try{
        //    b.clickByXPath("//a[contains(text(),'使用其他账户登录')]");
        //} catch (Exception ex) {
            //可能没有
        //}

        //b.put("TPL_username_1","张五常2008");
        //b.put("TPL_password","hi51wangbao");
        //b.submit("J_SubmitStatic");
        //打开
        b.getDriver().get(shop.getUserRateUrl());
        //码破解验证是否有验证码
        crackCode(b);
        //最近半年的交易记录
        b.clickByXPath("//li[contains(text(),'最近半年')]");
        //点击给他人的评价
        b.clickByXPath("//li[contains(text(),'给别人的评价')]");

        //取消勾选有评论内容
        b.click("rate-content-filter-2");
        //码破解验证是否有验证码
        crackCode(b);



        //保存数据
        
        //翻页
        //码破解验证是否有验证码
        crackCode(b);
        try {
            b.waitForSecond(30);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void crackCode(Browser b) {

    }

    /**
     *
     * @param b
     * @param shop
     * @return 是否本页数据都已经存在
     */
    private boolean saveRateData(Browser b , Shop shop) {
        //保存当前页面数据
        WebElement w = b.getDriver().findElement(By.id("reviews-list"));

        return false;
    }
}
