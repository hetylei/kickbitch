package com.winnie.test;

import com.winnie.pub.IocFactory;
import com.winnie.pub.utils.KeyCreator;
import com.winnie.pub.utils.Pager;
import com.winnie.test.dao.ComputerDao;
import com.winnie.test.vo.Computer;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-11
 * Time: 上午1:39
 * To change this template use File | Settings | File Templates.
 */
public class ComputerShop {
    @At("/tutorial")
    @Ok("fm:/index.ftl")
    public void doIndex() {
        //如果不hold住 /tutorial 会有404错误，虽然mainModule已经hold /? 了 应该是nutz bug啊
    }

    /**
     * http://192.168.232.225:8080/tutorial/computerShop?pager.page=9&pager.linesPerPage=10&cpu=&&orderBy=
     * @param pager
     * @param orderBy
     * @param cpu
     * @return
     */

    @At("/tutorial/computerShop")
    @Ok("fm:/com/winnie/test/computerlist.ftl")
    public Object doList(@Param("::pager.") Pager pager, @Param("orderBy") String orderBy, 
                         @Param("cpu") String cpu) {
        Map<String, Object> obj = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cpu", cpu);

        ComputerDao dao = IocFactory.getComputerDao();
        //缓存查询条件和排序
        pager.setQuery(Pager.getQueryStringFromMap(params));   //格式 name=value&name2=value2
        pager.setOrderBy(orderBy); //格式 orderfiled asc/desc,orderfiled2 asc/desc
        pager.setRecorderCount(dao.getComputerCountByParam(params));
        obj.put("computerList", dao.getComputerListByParam(orderBy, params, pager.getPage(), pager.getLinesPerPage()));
        obj.put("pager", pager);
        obj.put("params", params);
        return obj;
    }

    @At("/tutorial/computerShop/new")
    @Ok("fm:/com/winnie/test/computer.ftl")
    public Object doNew() {
        return new Computer();
    }

    /**
     * http://192.168.232.225:8080/tutorial/computerShop/edit/40286861345f6f8201345f6fa4c60051
     * @param sn
     * @return
     */
    @At("/tutorial/computerShop/edit/?")
    @Ok("fm:/com/winnie/test/computer.ftl")
    public Object doModify(String sn) {
        return IocFactory.getComputerDao().getComputerByPrimaryKey(sn);
    }


    @At("/tutorial/computerShop/save")
    @Ok("redirect:/tutorial/computerShop")
    public void doSave(@Param("..") Computer c) {
        //偷懒的方法，这个不要学啊
        if (c.getId() == 0 ) {
            IocFactory.getComputerDao().insertComputer(c);
        } else {
            IocFactory.getComputerDao().updateComputerIgnoreNull(c);
        }
    }

    @At("/tutorial/computerShop/del/?")
    @Ok("redirect:/tutorial/computerShop")
    public void doDelete(String sn ) {
        //偷懒的方法，这个不要学啊
        IocFactory.getComputerDao().deleteComputerByPrimaryKey(sn);
    }

    @At("/tutorial/computerShop/genTestData/?")
    @Ok("json")
    public String genTestData(int count) {
        for (int i=0;i<count;i++) {
            Computer c = new Computer();
            c.setCpu(String.valueOf(i));
            IocFactory.getComputerDao().insertComputer(c);
        }
        return "ok";
    }

}
