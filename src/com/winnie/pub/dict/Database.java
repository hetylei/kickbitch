package com.winnie.pub.dict;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-9
 * Time: 21:28:33
 * To change this template use File | Settings | File Templates.
 */
public class Database {
    private List<Table> tables;

    private static Database db = null;

    public static Database instance(){
        if (db == null) db = new Database();
        return db;
    }

    /**
     * 创建表实例
     * @param name 表名
     * @return 表实例
     */
    public Table table(String name) throws DictException {
        if (this.tables == null) this.tables = new ArrayList<Table>();

        for (Table t : this.tables) {
            if (t.getName().toUpperCase().equals(name.toUpperCase()))
                throw new DictException("表名字重复(" + name + ")");
        }
        Table t = new Table(name);
        this.tables.add(t);
        return t;
    }

    public TableIterator getTablesIterator(){
        return new TableIterator(this.tables.iterator());
    }

    public TableIterator getTablesIterator(EnumReferenceType reftype){
        List<Table> results = new ArrayList<Table>();

        switch (reftype){
            case Alone:
                for (Table t : this.tables) {
                    if (t.hasForeignKey()) continue;
                    if (foreignKeyReferTable(t.getName())) continue;
                    results.add(t);
                }
                break;

            case Refer:
                for (Table t : this.tables) {
                    if (!t.hasForeignKey()) continue;
                    if (foreignKeyReferTable(t.getName())) continue;
                    results.add(t);
                }
                break;

            case Mixed:
                for (Table t : this.tables) {
                    if (!t.hasForeignKey()) continue;
                    if (!foreignKeyReferTable(t.getName())) continue;
                    results.add(t);
                }
                break;

            case Host:
                for (Table t : this.tables) {
                    if (t.hasForeignKey()) continue;
                    if (!foreignKeyReferTable(t.getName())) continue;
                    results.add(t);
                }
                break;
        }
        return new TableIterator(results.iterator());
    }


    /**
     * 是不是被引用了
     * @param name
     */
    public boolean foreignKeyReferTable(String name){
        for (Table t : this.tables) {
            if (t.getName().toUpperCase().equals(name.toUpperCase())) continue;
            if (!t.hasForeignKey()) continue;
            if (t.foreignKeyReferTable(name)) return true;
        }
        return false;
    }

    private void getDDLSub(List<Table> lt,TableIterator l){
        while(l.hasNext()) lt.add(l.next());
    }

    public TableIterator getDropOrderIterator(){
        List<Table> lt = new ArrayList<Table>();

        getDDLSub(lt,getTablesIterator(EnumReferenceType.Alone));
        getDDLSub(lt,getTablesIterator(EnumReferenceType.Refer));
        getDDLSub(lt,getTablesIterator(EnumReferenceType.Mixed));
        getDDLSub(lt,getTablesIterator(EnumReferenceType.Host));

        return new TableIterator(lt.iterator());
    }

    public TableIterator getCreateOrderIterator(){
        List<Table> lt = new ArrayList<Table>();

        getDDLSub(lt,getTablesIterator(EnumReferenceType.Host));
        getDDLSub(lt,getTablesIterator(EnumReferenceType.Mixed));
        getDDLSub(lt,getTablesIterator(EnumReferenceType.Refer));
        getDDLSub(lt,getTablesIterator(EnumReferenceType.Alone));

        return new TableIterator(lt.iterator());
    }

    /**
     * 获取表实例
     * @param name 表名
     * @return 实例
     */
    public Table getTable(String name) {
        for (TableIterator it = this.getTablesIterator(); it.hasNext();){
            Table t = it.next();
            if (t.getName().toUpperCase().equals(name.toUpperCase())) return t;
        }

        return null;
        //throw new DictException("没有找到table定义("+name+")");
    }

    public List<Table> getTables() {
        return tables;
    }
}
