package com.winnie.pub.dict;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-9
 * Time: 22:31:18
 * To change this template use File | Settings | File Templates.
 */
public class Reference {
    private String local;
    private String foreign;

    public Reference(String local, String foreign) {
        this.local = local.toUpperCase();
        this.foreign = foreign.toUpperCase();
    }

    public String getLocal() {
        return local;
    }

    public String getForeign() {
        return foreign;
    }
}
