package com.winnie.tk.task.dict;

import com.winnie.pub.creator.CodeCreator;
import com.winnie.pub.dict.*;

/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2009-6-19
 * Time: 10:57:49
 */
public class TBKBuilder implements IBuilder {

    public void define() {

        Database.instance().table("Task").remark("����")
                .column(new Column("id").remark("����").type(DataType.Int))
                .column(new Column("taskName").remark("������").type(DataType.Varchar).len(2000))
                /**��ʽ�Կո�ָ��� 1234567  1 3 5 ��Ϊÿ��ִ�� **/
                .column(new Column("taskWeekDay").remark("��������").type(DataType.Varchar).len(2000))
                /**��ʽ�Կո�ָ��� yyyy-MM-dd   2012-09-07 ��Ϊÿ��ִ�� **/
                .column(new Column("taskDay").remark("��������").type(DataType.Varchar).len(2000))
                /**��ʽ�Կո�ָ��� HH:mm:ss   16:00:00 20:00:00 22:00:00 **/
                .column(new Column("taskTime").remark("����ʱ��").type(DataType.Varchar).len(2000))
                /**һ��һ������**/
                .column(new Column("taskCommand").remark("����").type(DataType.Varchar).len(2000))

                .column(new Column("isUse").remark("�Ƿ�����").type(DataType.Int))
                .column(new Column("isDelete").remark("�Ƿ�����").type(DataType.Int))
                .column(new Column("lastState").remark("���ִ��״̬").type(DataType.Text))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("TaskLog").remark("������־")
                .column(new Column("id").remark("����").type(DataType.Int))
                .column(new Column("taskId").remark("����").type(DataType.Int))
                .column(new Column("taskTime").remark("����ʱ��").type(DataType.DateTime))
                .column(new Column("taskLog").remark("��־").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("HotKeys").remark("�ؼ���")
                .column(new Column("id").remark("����").type(DataType.Int))
                //����������
                .column(new Column("keyKind").remark("�ؼ�������").type(DataType.Varchar).len(20))
                .column(new Column("downloadDate").remark("��������").type(DataType.DateTime))
                .column(new Column("url").remark("��ַ").type(DataType.Varchar).len(1024))
                .column(new Column("catalog").remark("��Ŀ").type(DataType.Varchar).len(100))
                .column(new Column("key").remark("�ؼ���").type(DataType.Varchar).len(100))
                .column(new Column("sortno").remark("�����").type(DataType.Int))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("P4PKeys").remark("ֱͨ����ͼ�ؼ���")
                .column(new Column("id").remark("����").type(DataType.Int))
                //ֱͨ����ͼ�ؼ��� �ո�ָ�
                .column(new Column("p4pids").remark("ֱͨ����ͼ�ؼ���").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("Shop").remark("������Ϣ")
                .column(new Column("id").remark("����").type(DataType.Int))

                .column(new Column("shopUrl").remark("����������").type(DataType.Varchar).len(2000))
                .column(new Column("shopName").remark("��������").type(DataType.Varchar).len(2000))
                .column(new Column("wangwang").remark("������").type(DataType.Varchar).len(2000))
                .column(new Column("userRateUrl").remark("��������").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("ShopProduct").remark("���̲�Ʒ")
                .column(new Column("id").remark("����").type(DataType.Int))

                .column(new Column("shopId").remark("����ID").type(DataType.Int))
                //item id
                .column(new Column("itemId").remark("��ƷID").type(DataType.Int))
                .column(new Column("productName").remark("��Ʒ����").type(DataType.Varchar).len(2000))
                .column(new Column("logState").remark("���״̬").type(DataType.Int))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("ShopProductTrade").remark("���̲�Ʒ���׼�¼")
                .column(new Column("id").remark("����").type(DataType.Int))
                //item id
                .column(new Column("productId").remark("��ƷID").type(DataType.Int))
                //����ʱ������
                .column(new Column("tradeName").remark("����ʱ������").type(DataType.Varchar).len(2000))
                .column(new Column("tradeTime").remark("����ʱ��").type(DataType.Varchar).len(2000))
                .column(new Column("tradeCount").remark("��������").type(DataType.Int))
                .column(new Column("tradePrice").remark("���׵���").type(DataType.Double))

                .column(new Column("saleCounts").remark("��ǰ�۳�����").type(DataType.Int))
                .column(new Column("orderCounts").remark("��ǰ�۳�����").type(DataType.Int))
                .column(new Column("rateCounts").remark("��ǰ��������").type(DataType.Int))

                .column(new Column("wangwang").remark("�������").type(DataType.Varchar).len(2000))
                .column(new Column("wangwangRater").remark("��������ȼ�").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;


        Database.instance().table("ShopProductRate").remark("���̲�Ʒ���ۼ�¼")
                .column(new Column("id").remark("����").type(DataType.Int))
                //item id
                .column(new Column("productId").remark("��ƷID").type(DataType.Int))
                //����ʱ������
                .column(new Column("tradeName").remark("����ʱ������").type(DataType.Varchar).len(2000))
                .column(new Column("tradeCount").remark("��������").type(DataType.Int))
                .column(new Column("tradePrice").remark("���׵���").type(DataType.Double))

                .column(new Column("wangwang").remark("�������").type(DataType.Varchar).len(2000))
                .column(new Column("wangwangRater").remark("��������ȼ�").type(DataType.Varchar).len(2000))
                //���� ���� ���
                .column(new Column("rateKind").remark("��������").type(DataType.Varchar).len(2000))
                .column(new Column("userRate").remark("�������").type(DataType.Varchar).len(2000))
                .column(new Column("rateTime").remark("����ʱ��").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;


        Database.instance().table("ShopRate").remark("�������ۼ�¼")
                .column(new Column("id").remark("����").type(DataType.Int))

                .column(new Column("shopId").remark("����ID").type(DataType.Int))
                //item id
                .column(new Column("itemId").remark("��ƷID").type(DataType.Int))
                //����ʱ������
                .column(new Column("tradeName").remark("����ʱ������").type(DataType.Varchar).len(2000))
                .column(new Column("tradeCount").remark("��������").type(DataType.Int))
                .column(new Column("tradePrice").remark("���׵���").type(DataType.Double))

                .column(new Column("wangwang").remark("�������").type(DataType.Varchar).len(2000))
                .column(new Column("wangwangRater").remark("��������ȼ�").type(DataType.Varchar).len(2000))

                //���� ���� ���
                .column(new Column("rateKind").remark("��������").type(DataType.Varchar).len(2000))
                .column(new Column("salerRate").remark("��������").type(DataType.Varchar).len(2000))
                .column(new Column("rateTime").remark("����ʱ��").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

    }


    public static void main(String argv[]) {
        //����builder��ʼ��
        new TBKBuilder().define();
        //��ñ�ʵ��
        Table t1 = Database.instance().getTable("Shop");
        //����������
        System.out.println(t1.getCreateMysqlDDL());

        //ʹ�ý��ּܴ��� vo,dao ���������
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t1, "com.winnie.tk.task", "TBK");
        Table t2 = Database.instance().getTable("ShopProduct");
        //����������
        System.out.println(t2.getCreateMysqlDDL());

        //ʹ�ý��ּܴ��� vo,dao ���������
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t2, "com.winnie.tk.task", "TBK");
        Table t3 = Database.instance().getTable("ShopProductTrade");
        //����������
        System.out.println(t3.getCreateMysqlDDL());

        //ʹ�ý��ּܴ��� vo,dao ���������
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t3, "com.winnie.tk.task", "TBK");
        Table t4 = Database.instance().getTable("ShopRate");
        //����������
        System.out.println(t4.getCreateMysqlDDL());

        //ʹ�ý��ּܴ��� vo,dao ���������
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t4, "com.winnie.tk.task", "TBK");
        Table t5 = Database.instance().getTable("ShopProductRate");
        //����������
        System.out.println(t5.getCreateMysqlDDL());

        //ʹ�ý��ּܴ��� vo,dao ���������
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t5, "com.winnie.tk.task", "TBK");
        //ʹ�ý��ּܴ���freemarkerҳ�漰eho
        //CodeCreator.createFreeMarkerEntityEditor(t1, "com.winnie.test", null);
    }


}
