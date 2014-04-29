package com.winnie.pub;

import com.winnie.pub.acl.utils.MenuManager;
import com.winnie.pub.acl.vo.SysMenu;
import com.winnie.pub.acl.vo.SysMenus;
import com.winnie.pub.utils.SessionHelper;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-18
 * Time: 下午10:33
 * To change this template use File | Settings | File Templates.
 */
public class NavSession implements ActionFilter {

    public NavSession() {

    }

    public View match(ActionContext actionContext) {
        //通过url解析   /22/
        String url = actionContext.getRequest().getRequestURI();
        if (url.length() > 1 && url.indexOf("/",1) > 0) {
            actionContext.getRequest().getSession().setAttribute("nav", url.substring(0, url.indexOf("/", 1)));
        } else {
            actionContext.getRequest().getSession().setAttribute("nav", url);
        }
        actionContext.getRequest().getSession().setAttribute("menu", url);


        if (actionContext.getRequest().getSession().getAttribute("menus") == null) {
            //根据权限显示用户菜单
            if (actionContext.getRequest().getSession().getAttribute("admin") != null)
                actionContext.getRequest().getSession().setAttribute("menus", MenuManager.adminMenus);
            else
                actionContext.getRequest().getSession().setAttribute("menus", MenuManager.userMenus);

        }

        return null;
    }
}
