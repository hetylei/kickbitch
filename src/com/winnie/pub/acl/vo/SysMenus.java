package com.winnie.pub.acl.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-12-20
 * Time: ÉÏÎç8:25
 * To change this template use File | Settings | File Templates.
 */
public class SysMenus {
    private List<SysMenuGroup> menus;

    public List<SysMenuGroup> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenuGroup> menus) {
        this.menus = menus;
    }

    public void addMenus(List<SysMenuGroup> addMenus) {
        menus.addAll(addMenus);
    }
}
