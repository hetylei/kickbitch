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
public class PrimaryKey {
    private List columns;
    private String remark;

    public boolean hasPrimaryKey(String pk) {
        for (Object c :columns) {
            if (c.toString().equals(pk)) return true;
        }
        return false;
    }
    /**
     * 创建主键
     * @param col
     */
    public PrimaryKey(String col){
        this.columns = new ArrayList();
        this.columns.add(col);
    }

    /**
     * 创建复合主键
     * @param col
     * @return
     */
    public PrimaryKey and(String col){
        this.columns.add(col);
        return this;
    }

    public PrimaryKey remark(String r){
        this.remark = r;
        return this;
    }

    public PrimaryKeyIterator getColumnsIterator(){
        return new PrimaryKeyIterator(this.columns.iterator());
    }

    public List<String> getColumns(){
        return this.columns;
    }
}

