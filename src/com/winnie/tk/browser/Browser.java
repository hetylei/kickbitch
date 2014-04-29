package com.winnie.tk.browser;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-9-14
 * Time: 上午8:17
 * To change this template use File | Settings | File Templates.
 */
public interface Browser {
    public String name();

    /**
     * 打开一个网址
     * @param url url
     * @return  body xml
     */
    public String open(String url);

    /**
     * 点击一个链接 或 其它东西
     * @param idName  先用id查找名字，再用name查找名字
     * @return body xml
     */
    public String click(String idName);

    /**
     * 根据css样式查找并点击
     * @param css  查找的css
     * @return body xml
     */
    public String clickByCSS(String css);

    /**
     * 根据xpath路径查找并点击
     * @param xpath  xpath路径
     * @return body xml
     */
    public String clickByXPath(String xpath);

    /**
     * 点击一个按钮
     * @param idName  先用id查找名字，再用name查找名字
     * @return body xml
     */
    public String submit(String idName);

    /**
     * 设置一个input 或者其它东西的值
     * @param idName  先用id查找名字，再用name查找名字
     * @param value   要设置的值
     * @return  成功否
     */
    public boolean put(String idName, String value);

    /**
     * 检测页面中是否等于值，不包含就一直等待
     * @param idName
     * @param value
     * @throws InterruptedException
     */
    public void waitForValue(String idName, String value) ;

    /**
     * 检测页面中是否包含关键词，不包含就一直等待
     * @param key 关键词
     */
    public void waitForText(String key) throws InterruptedException;

    /**
     * 等待N秒
     * @param second
     * @throws InterruptedException
     */
    public void waitForSecond(int second) throws InterruptedException;


    /**
     * 调用插件
     * @param pluginClass 类名
     * @param params 参数列表
     * @throws InterruptedException
     */
    public void plugin(String pluginClass, String[] params) throws InterruptedException;

    public WebDriver getDriver();


    /**
     * 关闭浏览器
     */
    public void close();
}
