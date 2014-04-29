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
 * Time: ����1:32
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
        //���Ա��˺ŵ�¼
        //b.open("https://login.taobao.com/member/login.jhtml");
        //try{
        //    b.clickByXPath("//a[contains(text(),'ʹ�������˻���¼')]");
        //} catch (Exception ex) {
            //����û��
        //}

        //b.put("TPL_username_1","���峣2008");
        //b.put("TPL_password","hi51wangbao");
        //b.submit("J_SubmitStatic");
        //��
        b.getDriver().get(shop.getUserRateUrl());
        //���ƽ���֤�Ƿ�����֤��
        crackCode(b);
        //�������Ľ��׼�¼
        b.clickByXPath("//li[contains(text(),'�������')]");
        //��������˵�����
        b.clickByXPath("//li[contains(text(),'�����˵�����')]");

        //ȡ����ѡ����������
        b.click("rate-content-filter-2");
        //���ƽ���֤�Ƿ�����֤��
        crackCode(b);



        //��������
        
        //��ҳ
        //���ƽ���֤�Ƿ�����֤��
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
     * @return �Ƿ�ҳ���ݶ��Ѿ�����
     */
    private boolean saveRateData(Browser b , Shop shop) {
        //���浱ǰҳ������
        WebElement w = b.getDriver().findElement(By.id("reviews-list"));

        return false;
    }
}
