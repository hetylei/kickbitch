package com.winnie;

import com.winnie.pub.Config;

import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;
import org.nutz.mvc.ioc.provider.JsonIocProvider;
import org.nutz.mvc.view.FreemarkerViewMaker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-11
 * Time: ÉÏÎç12:08
 * To change this template use File | Settings | File Templates.
 */
@Modules(scanPackage = true)
@IocBy(type = JsonIocProvider.class, args = {"dao.js"})
@SetupBy(Config.class)
@Views({FreemarkerViewMaker.class})
@Fail("fm:/error.ftl")
public class MainModule {


    @At("/login")
    @Filters
    @Ok("fm:/member/login.ftl")
    public void login(HttpSession session) {

    }


    @At("/logout")
    @Ok("redirect:/")
    @Filters
    public void doLogout(HttpSession session) {
        session.invalidate();
    }


}
