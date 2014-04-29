package com.winnie.tk.browser;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-9-14
 * Time: ����8:17
 * To change this template use File | Settings | File Templates.
 */
public interface Browser {
    public String name();

    /**
     * ��һ����ַ
     * @param url url
     * @return  body xml
     */
    public String open(String url);

    /**
     * ���һ������ �� ��������
     * @param idName  ����id�������֣�����name��������
     * @return body xml
     */
    public String click(String idName);

    /**
     * ����css��ʽ���Ҳ����
     * @param css  ���ҵ�css
     * @return body xml
     */
    public String clickByCSS(String css);

    /**
     * ����xpath·�����Ҳ����
     * @param xpath  xpath·��
     * @return body xml
     */
    public String clickByXPath(String xpath);

    /**
     * ���һ����ť
     * @param idName  ����id�������֣�����name��������
     * @return body xml
     */
    public String submit(String idName);

    /**
     * ����һ��input ��������������ֵ
     * @param idName  ����id�������֣�����name��������
     * @param value   Ҫ���õ�ֵ
     * @return  �ɹ���
     */
    public boolean put(String idName, String value);

    /**
     * ���ҳ�����Ƿ����ֵ����������һֱ�ȴ�
     * @param idName
     * @param value
     * @throws InterruptedException
     */
    public void waitForValue(String idName, String value) ;

    /**
     * ���ҳ�����Ƿ�����ؼ��ʣ���������һֱ�ȴ�
     * @param key �ؼ���
     */
    public void waitForText(String key) throws InterruptedException;

    /**
     * �ȴ�N��
     * @param second
     * @throws InterruptedException
     */
    public void waitForSecond(int second) throws InterruptedException;


    /**
     * ���ò��
     * @param pluginClass ����
     * @param params �����б�
     * @throws InterruptedException
     */
    public void plugin(String pluginClass, String[] params) throws InterruptedException;

    public WebDriver getDriver();


    /**
     * �ر������
     */
    public void close();
}
