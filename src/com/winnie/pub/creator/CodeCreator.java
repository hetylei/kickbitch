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
     * ��ȡִ��·��
     * @return
     * @throws Exception
     */
    public static String getPath() {
        System.out.println("��ȷ����builder����ant���뵽 /classes ��");
        return Thread.currentThread().getContextClassLoader().getResource("").getPath() + "../";
    }



    /**
     * ��������bfmpbuilder table ��
     * <br>vo����         /src/{packageName}/vo
     * <br>dao �ӿ�       /src/{packageName}/dao
     * <br>jdbcdao ʵ��   /src/{packageName}/dao
     * @param table
     * @param packageName
     * @param appendToEntity  �Ƿ�ʹ��׷��ģʽ <br>
     *  �����Ϊnull �� ���� ʹ��׷��ģʽ:  <br>
     *  �����ɵĲ���׷�ӵ� packageName.dao.appendToEntityDao ��<br>
     *  ����createJdbcDaoConfig(t, "com.yxsoft.tester.mail", "mailold"); <br>
     *  ���ɴ��뽫�ֱ�׷�ӵ�:<br>
     *  {����Ŀ¼}\src\com\yxosft\tester\mail\dao\mailoldDao.java<br>
     *  {����Ŀ¼}\src\com\yxosft\tester\mail\dao\mailoldJdbcDao.java<br>
     *
     *  ��� appendToEntity ��ָ������ļ������� ���½�
     */
    public static void createJdbcDaoConfig(DBType db, Table table, String packageName, String appendToEntity){
        String sourceBase = getPath();
        VoCreator.createVo(table, sourceBase, packageName);
        JdbcDaoCreator.createDao(db, table, sourceBase, packageName, appendToEntity, true);
    }

    /**
     * ��������bfmpbuilder table ��(���޸�springdao �� YXDaoFactory����
     * <br>vo����
     * <br>dao �ӿ�
     * <br>jdbcdao ʵ��
     * @param table
     * @param packageName
     * @param appendToEntity  �Ƿ�ʹ��׷��ģʽ <br>
     *  �����Ϊnull �� ���� ʹ��׷��ģʽ:  <br>
     *  �����ɵĲ���׷�ӵ� packageName.dao.appendToEntityDao ��<br>
     *  ����createJdbcDaoConfig(t, "e:\bfmp", "com.yxsoft.tester.mail", "mailold"); <br>
     *  ���ɴ��뽫�ֱ�׷�ӵ�:<br>
     *  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldDao.java<br>
     *  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldJdbcDao.java<br>
     *
     *  ��� appendToEntity ��ָ������ļ������� ���½�
     */
    public static void createJdbcDaoConfigWithoutModifyConfig(DBType db, Table table, String packageName, String appendToEntity){
        String sourceBase = getPath();
        VoCreator.createVo(table, sourceBase, packageName);
        JdbcDaoCreator.createDao(db, table, sourceBase, packageName, appendToEntity, false);
    }


    /**
     * ��������builderr��  <br>
     * freemarke�б�/�鿴/�޸� ҳ��      /web/{packageName}/<br>
     * �б�/�鿴/�޸� eho                /src/{packageName}/eho<br>
     * @param table         bfmpbuilder table
     * @param packageName   ���ɴ����·��
     * @param useEntityDao  <br>
     * �����Ϊnull �� ���� ʹ�� useEntityDao ��ָ����dao <br>
     * �� createFreeMarkerEntityEditor(t,"com.yxsoft.tester.mail", "Mail");<br>
     * ������е��õ�dao Ϊ<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    public static void createFreeMarkerEntityEditor(Table table, String packageName, String useEntityDao) {
        String sourceBase = getPath();
        FreemarkerCreator.createEHO(table, sourceBase, packageName, useEntityDao);
    }



    /**
     * ɾ��  �������
     * @param entityName    ����
     * @param packageName   ���ɴ����·��
     */
    public static void deleteConfig(String entityName, String packageName){
        String sourceBase = getPath();

        FileOperator.delYXDaoFactoryMethod(sourceBase, entityName, packageName);


    }

    public static void main(String argv[]){
        //Դ����·�� src ����һ��Ŀ¼ ��e:\bfmp\src ������e:\bfmp

        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties  pp =new Properties();
            pp.put("user", "yw001");
            pp.put("password", "hy123hy");
            pp.put("remarksReporting", "true");//������Properties��Ŀ�� ��remarksReporting
            conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.232.56:1521:heyi",pp);

            //deleteConfig("HYJC_BASE_PRICE", SOURCEBASE, "com.yr365.hyjc.baseprice");
            //���� ������(�Ѿ������ݿ��д���)  Դ����·�� ������
            //CodeCreator.createDaoConfig(conn, "HYJC_BASE_PRICE", SOURCEBASE, "com.yr365.hyjc.baseprice");
            // CodeCreator.createJdbcDaoConfig(conn, "HYJC_BASE_PRICE", SOURCEBASE, "com.yr365.hyjc.baseprice");
            //BfmpBuilder.createBuilder(conn, "NBGL_FeteFeeApply",SOURCEBASE,"com.yxsoft.dict.nbgl");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("[CodeCreator]����û���ҵ� (oracle.jdbc.driver.OracleDriver)");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
