package com.winnie.pub.dict;

/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-9
 * Time: 21:23:33
 * To change this template use File | Settings | File Templates.
 */
public class Column {
    private String property;
    private String name;
    private String remark;
    private int len;
    private int scape;  // -1: not use scape
    private boolean ai; // auto incremental
    private boolean required;
    private String defaultv;
    private DataType type;

    /**
     * ����һ���ֶ�
     * @param name �ֶ���
     */
    public Column(String name) {
        this.property = name;
        this.name = name.toUpperCase();
        this.scape = -1;
        this.len = -1;
        this.required = false;
        this.defaultv = null;
    }

    //property �� ibatis �õ��ֶ���
    public Column(String name,String property) {
        this.property = property;
        this.name = name.toUpperCase();
        this.scape = -1;
        this.len = -1;
        this.required = false;
        this.defaultv = null;
    }

    /**
     * �����ֶεı�ע��Ϣ
     * @param r
     * @return
     */
    public Column remark(String r){
        this.remark = r;
        return this;
    }

    /**
     * �����ֶ�����
     * @param type
     * @return
     */
    public Column type(DataType type){
        this.type = type;
        return this;
    }

    /**
     * �����ֶγ���
     * @param i
     * @return
     */
    public Column len(int i) {
        this.len = i;
        return this;
    }

    /**
     * С������
     * @param i
     * @return
     */
    public Column scape(int i) {
        this.scape = i;
        return this;
    }

    /**
     * Ĭ��ֵ
     * @param s
     * @return
     */
    public Column defaultv(String s) {
        this.defaultv = s;
        return this;
    }

    /**
     * ���� �����ֶ�,������������
     * @return
     */
    public Column ai(){
        this.ai = true;
        return this;
    }

    /**
     * ���� ��null
     * @return
     */
    public Column required(){
        this.required = true;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isDefault() {
        return this.defaultv != null && !this.defaultv.equals("");
    }

    public String getLenCaption(){

        if (len == -1) return "";
        if (scape == -1)
            return new StringBuffer().append("(").append(len).append(")").toString();
        else
            return new StringBuffer().append("(").append(len).append(",").append(scape).append(")").toString();
    }

    public String getTypeCaption(DBType dbtype) {
        try {
            return DataType.getTypeDefine(this.type,dbtype);
        } catch (DictException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public String getJavaCaption() {
        try {
            return DataType.getJavaDefine(this.type);
        } catch (DictException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public String getDefaultv() {
        return defaultv;
    }

    public DataType getDataType() {
        return type;
    }

    public String getProperty() {
        return property;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getScape() {
        return scape;
    }

    public void setScape(int scape) {
        this.scape = scape;
    }
}
