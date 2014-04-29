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
public class ForeignKey {
    private String foreignTable;
    private List references;

    public ForeignKey(String foreignTable){
        this.references = new ArrayList();
        this.foreignTable = foreignTable.toUpperCase();
    }

    public ForeignKey ref(String localcol,String foreigncol){
        this.references.add(new Reference(localcol.toUpperCase(),foreigncol.toUpperCase()));
        return this;
    }

    public String getForeignTable() {
        return foreignTable;
    }

    public ForeignKeyIterator getReferenceIterator(){
        return new ForeignKeyIterator(this.references.iterator());
    }
}
