package com.winnie.pub.creator.pub;

/**
 * Created by cl
 * Date: 2008-8-15
 * Time: 9:46:39
 */
public class JavaBean {
    /**
     * 取得javaBean set方法
     * @param property  属性
     * @return  set方法
     */
    public static String getSetterName(String property){
        if (property!=null && !property.equals("")){
            return "set"+property.toUpperCase().charAt(0)+property.substring(1);
        } else {
            return "";
        }
    }

    /**
     * 取得javaBean get方法
     * @param property 属性
     * @return set方法
     */
    public static String getGetterName(String property){
        if (property!=null && !property.equals("")){
            return "get"+property.toUpperCase().charAt(0)+property.substring(1);
        } else {
            return "";
        }
    }
}
