package com.winnie.pub.creator;

import com.winnie.pub.creator.dao.JdbcDaoCreator;
import com.winnie.pub.creator.freemarker.FreemarkerCreator;
import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.creator.vo.VoCreator;
import com.winnie.pub.dict.*;


import java.sql.*;
import java.util.Properties;



/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-17
 * Time: 9:09:48
 * To change this template use File | Settings | File Templates.
 */
public class CodeCreator {


    /**
     * 获取执行路径
     * @return
     * @throws Exception
     */
    public static String getPath() {
        System.out.println("请确保：builder是用ant编译到 /classes 中");
        return Thread.currentThread().getContextClassLoader().getResource("").getPath() + "../";
    }



    /**
     * 创建基于bfmpbuilder table 的
     * <br>vo对象         /src/{packageName}/vo
     * <br>dao 接口       /src/{packageName}/dao
     * <br>jdbcdao 实现   /src/{packageName}/dao
     * @param table
     * @param packageName
     * @param appendToEntity  是否使用追加模式 <br>
     *  如果不为null 或 “” 使用追加模式:  <br>
     *  将生成的操作追加到 packageName.dao.appendToEntityDao 中<br>
     *  例：createJdbcDaoConfig(t, "com.yxsoft.tester.mail", "mailold"); <br>
     *  生成代码将分别追加到:<br>
     *  {工程目录}\src\com\yxosft\tester\mail\dao\mailoldDao.java<br>
     *  {工程目录}\src\com\yxosft\tester\mail\dao\mailoldJdbcDao.java<br>
     *
     *  如果 appendToEntity 所指的相关文件不存在 则新建
     */
    public static void createJdbcDaoConfig(DBType db, Table table, String packageName, String appendToEntity){
        String sourceBase = getPath();
        VoCreator.createVo(table, sourceBase, packageName);
        JdbcDaoCreator.createDao(db, table, sourceBase, packageName, appendToEntity, true);
    }

    /**
     * 创建基于bfmpbuilder table 的(不修改springdao 和 YXDaoFactory配置
     * <br>vo对象
     * <br>dao 接口
     * <br>jdbcdao 实现
     * @param table
     * @param packageName
     * @param appendToEntity  是否使用追加模式 <br>
     *  如果不为null 或 “” 使用追加模式:  <br>
     *  将生成的操作追加到 packageName.dao.appendToEntityDao 中<br>
     *  例：createJdbcDaoConfig(t, "e:\bfmp", "com.yxsoft.tester.mail", "mailold"); <br>
     *  生成代码将分别追加到:<br>
     *  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldDao.java<br>
     *  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldJdbcDao.java<br>
     *
     *  如果 appendToEntity 所指的相关文件不存在 则新建
     */
    public static void createJdbcDaoConfigWithoutModifyConfig(DBType db, Table table, String packageName, String appendToEntity){
        String sourceBase = getPath();
        VoCreator.createVo(table, sourceBase, packageName);
        JdbcDaoCreator.createDao(db, table, sourceBase, packageName, appendToEntity, false);
    }


    /**
     * 创建基于builderr的  <br>
     * freemarke列表/查看/修改 页面      /web/{packageName}/<br>
     * 列表/查看/修改 eho                /src/{packageName}/eho<br>
     * @param table         bfmpbuilder table
     * @param packageName   生成代码包路径
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createFreeMarkerEntityEditor(t,"com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    public static void createFreeMarkerEntityEditor(Table table, String packageName, String useEntityDao) {
        String sourceBase = getPath();
        FreemarkerCreator.createEHO(table, sourceBase, packageName, useEntityDao);
    }



    /**
     * 删除  相关配置
     * @param entityName    表名
     * @param packageName   生成代码包路径
     */
    public static void deleteConfig(String entityName, String packageName){
        String sourceBase = getPath();

        FileOperator.delYXDaoFactoryMethod(sourceBase, entityName, packageName);


    }

    public static void main(String argv[]){
        //源代码路径 src 的上一级目录 例e:\bfmp\src 下面用e:\bfmp

        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties  pp =new Properties();
            pp.put("user", "yw001");
            pp.put("password", "hy123hy");
            pp.put("remarksReporting", "true");//这是用Properties的目的 打开remarksReporting
            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.232.56:1521:heyi",pp);

            //deleteConfig("HYJC_BASE_PRICE", SOURCEBASE, "com.yr365.hyjc.baseprice");
            //连接 表名称(已经在数据库中创建)  源代码路径 包名称
            //CodeCreator.createDaoConfig(conn, "HYJC_BASE_PRICE", SOURCEBASE, "com.yr365.hyjc.baseprice");
            // CodeCreator.createJdbcDaoConfig(conn, "HYJC_BASE_PRICE", SOURCEBASE, "com.yr365.hyjc.baseprice");
            //BfmpBuilder.createBuilder(conn, "NBGL_FeteFeeApply",SOURCEBASE,"com.yxsoft.dict.nbgl");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("[CodeCreator]驱动没有找到 (oracle.jdbc.driver.OracleDriver)");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
