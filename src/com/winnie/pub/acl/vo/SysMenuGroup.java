package com.winnie.pub.acl.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-12-19
 * Time: ÉÏÎç11:28
 * To change this template use File | Settings | File Templates.
 */
public class SysMenuGroup {
    private String id;
    private String name;
    private String link;
    private List<SysMenu> menus;
    private List<SysMenuGroup> groups;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    public List<SysMenuGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<SysMenuGroup> groups) {
        this.groups = groups;
    }
}
