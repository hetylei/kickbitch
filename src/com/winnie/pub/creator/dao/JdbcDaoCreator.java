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
     * ����JDBC DAO�ӿڼ�DAOʵ�� (ʹ��ģ��config/template/daoInterface.jt|daoSqlMap.jt)
     * @param db
     * @param table      bfmpbuilder table
     * @param sourceBase ���ش���Դ·��
     * @param packageName �����ļ����ڰ���
     * @param appendToEntity <br>
*  �����Ϊnull �� ���� ʹ��׷��ģʽ:  <br>
*  �����ɵĲ���׷�ӵ� packageName.dao.appendToEntityDao ��<br>
*  ����createDao(t, "e:\bfmp", "com.yxsoft.tester.mail", "mailold"); <br>
*  ���ɴ��뽫�ֱ�׷�ӵ�:<br>
*  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldDao.java<br>
*  e:\bfmp\src\com\yxosft\tester\mail\dao\mailoldJdbcDao.java<br>
*
*  ��� appendToEntity ��ָ������ļ������� ���½�
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
            System.out.println("�����ˣ�"+table.getName()+"����û����������");
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
                    System.out.println("[success]��ӷ����ɹ�:"+daoFileName);
                } else {
                    System.out.println("[fails]��ӷ���ʧ��:"+daoFileName);
                }
            } else {
                if (FileOperator.saveToFile(daoClass, daoFileName, "gb2312"))
                {
                    System.out.println("[success]�����ɹ�:"+daoFileName);
                } else {
                    System.out.println("[fails]����ʧ��:"+daoFileName);
                }
            }
        } else {
            daoClass = daoClass.replaceAll("#DaoName", _entityName);

            String daoFileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\dao\\"+_entityName+"Dao.java";
            if (FileOperator.saveToFile(daoClass, daoFileName, "gb2312"))
            {
                System.out.println("[success]�����ɹ�:"+daoFileName);
            } else {
                System.out.println("[fails]����ʧ��:"+daoFileName);
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
                    System.out.println("[success]��ӷ����ɹ�:"+jdbcdaoFileName);
                } else {
                    System.out.println("[fails]��ӷ���ʧ��:"+jdbcdaoFileName);
                }
            } else {
                if (FileOperator.saveToFile(jdbcDaoClass, jdbcdaoFileName, "gb2312"))
                {
                    System.out.println("[success]�����ɹ�:"+jdbcdaoFileName);
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
                System.out.println("[success]�����ɹ�:"+jdbcdaoFileName);
            }

            if (modifyConfig) {
                FileOperator.addDaoConfig(sourceBase, _entityName,  packageName);//table.getName()
                FileOperator.addIocFactoryMethod(sourceBase, _entityName, packageName);//table.getName()
            }
        }
    }
}
