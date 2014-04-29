package com.winnie.tk.task.plugin;

import com.winnie.tk.browser.Browser;
import com.winnie.tk.browser.Plugin;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-10-1
 * Time: обнГ1:32
 * To change this template use File | Settings | File Templates.
 */
public class PluginTester implements Plugin {
    @Override
    public void execute(Browser b, String[] params) {
        for (String s : params) {
            System.out.println(s);
        }
    }
}
