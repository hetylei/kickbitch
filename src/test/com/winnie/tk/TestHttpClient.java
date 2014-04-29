package test.com.winnie.tk;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.winnie.pub.Config;
import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.utils.Dates;
import com.winnie.tk.browser.Chrome;
import com.winnie.tk.browser.Executer;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutType;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-8-14
 * Time: ÏÂÎç4:08
 * To change this template use File | Settings | File Templates.
 */
public class TestHttpClient {

    public static void testExecuter(Chrome chrome) throws Exception {

    }


    public static void testPlugIn(Chrome chrome) throws Exception {
        Executer.runLine(chrome, "plugin com.winnie.tk.task.plugin.PluginTester ¹þ¹þ ºÙºÙ 123");
    }

    public static void testPlugInSearchUpKey(Chrome chrome) throws Exception {
        Executer.runLine(chrome, "plugin com.winnie.tk.task.plugin.SearchUpKey http://top.taobao.com/level3.php?spm=0.0.0.245.738eec&cat=TR_BBYP&level3=50018813&show=focus&up=true&offset=0 Ö½Äò¿ã");
    }

    public static void testPlugInHotKey(Chrome chrome) throws Exception {
        Executer.runLine(chrome, "plugin com.winnie.tk.task.plugin.HotKey http://top.taobao.com/level3.php?cat=TR_BBYP&level3=50018813&show=focus&offset=0&up=false Ö½Äò¿ã");
    }

    public static void main(String[] args) throws IOException {
        ChromeDriverService chromeDriverService = null;
        try {


            chromeDriverService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File("E:\\chenlei\\workspace\\taobaoke\\chromedriver.exe"))
                    //.withEnvironment()
                    .usingAnyFreePort()
                    .build();
            try {
                chromeDriverService.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--user-data-dir=C:\\Users\\chenlei\\AppData\\Local\\Google\\Chrome\\User Data");
            //WebDriver driver = new ChromeDriver(chromeDriverService, co);
            //Chrome chrome = new Chrome(driver);

            //chrome.open("http://www.samhwacompany.com/cn/index.html");
            //System.out.println(driver.getPageSource());
            //testGetHotKeys(driver, chrome);
            //testPlugInSearchUpKey(chrome);
            //testPlugInHotKey(chrome);

            //chrome.close();
            WebDriver driver1 = new HtmlUnitDriver();
            driver1.get("http://www.samhwacompany.com/cn/index.html");

            System.out.println(driver1.getPageSource());
        } catch (Exception e) {
            System.err.println( "Exception: " + e );
        } finally {
            chromeDriverService.stop();
        }

    }

    private static void testGetHotKeys(WebDriver driver, Chrome chrome) throws Exception {
        System.out.println("-----------------Ö½Äò¿ãËÑË÷ÉÏÉý-------------");

        Executer.runLine(chrome, "open http://top.taobao.com/level3.php?spm=0.0.0.245.738eec&cat=TR_BBYP&level3=50018813&show=focus&up=true&offset=0");
        //System.out.println(driver.getPageSource());
        for (int i=1;i<=162;i++) {
            WebElement e = driver.findElement(By.cssSelector("a[href*='searchup.title."+i+"&']"));
            System.out.println(e.getText());
            if (i % 30 == 0) {
                e = driver.findElement(By.cssSelector("a[class*='page-next']"));
                e.click();
                chrome.waitForText("id=\"J_" + (i+1) + "\"");
            }
        }

        System.out.println("-----------------Ö½Äò¿ãËÑË÷ÈÈÃÅ-------------");

        Executer.runLine(chrome, "open http://top.taobao.com/level3.php?cat=TR_BBYP&level3=50018813&show=focus&offset=0&up=false");
        //System.out.println(driver.getPageSource());
        for (int i=1;i<=162;i++) {
            WebElement e = driver.findElement(By.cssSelector("a[href*='searchhot.title."+i+"&']"));
            System.out.println(e.getText());
            if (i % 30 == 0) {
                e = driver.findElement(By.cssSelector("a[class*='page-next']"));
                e.click();
                chrome.waitForText("id=\"J_" + (i+1) + "\"");
            }
        }


    }
}
