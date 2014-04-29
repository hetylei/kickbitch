package com.winnie;

import com.winnie.pub.NavSession;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-17
 * Time: ÏÂÎç11:17
 * To change this template use File | Settings | File Templates.
 */


@Filters({@By(type = NavSession.class)})
public class MemberModule {
    @At("/member/")
    @Ok("fm:/member/index.ftl")
    public void doIndex() {

    }

    @At("/member/deny")
    @Ok("fm:/member/deny.ftl")
    public void doDeny() {

    }

    @At("/member/login")
    @Ok("fm:/member/login.ftl")
    //@Fail("fm:/member/login.ftl")
    public void doLogin(@Param("uId") String uId, @Param("uPass") String uPass, HttpSession session) throws Exception {

    }


    @At("/member/logout")
    @Ok("redirect:/")
    @Filters
    public void doLogout(HttpSession session) {
        session.removeAttribute("_$user");
    }

    @At("/member/nav")
    @Ok("json")
    public Object doNav(HttpSession session) {
        Map<String, String> nav = new LinkedHashMap<String, String>();



        return nav;
    }

}
