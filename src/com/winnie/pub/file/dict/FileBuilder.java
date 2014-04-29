package com.winnie.pub.file.dict;

import com.winnie.pub.dict.*;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-20
 * Time: ����8:57
 * To change this template use File | Settings | File Templates.
 */
public class FileBuilder implements IBuilder {
    @Override
    public void define() {
        Database.instance().table("UploadFile").remark("�ϴ��ļ�")
                .column(new Column("sn").remark("����").type(DataType.Varchar).len(40).required())
                .column(new Column("bizType").remark("�ϴ��ļ�ģ������").type(DataType.Varchar).len(40))
                .column(new Column("bizSn").remark("�ϴ��ļ�ģ��Sn").type(DataType.Varchar).len(40))
                .column(new Column("bizSign").remark("�ϴ��ļ�ģ���ʶ").type(DataType.Varchar).len(40))
                .column(new Column("fileName").remark("�ϴ��ļ���").type(DataType.Varchar).len(400))
                .column(new Column("contentType").remark("�ϴ��ļ�����").type(DataType.Varchar).len(40))
                .column(new Column("fileSize").remark("�ϴ��ļ�����").type(DataType.Double))
                .column(new Column("filePath").remark("�ϴ��ļ�·�������)").type(DataType.Varchar).len(400))

                .column(new Column("createTime").remark("����ʱ��").type(DataType.Date))
                .column(new Column("createUser").remark("�����û�").type(DataType.Varchar).len(40))


                .primarykey(new PrimaryKey("sn"));


    }


    public static void main(String[] args) {
        new FileBuilder().define();
        Table t = Database.instance().getTable("UploadFile");

        System.out.print(t.getCreateMysqlDDL());
        //CodeCreator.createJdbcDaoConfig(t, "com.winnie.pub.file", "File");
        //CodeCreator.createFreeMarkerEntityEditor(t, "com.winnie.pub.file", "File");
    }


}
