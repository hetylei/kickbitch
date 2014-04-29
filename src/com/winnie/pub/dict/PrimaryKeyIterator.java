package com.winnie.pub.dict;



import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-10
 * Time: 10:37:52
 * To change this template use File | Settings | File Templates.
 */
public class PrimaryKeyIterator extends BfmpIterator {

    public PrimaryKeyIterator(Iterator _iterator) {
        super(_iterator);
    }

    public String next(){
        return (String) _iterator.next();
    }
}
