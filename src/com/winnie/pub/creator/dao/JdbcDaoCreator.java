package com.winnie.pub.creator.dao;



import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.creator.pub.JavaBean;
import com.winnie.pub.dict.*;

import java.util.List;

/**
 * Created by cl
 * Date: 2008-8-15
 * Time: 13:59:32
 */
public class JdbcDaoCreator {
    /**
     * 生成JDBC DAO接口及DAO实现 (使用模板config/template/daoInterface.jt|daoSqlMap.jt)
     * @param db
     * @param table      bfmpbuilder table
     * @param sourceBase 本地代码源路径
     * @param packageName 生成文件所在包名
     * @param appendToEntity <br>
*  如果不为null 或 “” 使用追加模式:  <br>
*  将生成的操作追加到 packageName.dao.appendToEntityDao 中<br>
*  例：createDao(t, "e:\bfmp", "com.yxsoft.tester.mail", "mailold"); <br>
*  生成代码将分别追加到:<br>
*  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldDao.java<br>
*  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldJdbcDao.java<br>
*
*  如果 appendToEntity 所指的相关文件不存在 则新建
     * @param modifyConfig
     */
    public static void createDao(DBType db, Table table, String sourceBase, String packageName, String appendToEntity, boolean modifyConfig) {

        String _entityName=table.getName();
        _entityName=_entityName.replaceAll("_","");

        List<Column> columns = table.getColumns();
        List<String> primaryKeys;
        try
        {
            primaryKeys = table.getPrimaryKey().getColumns();
        } catch (Exception e) {
            System.out.println("出错了，"+table.getName()+"可能没有设置主键");
            return;
        }

        //#ResultMap
        StringBuffer resultMapStringBuffer = new StringBuffer();
        //#insertField
        StringBuffer insertFieldStringBuffer = new StringBuffer();
        //#insertValue
        StringBuffer insertValueStringBuffer = new StringBuffer();
        //#updateField
        StringBuffer updateFieldStringBuffer = new StringBuffer();
        //#filterField
        StringBuffer filterFieldStringBuffer = new StringBuffer();
        for (Column c : columns) {
            //i.setName (rs.getString ("NAME"));
            try {
                resultMapStringBuffer.append("            i.").append(JavaBean.getSetterName(c.getProperty()))
                        .append("(").append("rs.").append(DataType.getJdbcDefine(c.getDataType()))
                        .append("(\"").append(c.getName()).append("\"));\n");
            } catch (DictException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            insertFieldStringBuffer.append(c.getProperty()).append(", ");
            insertValueStringBuffer.append("@").append(c.getProperty()).append(", ");
            if (primaryKeys.indexOf(c.getProperty()) == -1)
                updateFieldStringBuffer.append("                \" ").append(c.getProperty()).append(" = @")
                        .append(c.getProperty()).append(", \"+\n");

            /*
            *         if (param.get("customerSn") != null && !param.get("customerSn").equals("")) {
                         filter += " and customerSn = @customerSn ";
                     }
            * */
            filterFieldStringBuffer.append("        if (param.get(\"").append(c.getProperty())
                    .append("\") != null && !param.get(\"").append(c.getProperty())
                    .append("\").equals(\"\")) {").append("\n")
                    .append("            filter += \" and t.").append(c.getProperty())
                    .append(" = @").append(c.getProperty()).append(" \";").append("\n")
                    .append("        }").append("\n");
        }
        if (insertFieldStringBuffer.lastIndexOf(",") !=-1)
            insertFieldStringBuffer.delete(insertFieldStringBuffer.lastIndexOf(","), insertFieldStringBuffer.lastIndexOf(" "));
        if (insertValueStringBuffer.lastIndexOf(",") !=-1)
            insertValueStringBuffer.delete(insertValueStringBuffer.lastIndexOf(","), insertValueStringBuffer.lastIndexOf(" "));
        if (updateFieldStringBuffer.lastIndexOf(",") !=-1)
            updateFieldStringBuffer.delete(updateFieldStringBuffer.lastIndexOf(","), updateFieldStringBuffer.lastIndexOf(" "));



        //#primaryKey1
        String primaryKey1;
        if (primaryKeys.size() > 0) {
            primaryKey1 = primaryKeys.get(0);
        } else {
            primaryKey1 = columns.get(0).getProperty();
        }
        //#primaryKeyFilterParam  sn = @sn and sn2 = @sn2 ...
        StringBuffer primaryKeyFilterParam = new StringBuffer();
        //#primaryKeyFilterList  t.sn = ? and t.sn2 = ? ...
        StringBuffer primaryKeyFilterList = new StringBuffer();
        //#PrimaryKeyParamList String sn, String sn2 ...
        StringBuffer primaryKeyParamList = new StringBuffer();
        //#PrimaryKeyParamCall sn, sn2 ...
        StringBuffer primaryKeyParamCall = new StringBuffer();
        
        for (String pk : primaryKeys) {
            primaryKeyParamList.append("String ").append(pk).append(", ");
            primaryKeyFilterParam.append(pk).append(" = @").append(pk).append(" and ");
            primaryKeyFilterList.append("t.").append(pk).append(" = @").append(pk).append(" and ");
            primaryKeyParamCall.append(pk).append(", ");
        }
        primaryKeyParamList.delete(primaryKeyParamList.lastIndexOf(","), primaryKeyParamList.lastIndexOf(" "));
        primaryKeyFilterParam.delete(primaryKeyFilterParam.lastIndexOf("and"), primaryKeyFilterParam.lastIndexOf(" "));
        primaryKeyFilterList.delete(primaryKeyFilterList.lastIndexOf("and"), primaryKeyFilterList.lastIndexOf(" "));
        primaryKeyParamCall.delete(primaryKeyParamCall.lastIndexOf(","), primaryKeyParamCall.lastIndexOf(" "));
        
        //dao save
        StringBuffer daoTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "daoInterface.jt", "gb2312");
        String daoClass = daoTemplateStringBuffer.toString();
        daoClass = daoClass.replaceAll("#EntityName", _entityName);//table.getName()
        daoClass = daoClass.replaceAll("#TableName",table.getName());
        daoClass = daoClass.replaceAll("#packageName", packageName);
        daoClass = daoClass.replaceAll("#primaryKeyParamList", primaryKeyParamList.toString());

        if (appendToEntity!=null && !appendToEntity.equals("")) {
            daoClass = daoClass.replaceAll("#DaoName", appendToEntity);

            String daoFileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\dao\\"+appendToEntity+"Dao.java";
            if (FileOperator.isFileExists(daoFileName)) {
                daoClass = daoClass.substring(daoClass.indexOf("//#appendbegin")+"//#appendbegin".length(), daoClass.indexOf("//#appendend"));
                //System.out.println(daoClass);
                if (FileOperator.appendToFileEnd(daoFileName, daoClass, "}", "gb2312")){
                    System.out.println("[success]添加方法成功:"+daoFileName);
                } else {
                    System.out.println("[fails]添加方法失败:"+daoFileName);
                }
            } else {
                if (FileOperator.saveToFile(daoClass, daoFileName, "gb2312"))
                {
                    System.out.println("[success]创建成功:"+daoFileName);
                } else {
                    System.out.println("[fails]创建失败:"+daoFileName);
                }
            }
        } else {
            daoClass = daoClass.replaceAll("#DaoName", _entityName);

            String daoFileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\dao\\"+_entityName+"Dao.java";
            if (FileOperator.saveToFile(daoClass, daoFileName, "gb2312"))
            {
                System.out.println("[success]创建成功:"+daoFileName);
            } else {
                System.out.println("[fails]创建失败:"+daoFileName);
            }
        }

        //Jdbcdao save
        StringBuffer jdbcDaoTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "dao"+db.name()+"Jdbc.jt", "gb2312");
        String jdbcDaoClass = jdbcDaoTemplateStringBuffer.toString();
        jdbcDaoClass = jdbcDaoClass.replaceAll("#EntityName", _entityName);//table.getName()
        jdbcDaoClass = jdbcDaoClass.replaceAll("#TableName",table.getName());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#packageName", packageName);
        jdbcDaoClass = jdbcDaoClass.replaceAll("#ResultMap", resultMapStringBuffer.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#insertField", insertFieldStringBuffer.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#insertValue", insertValueStringBuffer.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#updateField", updateFieldStringBuffer.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#filterField", filterFieldStringBuffer.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#primaryKeyFilterParam", primaryKeyFilterParam.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#primaryKeyFilterList", primaryKeyFilterList.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#primaryKeyParamList", primaryKeyParamList.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#primaryKeyParamCall", primaryKeyParamCall.toString());
        jdbcDaoClass = jdbcDaoClass.replaceAll("#primaryKey1", primaryKey1);


        //ibatisXML = ibatisXML.replaceAll("#primaryKeyProperty", primaryKeyProperty.toString());

        if (appendToEntity!=null && !appendToEntity.equals("")) {
            jdbcDaoClass = jdbcDaoClass.replaceAll("#DaoName", appendToEntity);

            String jdbcdaoFileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\dao\\"+appendToEntity+"JdbcDao.java";
            if (FileOperator.isFileExists(jdbcdaoFileName)) {
                jdbcDaoClass = jdbcDaoClass.substring(jdbcDaoClass.indexOf("//#appendbegin")+"//#appendbegin".length(), jdbcDaoClass.indexOf("//#appendend"));
                //System.out.println(jdbcDaoClass);
                if (FileOperator.appendToFileEnd(jdbcdaoFileName, jdbcDaoClass, "}", "gb2312")){
                    System.out.println("[success]添加方法成功:"+jdbcdaoFileName);
                } else {
                    System.out.println("[fails]添加方法失败:"+jdbcdaoFileName);
                }
            } else {
                if (FileOperator.saveToFile(jdbcDaoClass, jdbcdaoFileName, "gb2312"))
                {
                    System.out.println("[success]创建成功:"+jdbcdaoFileName);
                }
                
                if (modifyConfig) {
                    FileOperator.addDaoConfig(sourceBase, appendToEntity, packageName);
                    FileOperator.addIocFactoryMethod(sourceBase, appendToEntity, packageName);
                }
            }
        } else {
            jdbcDaoClass = jdbcDaoClass.replaceAll("#DaoName", _entityName);//table.getName()

            String jdbcdaoFileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\dao\\"+_entityName+"JdbcDao.java";//table.getName()
            if (FileOperator.saveToFile(jdbcDaoClass, jdbcdaoFileName, "gb2312"))
            {
                System.out.println("[success]创建成功:"+jdbcdaoFileName);
            }

            if (modifyConfig) {
                FileOperator.addDaoConfig(sourceBase, _entityName,  packageName);//table.getName()
                FileOperator.addIocFactoryMethod(sourceBase, _entityName, packageName);//table.getName()
            }
        }
    }
}
