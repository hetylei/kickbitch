package com.winnie.pub.creator.pub;

/**
 * Created by cl
 * Date: 2008-8-15
 * Time: 9:46:39
 */
public class JavaBean {
    /**
     * ȡ��javaBean set����
     * @param property  ����
     * @return  set����
     */
    public static String getSetterName(String property){
        if (property!=null && !property.equals("")){
            return "set"+property.toUpperCase().charAt(0)+property.substring(1);
        } else {
            return "";
        }
    }

    /**
     * ȡ��javaBean get����
     * @param property ����
     * @return set����
     */
    public static String getGetterName(String property){
        if (property!=null && !property.equals("")){
            return "get"+property.toUpperCase().charAt(0)+property.substring(1);
        } else {
            return "";
        }
    }
}
