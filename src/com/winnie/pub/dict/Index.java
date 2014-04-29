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
public class Index {
    private List columns;
    private String remark;

    public Index(String col){
        this.columns = new ArrayList();
        this.columns.add(col.toUpperCase());
    }

    public Index and(String col){
        this.columns.add(col.toUpperCase());
        return this;
    }

    public Index remark(String r){
        this.remark = r;
        return this;
    }

    public IndexIterator getColumnsIterator(){
        return new IndexIterator(this.columns.iterator());
    }
}
