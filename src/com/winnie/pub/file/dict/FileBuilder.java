package com.winnie.pub.file.dict;

import com.winnie.pub.dict.*;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-20
 * Time: 下午8:57
 * To change this template use File | Settings | File Templates.
 */
public class FileBuilder implements IBuilder {
    @Override
    public void define() {
        Database.instance().table("UploadFile").remark("上传文件")
                .column(new Column("sn").remark("主键").type(DataType.Varchar).len(40).required())
                .column(new Column("bizType").remark("上传文件模块类型").type(DataType.Varchar).len(40))
                .column(new Column("bizSn").remark("上传文件模块Sn").type(DataType.Varchar).len(40))
                .column(new Column("bizSign").remark("上传文件模块标识").type(DataType.Varchar).len(40))
                .column(new Column("fileName").remark("上传文件名").type(DataType.Varchar).len(400))
                .column(new Column("contentType").remark("上传文件类型").type(DataType.Varchar).len(40))
                .column(new Column("fileSize").remark("上传文件长度").type(DataType.Double))
                .column(new Column("filePath").remark("上传文件路径（相对)").type(DataType.Varchar).len(400))

                .column(new Column("createTime").remark("创建时间").type(DataType.Date))
                .column(new Column("createUser").remark("创建用户").type(DataType.Varchar).len(40))


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
