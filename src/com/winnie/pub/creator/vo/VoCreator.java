package com.winnie.pub.creator.vo;

import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.creator.pub.JavaBean;
import com.winnie.pub.dict.*;

/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-17
 * Time: 16:22:57
 * To change this template use File | Settings | File Templates.
 */
public class VoCreator {

    /**
     * 创建值对象  使用模板(codeCreatorTemplate/vo.jt)
     * @param table      bfmpbuilder table
     * @param sourceBase 本地代码根路径
     * @param packageName 生成文件位于包
     */
    public static void createVo(Table table, String sourceBase, String packageName){
        createFile(table, table.getName(), sourceBase, packageName);
    }

    /**
     * 创建VO类
     * @param table       表
     * @param entityName    实体名
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     */
    private static void createFile(Table table, String entityName, String sourceBase, String packageName){

        String _entityName = entityName.replaceAll("_","");
        
        StringBuffer propertiesCode = new StringBuffer();
        StringBuffer gatCode = new StringBuffer();

        for (Column c : table.getColumns()) {
            try {
                propertiesCode.append("    /**").append(c.getRemark()).append("*/\n")
                        .append("    @Column\n");
                if (table.getPrimaryKey().hasPrimaryKey(c.getProperty()))  {
                    if (c.getDataType().equals(DataType.Int)) {
                        //自增主键
                        propertiesCode .append("    @Id\n");
                    } else {
                        //字符串主键
                        propertiesCode .append("    @Name\n");
                    }
                }
                propertiesCode.append("    private ").append(DataType.getJavaDefine(c.getDataType()))
                        .append(" ").append(c.getProperty()).append(";\n");
            } catch (DictException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            try {
                gatCode.append("    public ").append(DataType.getJavaDefine(c.getDataType()))
                        .append(" ").append(JavaBean.getGetterName(c.getProperty())).append("(){\n")
                        .append("        return ").append(c.getProperty()).append(";\n")
                        .append("    }\n");
            } catch (DictException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                gatCode.append("    public void ").append(JavaBean.getSetterName(c.getProperty())).append("(")
                        .append(DataType.getJavaDefine(c.getDataType())).append(" value){\n")
                        .append("        this.").append(c.getProperty()).append(" = value;\n")
                        .append("    }\n\n");
            } catch (DictException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        StringBuffer voTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "vo.jt", "gb2312");

        String voCode = voTemplateStringBuffer.toString();
        voCode = voCode.replaceAll("#TableName", entityName);
        voCode = voCode.replaceAll("#packageName", packageName);
        voCode = voCode.replaceAll("#EntityName", _entityName);//entityName
        voCode = voCode.replaceAll("#properties", propertiesCode.toString());
        voCode = voCode.replaceAll("#getterandsetter", gatCode.toString());

        String fileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\vo\\"+_entityName+".java"; //entityName
        if (FileOperator.saveToFile(voCode, fileName, "gb2312"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }
    }
}
