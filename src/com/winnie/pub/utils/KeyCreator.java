package com.winnie.pub.utils;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-10-16
 * Time: ионГ9:07
 * To change this template use File | Settings | File Templates.
 */
public class KeyCreator {
    private static UUIDHexGenerator uuid = new  UUIDHexGenerator();

    public static String getUuid(){
         return uuid.generate();
    }
}
