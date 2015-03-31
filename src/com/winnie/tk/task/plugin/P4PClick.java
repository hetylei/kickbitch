package com.winnie.tk.task.plugin;

import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;
import com.winnie.tk.task.dao.TBKDao;
import com.winnie.tk.task.vo.P4PKeys;
import com.winnie.tk.task.vo.TaskLog;
import com.winnie.vpn.VPNController;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Calendar;
import java.util.List;

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
        }  else if (params.length == 1 && params[0].startsWith("catalog")) {
            TBKDao dao = IocFactory.getTBKDao();
            List<P4PKeys> list = dao.getP4PKeysListByParam("", "catalog", params[0].substring(7));
            run(b, list);
        }  else {
            run(b, params);
        }
    }

    private boolean run(Browser b, String[] params) {
        ClickBy clickBy = new ClickBy();
        boolean bl = false;
        for (String id : params) {
            bl = bl || clickBy.run(b, (Config.pu.getValue("p4pleft")+" img[src*='" + id + "']"));
            bl = bl || clickBy.run(b, (Config.pu.getValue("p4pbottom")+" img[src*='" + id + "']"));
            bl = bl || clickBy.run(b, (Config.pu.getValue("p4pshop")+" img[src*='" + id + "']"));
        }
        return bl;
    }

    private void run(Browser b, List<P4PKeys> list) {
        for (P4PKeys p4p : list) {
            boolean bl = run(b, p4p.getP4pids().split(" "));
            if (bl) {
                TaskLog l = new TaskLog();
                l.setTaskTime(Calendar.getInstance().getTime());
                l.setTaskLog(VPNController.vpn.getCurrentArea() + b.getDriver().getTitle());
                l.setP4pid(String.valueOf(p4p.getId()));
                P4PClickLog.log(l);
            }
        }
    }
}
