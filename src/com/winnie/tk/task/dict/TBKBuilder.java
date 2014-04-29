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

        Database.instance().table("Task").remark("任务")
                .column(new Column("id").remark("主键").type(DataType.Int))
                .column(new Column("taskName").remark("任务名").type(DataType.Varchar).len(2000))
                /**格式以空格分隔的 1234567  1 3 5 空为每天执行 **/
                .column(new Column("taskWeekDay").remark("启动星期").type(DataType.Varchar).len(2000))
                /**格式以空格分隔的 yyyy-MM-dd   2012-09-07 空为每天执行 **/
                .column(new Column("taskDay").remark("启动日期").type(DataType.Varchar).len(2000))
                /**格式以空格分隔的 HH:mm:ss   16:00:00 20:00:00 22:00:00 **/
                .column(new Column("taskTime").remark("启动时间").type(DataType.Varchar).len(2000))
                /**一行一个命令**/
                .column(new Column("taskCommand").remark("命令").type(DataType.Varchar).len(2000))

                .column(new Column("isUse").remark("是否启用").type(DataType.Int))
                .column(new Column("isDelete").remark("是否启用").type(DataType.Int))
                .column(new Column("lastState").remark("最后执行状态").type(DataType.Text))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("TaskLog").remark("任务日志")
                .column(new Column("id").remark("主键").type(DataType.Int))
                .column(new Column("taskId").remark("任务").type(DataType.Int))
                .column(new Column("taskTime").remark("启动时间").type(DataType.DateTime))
                .column(new Column("taskLog").remark("日志").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("HotKeys").remark("关键词")
                .column(new Column("id").remark("主键").type(DataType.Int))
                //上升、热门
                .column(new Column("keyKind").remark("关键词类型").type(DataType.Varchar).len(20))
                .column(new Column("downloadDate").remark("下载日期").type(DataType.DateTime))
                .column(new Column("url").remark("地址").type(DataType.Varchar).len(1024))
                .column(new Column("catalog").remark("类目").type(DataType.Varchar).len(100))
                .column(new Column("key").remark("关键词").type(DataType.Varchar).len(100))
                .column(new Column("sortno").remark("排序号").type(DataType.Int))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("P4PKeys").remark("直通车主图关键词")
                .column(new Column("id").remark("主键").type(DataType.Int))
                //直通车主图关键词 空格分隔
                .column(new Column("p4pids").remark("直通车主图关键词").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("Shop").remark("店铺信息")
                .column(new Column("id").remark("主键").type(DataType.Int))

                .column(new Column("shopUrl").remark("店铺主链接").type(DataType.Varchar).len(2000))
                .column(new Column("shopName").remark("店铺名称").type(DataType.Varchar).len(2000))
                .column(new Column("wangwang").remark("主旺旺").type(DataType.Varchar).len(2000))
                .column(new Column("userRateUrl").remark("评价链接").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("ShopProduct").remark("店铺产品")
                .column(new Column("id").remark("主键").type(DataType.Int))

                .column(new Column("shopId").remark("店铺ID").type(DataType.Int))
                //item id
                .column(new Column("itemId").remark("产品ID").type(DataType.Int))
                .column(new Column("productName").remark("产品名称").type(DataType.Varchar).len(2000))
                .column(new Column("logState").remark("监控状态").type(DataType.Int))

                .primarykey(new PrimaryKey("id"))
        ;

        Database.instance().table("ShopProductTrade").remark("店铺产品交易记录")
                .column(new Column("id").remark("主键").type(DataType.Int))
                //item id
                .column(new Column("productId").remark("产品ID").type(DataType.Int))
                //交易时的名称
                .column(new Column("tradeName").remark("交易时的名称").type(DataType.Varchar).len(2000))
                .column(new Column("tradeTime").remark("交易时间").type(DataType.Varchar).len(2000))
                .column(new Column("tradeCount").remark("交易数量").type(DataType.Int))
                .column(new Column("tradePrice").remark("交易单价").type(DataType.Double))

                .column(new Column("saleCounts").remark("当前售出数量").type(DataType.Int))
                .column(new Column("orderCounts").remark("当前售出笔数").type(DataType.Int))
                .column(new Column("rateCounts").remark("当前评论数量").type(DataType.Int))

                .column(new Column("wangwang").remark("买家旺旺").type(DataType.Varchar).len(2000))
                .column(new Column("wangwangRater").remark("买家旺旺等级").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;


        Database.instance().table("ShopProductRate").remark("店铺产品评价记录")
                .column(new Column("id").remark("主键").type(DataType.Int))
                //item id
                .column(new Column("productId").remark("产品ID").type(DataType.Int))
                //交易时的名称
                .column(new Column("tradeName").remark("交易时的名称").type(DataType.Varchar).len(2000))
                .column(new Column("tradeCount").remark("交易数量").type(DataType.Int))
                .column(new Column("tradePrice").remark("交易单价").type(DataType.Double))

                .column(new Column("wangwang").remark("买家旺旺").type(DataType.Varchar).len(2000))
                .column(new Column("wangwangRater").remark("买家旺旺等级").type(DataType.Varchar).len(2000))
                //好评 中评 差价
                .column(new Column("rateKind").remark("评价类型").type(DataType.Varchar).len(2000))
                .column(new Column("userRate").remark("买家评价").type(DataType.Varchar).len(2000))
                .column(new Column("rateTime").remark("评价时间").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;


        Database.instance().table("ShopRate").remark("卖家评价记录")
                .column(new Column("id").remark("主键").type(DataType.Int))

                .column(new Column("shopId").remark("店铺ID").type(DataType.Int))
                //item id
                .column(new Column("itemId").remark("产品ID").type(DataType.Int))
                //交易时的名称
                .column(new Column("tradeName").remark("交易时的名称").type(DataType.Varchar).len(2000))
                .column(new Column("tradeCount").remark("交易数量").type(DataType.Int))
                .column(new Column("tradePrice").remark("交易单价").type(DataType.Double))

                .column(new Column("wangwang").remark("买家旺旺").type(DataType.Varchar).len(2000))
                .column(new Column("wangwangRater").remark("买家旺旺等级").type(DataType.Varchar).len(2000))

                //好评 中评 差价
                .column(new Column("rateKind").remark("评价类型").type(DataType.Varchar).len(2000))
                .column(new Column("salerRate").remark("卖家评价").type(DataType.Varchar).len(2000))
                .column(new Column("rateTime").remark("评价时间").type(DataType.Varchar).len(2000))

                .primarykey(new PrimaryKey("id"))
        ;

    }


    public static void main(String argv[]) {
        //调用builder初始化
        new TBKBuilder().define();
        //获得表实例
        Table t1 = Database.instance().getTable("Shop");
        //输出建表语句
        System.out.println(t1.getCreateMysqlDDL());

        //使用脚手架创建 vo,dao 及相关配置
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t1, "com.winnie.tk.task", "TBK");
        Table t2 = Database.instance().getTable("ShopProduct");
        //输出建表语句
        System.out.println(t2.getCreateMysqlDDL());

        //使用脚手架创建 vo,dao 及相关配置
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t2, "com.winnie.tk.task", "TBK");
        Table t3 = Database.instance().getTable("ShopProductTrade");
        //输出建表语句
        System.out.println(t3.getCreateMysqlDDL());

        //使用脚手架创建 vo,dao 及相关配置
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t3, "com.winnie.tk.task", "TBK");
        Table t4 = Database.instance().getTable("ShopRate");
        //输出建表语句
        System.out.println(t4.getCreateMysqlDDL());

        //使用脚手架创建 vo,dao 及相关配置
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t4, "com.winnie.tk.task", "TBK");
        Table t5 = Database.instance().getTable("ShopProductRate");
        //输出建表语句
        System.out.println(t5.getCreateMysqlDDL());

        //使用脚手架创建 vo,dao 及相关配置
        CodeCreator.createJdbcDaoConfig(DBType.MySQL, t5, "com.winnie.tk.task", "TBK");
        //使用脚手架创建freemarker页面及eho
        //CodeCreator.createFreeMarkerEntityEditor(t1, "com.winnie.test", null);
    }


}
