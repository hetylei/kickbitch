package com.winnie.pub.acl.utils;

import com.winnie.pub.acl.vo.SysMenus;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-12-19
 * Time: ÉÏÎç11:35
 * To change this template use File | Settings | File Templates.
 */
public class MenuManager {
    public final static SysMenus userMenus;
    public final static SysMenus adminMenus;
    static {
        Ioc ioc = new NutIoc(new JsonLoader("userMenu.js"));
        userMenus = ioc.get(SysMenus.class, "UserMenus");

        Ioc ioc2 = new NutIoc(new JsonLoader("adminMenu.js"));
        adminMenus = ioc2.get(SysMenus.class, "AdminMenus");
        adminMenus.getMenus().addAll(0, userMenus.getMenus());
    }

}
