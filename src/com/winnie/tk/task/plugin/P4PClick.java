package com.winnie.tk.task.plugin;

import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;
import com.winnie.tk.task.dao.TBKDao;
import com.winnie.tk.task.vo.P4PKeys;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: ÏÂÎç1:32
 * To change this template use File | Settings | File Templates.
 */
public class P4PClick implements Plugin {
    public static Logger logger = Logger.getLogger(P4PClick.class);
    @Override
    public void execute(Browser b, String[] params) {
        if (params.length == 1 && params[0].startsWith("id")) {
            TBKDao dao = IocFactory.getTBKDao();
            P4PKeys p = dao.getP4PKeysByPrimaryKey(params[0].substring(2));
            run(b, p.getP4pids().split(" "));
        }  else {
            run(b, params);
        }
    }

    private void run(Browser b, String[] params) {
        ClickBy clickBy = new ClickBy();
        for (String id : params) {
            clickBy.execute(b, (Config.pu.getValue("p4pleft")+" img[src*='" + id + "']").split("bitch"));
            clickBy.execute(b, (Config.pu.getValue("p4pbottom")+" img[src*='" + id + "']").split("bitch"));
            clickBy.execute(b, (Config.pu.getValue("p4pshop")+" img[src*='" + id + "']").split("bitch"));
        }
    }
}
