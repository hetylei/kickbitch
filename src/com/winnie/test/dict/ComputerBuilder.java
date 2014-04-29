package com.winnie.test.dict;

import com.winnie.pub.creator.CodeCreator;
import com.winnie.pub.dict.*;

/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2009-6-19
 * Time: 10:57:49
 */
public class ComputerBuilder implements IBuilder {

    public void define() {
        //���� Computer����
        this.Computer();
    }

    private void Computer() {
        // Database.instance().table������һ����
        // table("����").remark("��ע")

        Database.instance().table("Computer").remark("����")
                //table.column���һ�ֶζ���
                //new Column("�ֶ���").remark("��ע").type(DataType.�ֶ�����).len(����).scape(С������).defaultv(Ĭ��ֵ).required()//������;
                .column(new Column("id").remark("����").type(DataType.Int))
                .column(new Column("cpu").remark("cpu�ͺ�").type(DataType.Varchar).len(100).defaultv("inter").required())
                .column(new Column("memory").remark("�ڴ��ͺ�").type(DataType.Varchar).len(100))
                .column(new Column("hardDisk").remark("Ӳ���ͺ�").type(DataType.Varchar).len(100))
                .column(new Column("keyBoard").remark("�����ͺ�").type(DataType.Varchar).len(100))
                .column(new Column("mouse").remark("����ͺ�").type(DataType.Varchar).len(100))
                .column(new Column("cpuCoreCount").remark("cpu��������").type(DataType.Int))
                .column(new Column("madeDate").remark("��װʱ��").type(DataType.DateTime))

                        //��������
                        //��������ʹ�� new PrimaryKey(����1).and(����2);
                .primarykey(new PrimaryKey("id"))
        ;
    }


    public static void main(String argv[]) {
        //����builder��ʼ��
        new ComputerBuilder().define();
        //��ñ�ʵ��
        Table t1 = Database.instance().getTable("Computer");
        //����������
        System.out.println(t1.getCreateSQLServerDDL());

        //ʹ�ý��ּܴ��� vo,dao ���������
        CodeCreator.createJdbcDaoConfig(DBType.SQLServer, t1, "com.winnie.test", null);
        //ʹ�ý��ּܴ���freemarkerҳ�漰eho
        //CodeCreator.createFreeMarkerEntityEditor(t1, "com.winnie.test", null);
    }


}
