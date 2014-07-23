package com.winnie.tk;

import com.winnie.pub.Config;
import com.winnie.pub.IocFactory;
import com.winnie.quartz.BBSJob;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.JsonIocProvider;
import org.nutz.mvc.view.FreemarkerViewMaker;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-11
 * Time: ÉÏÎç12:08
 * To change this template use File | Settings | File Templates.
 */

public class TKModule {


    @At("/")
    @Ok("fm:/tk/main.ftl")
    public Object index() {
        return IocFactory.getTBKDao().getAllTaskList("isuse desc");
    }

    @At("/tk/startall")
    @Ok("fm:/tk/main.ftl")
    public Object startAll() {
        BBSJob.doAllNow();
        return index();
    }

    @At("/tk/start/?")
    @Ok("fm:/tk/main.ftl")
    public Object start(int id) {
        BBSJob.jobrun = true;
        BBSJob.doOne(id);
        BBSJob.jobrun = false;
        return index();
    }

    @At("/tk/start/?/?")
    @Ok("fm:/tk/main.ftl")
    public Object start(int id, int count) {
        BBSJob.jobrun = true;
        for (int i=0;i<count;i++) BBSJob.doOne(id);
        BBSJob.jobrun = false;
        return index();
    }




}
