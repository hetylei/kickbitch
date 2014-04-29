package com.winnie.pub.creator.freemarker.eho;

import com.winnie.pub.creator.builder.BfmpBuilder;
import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.creator.pub.JavaBean;
import com.winnie.pub.dict.*;

import java.sql.Connection;
import java.util.List;



/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-30
 * Time: 16:02:23
 * To change this template use File | Settings | File Templates.
 */
public class EHOCreator {
    /**
     * 生成 列表EHO (使用模板config/template/ehoList.jt)
     * @param conn          连接
     * @param entityName    表名
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    public static void createListEHO(Connection conn, String entityName, String sourceBase, String packageName, String useEntityDao) {
        List<String> primaryKeys = BfmpBuilder.getEntityPrimaryKeys(conn, entityName);

        createListEHOFile(primaryKeys, entityName, sourceBase, packageName, useEntityDao);
    }

    /**
     * 生成 列表EHO (使用模板config/template/ehoList.jt)
     * @param table         bfmpbuidler table
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    public static void createListEHO(Table table, String sourceBase, String packageName, String useEntityDao) {
        createListEHOFile(table.getPrimaryKey().getColumns(), table.getName(), sourceBase, packageName, useEntityDao);
    }

    /**
     * 生成 列表EHO文件 (使用模板config/template/ehoList.jt)
     * @param primaryKeys   主键列表
     * @param entityName    实体名
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    private static void createListEHOFile(List<String> primaryKeys, String entityName, String sourceBase, String packageName, String useEntityDao) {
        String _entityName = entityName.replaceAll("_","");
        //primaryKeysStringBuffer String sn = _po.inputUrl("sn"); ...
        StringBuffer primaryKeysStringBuffer = new StringBuffer();
        //primaryKeysParamCall sn, ....
        StringBuffer primaryKeysParamCall = new StringBuffer();
        for (int i=0; i<primaryKeys.size(); i++) {
            String c = primaryKeys.get(i);
            primaryKeysStringBuffer.append("String ").append(c).append(" = _po.inputUrl(\"").append(c).append("\");\n");
            primaryKeysParamCall.append(c).append(", ");
        }

        primaryKeysParamCall.delete(primaryKeysParamCall.lastIndexOf(","), primaryKeysParamCall.lastIndexOf(" "));

        StringBuffer listEHOTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "ehoList.jt", "gb2312");
        String classList = listEHOTemplateStringBuffer.toString();
        classList = classList.replaceAll("#packageName", packageName);
        classList = classList.replaceAll("#path", packageName.replaceAll("\\.", "/")); 
        classList = classList.replaceAll("#EntityName", _entityName);//entityName
        classList = classList.replaceAll("#primarykeys", primaryKeysStringBuffer.toString());
        classList = classList.replaceAll("#primaryKeysParamCall", primaryKeysParamCall.toString());
        if (useEntityDao!=null && !useEntityDao.equals("")) {
            classList = classList.replaceAll("#DaoClass", useEntityDao+"Dao");
        } else {
            classList = classList.replaceAll("#DaoClass", _entityName+"Dao");//entityName
        }

        String fileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\eho\\"+_entityName+"ListEHO.java";//entityName
        if (FileOperator.saveToFile(classList, fileName, "gb2312"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }
    }

    /**
     * 生成 编辑EHO (使用模板config/template/ehoEdit.jt)
     * @param conn          连接
     * @param entityName    表名
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    public static void createEditEHO(Connection conn, String entityName, String sourceBase, String packageName, String useEntityDao) {
        List<String> primaryKeys = BfmpBuilder.getEntityPrimaryKeys(conn, entityName);

        createEditEHOFile(primaryKeys, entityName, sourceBase, packageName, useEntityDao);
    }

    /**
     * 生成 编辑EHO (使用模板config/template/ehoEdit.jt)
     * @param table         bfmpbuilder table
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    public static void createEditEHO(Table table, String sourceBase, String packageName, String useEntityDao) {
        createEditEHOFile(table.getPrimaryKey().getColumns(), table.getName(), sourceBase, packageName, useEntityDao);
    }

    /**
     * 生成 编辑EHO 文件 (使用模板config/template/ehoEdit.jt)
     * @param primaryKeys   主键列表
     * @param entityName    实体名
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     * @param useEntityDao  <br>
     * 如果不为null 或 “” 使用 useEntityDao 所指定的dao <br>
     * 例 createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * 则代码中调用的dao 为<br>
     * import com.yxosft.tester.mail.dao.MailDao;
     *
     * YXDaoFactory.getMailDao();
     */
    private static void createEditEHOFile(List<String> primaryKeys, String entityName, String sourceBase, String packageName, String useEntityDao){
        String _entityName = entityName.replaceAll("_","");
        //primaryKeysStringBuffer String sn = _po.inputUrl("sn"); ...
        StringBuffer primaryKeysStringBuffer = new StringBuffer();
        //primaryKeysParamCall sn, ....
        StringBuffer primaryKeysParamCall = new StringBuffer();
        //pkSetStringBuffer vo.setSn(KeyCreator.getUuid());
        StringBuffer pkSetStringBuffer = new StringBuffer();
        for (int i=0; i<primaryKeys.size(); i++) {
            String c = primaryKeys.get(i);
            primaryKeysStringBuffer.append("String ").append(c).append(" = _po.inputUrl(\"").append(c).append("\");\n");
            pkSetStringBuffer.append("        vo.").append(JavaBean.getSetterName(c)).
                    append("(KeyCreator.getUuid());\n");
            primaryKeysParamCall.append(c).append(", ");
        }

        primaryKeysParamCall.delete(primaryKeysParamCall.lastIndexOf(","), primaryKeysParamCall.lastIndexOf(" "));

        StringBuffer listModalTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "ehoEdit.jt", "gb2312");
        String classEdit = listModalTemplateStringBuffer.toString();
        classEdit = classEdit.replaceAll("#packageName", packageName);
        classEdit = classEdit.replaceAll("#path", packageName.replaceAll("\\.", "/")); 
        classEdit = classEdit.replaceAll("#EntityName", _entityName); //entityName
        classEdit = classEdit.replaceAll("#primarykeys", primaryKeysStringBuffer.toString());
        classEdit = classEdit.replaceAll("#setPk", pkSetStringBuffer.toString());
        classEdit = classEdit.replaceAll("#primaryKeysParamCall", primaryKeysParamCall.toString());
        if (useEntityDao!=null && !useEntityDao.equals("")) {
            classEdit = classEdit.replaceAll("#DaoClass", useEntityDao+"Dao");
        } else {
            classEdit = classEdit.replaceAll("#DaoClass", _entityName+"Dao"); //entityName
        }

        String fileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\eho\\"+_entityName+"EditEHO.java"; //entityName
        if (FileOperator.saveToFile(classEdit, fileName, "gb2312"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }    
    }
}
