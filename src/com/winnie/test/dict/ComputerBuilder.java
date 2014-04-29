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
        //调用 Computer定义
        this.Computer();
    }

    private void Computer() {
        // Database.instance().table来定义一个表
        // table("表名").remark("备注")

        Database.instance().table("Computer").remark("电脑")
                //table.column添加一字段定义
                //new Column("字段名").remark("备注").type(DataType.字段类型).len(长度).scape(小数精度).defaultv(默认值).required()//必填项;
                .column(new Column("id").remark("主键").type(DataType.Int))
                .column(new Column("cpu").remark("cpu型号").type(DataType.Varchar).len(100).defaultv("inter").required())
                .column(new Column("memory").remark("内存型号").type(DataType.Varchar).len(100))
                .column(new Column("hardDisk").remark("硬盘型号").type(DataType.Varchar).len(100))
                .column(new Column("keyBoard").remark("键盘型号").type(DataType.Varchar).len(100))
                .column(new Column("mouse").remark("鼠标型号").type(DataType.Varchar).len(100))
                .column(new Column("cpuCoreCount").remark("cpu核心数量").type(DataType.Int))
                .column(new Column("madeDate").remark("组装时间").type(DataType.DateTime))

                        //主键定义
                        //多重主键使用 new PrimaryKey(主键1).and(主键2);
                .primarykey(new PrimaryKey("id"))
        ;
    }


    public static void main(String argv[]) {
        //调用builder初始化
        new ComputerBuilder().define();
        //获得表实例
        Table t1 = Database.instance().getTable("Computer");
        //输出建表语句
        System.out.println(t1.getCreateSQLServerDDL());

        //使用脚手架创建 vo,dao 及相关配置
        CodeCreator.createJdbcDaoConfig(DBType.SQLServer, t1, "com.winnie.test", null);
        //使用脚手架创建freemarker页面及eho
        //CodeCreator.createFreeMarkerEntityEditor(t1, "com.winnie.test", null);
    }


}
