package com.winnie.pub.dict;


import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-18
 * Time: 11:14:35
 * To change this template use File | Settings | File Templates.
 */
public class ColumnIterator extends BfmpIterator {
    public ColumnIterator(Iterator _iterator) {
        super(_iterator);
    }

    public Column next(){
        return (Column) _iterator.next();
    }
}
