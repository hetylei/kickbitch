package com.winnie.pub.utils;




import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-10-15
 * Time: ÏÂÎç8:27
 * To change this template use File | Settings | File Templates.
 */
public class Flexigrid {
    public static String getFieldValue(Object o, String fieldName) {
        try {
            Class c = o.getClass();
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method m = c.getMethod(methodName);
            return m.invoke(o).toString();

        } catch (Exception e) {
            return "";
        }
    }

    public static Map<String, Object> generateData(List result, String pkFieldName, int page, int total) {
        Map<String, Object> rm = new HashMap<String, Object>();
        List<Map<String, Object>> cl = new ArrayList<Map<String, Object>>();
        for (Object o : result) {
            Map<String, Object> cr = new HashMap<String, Object>();
            cr.put("id", getFieldValue(o, pkFieldName));
            cr.put("cell", o);

            cl.add(cr);
        }
        rm.put("rows", cl);
        rm.put("page", page);
        rm.put("total", total);

        return rm;
    }
}
