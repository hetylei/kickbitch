package com.winnie.pub.acl.dict;

import com.winnie.pub.creator.CodeCreator;
import com.winnie.pub.dict.*;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-20
 * Time: ����8:57
 * To change this template use File | Settings | File Templates.
 */
public class AclBuilder implements IBuilder {
    @Override
    public void define() {
        Database.instance().table("SysUser").remark("��¼�û�")
                .column(new Column("sn").remark("����").type(DataType.Varchar).len(40).required())
                .column(new Column("customerSn").remark("�ͻ�").type(DataType.Varchar).len(40))
                .column(new Column("loginName").remark("��¼��").type(DataType.Varchar).len(20))
                .column(new Column("userName").remark("�û���").type(DataType.Varchar).len(100))
                .column(new Column("password").remark("����").type(DataType.Varchar).len(100))
                .column(new Column("orgSn").remark("��˾").type(DataType.Varchar).len(40))

                .primarykey(new PrimaryKey("sn"));

        Database.instance().table("SysOrg").remark("������֯")
                .column(new Column("sn").remark("����").type(DataType.Varchar).len(40).required())
                .column(new Column("customerSn").remark("�ͻ�sm").type(DataType.Varchar).len(40))
                .column(new Column("orgName").remark("��������").type(DataType.Varchar).len(100))
                .column(new Column("parentSn").remark("�ϼ�����").type(DataType.Varchar).len(40))
                .column(new Column("orgLevel").remark("��������").type(DataType.Int))

                .primarykey(new PrimaryKey("sn"));

    }


    public static void main(String[] args) {
        new AclBuilder().define();
        Table t = Database.instance().getTable("SysUser");
        Table t2 = Database.instance().getTable("SysOrg");

        System.out.println(t.getCreateMysqlDDL());
        System.out.println(t2.getCreateMysqlDDL());

        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t, "com.winnie.pub.acl", "Acl");
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t2, "com.winnie.pub.acl", "Acl");
    }


}
