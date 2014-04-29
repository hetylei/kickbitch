package com.winnie.pub.dict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-9
 * Time: 22:21:44
 * To change this template use File | Settings | File Templates.
 */
public class Unique {
    private List columns;
    private String remark;

    public Unique(String col){
        this.columns = new ArrayList();
        this.columns.add(col.toUpperCase());
    }

    public Unique and(String col){
        this.columns.add(col.toUpperCase());
        return this;
    }

    public Unique remark(String r){
        this.remark = r;
        return this;
    }

    public UniqueIterator getColumnsIterator(){
        return new UniqueIterator(this.columns.iterator());
    }
}
