package com.winnie.pub.dict;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-10
 * Time: 10:41:02
 * To change this template use File | Settings | File Templates.
 */
public class BfmpIterator {
    protected final Iterator _iterator;

    public BfmpIterator(Iterator _iterator) {
        this._iterator = _iterator;
    }

    public boolean hasNext(){
        return _iterator.hasNext();
    }
}
