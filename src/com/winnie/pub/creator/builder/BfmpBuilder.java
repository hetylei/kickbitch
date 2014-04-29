package com.winnie.pub.creator.builder;

import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.dict.*;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-17
 * Time: 8:53:41
 * To change this template use File | Settings | File Templates.
 */
public class BfmpBuilder {
    /**
     * 从数据库取得表结构描述
     * @param conn   连接对象
     * @param dbtype 数据库类型
     * @param entityName  表名
     * @return Column对象列表
     */
    public static List<Column> getEntityColumns(Connection conn, DBType dbtype, String entityName) {
        List<Column> entityColumns = new ArrayList<Column>();
        try {
            ResultSet columns = conn.getMetaData().getColumns(null, null, entityName, null);

            while (columns.next()) {
                /*
                for (int i=1 ;i<=columns.getMetaData().getColumnCount();i++) {
                    System.out.print(columns.getString(i)+"|");
                }
                System.out.print("\n");
                */
                Column c = new Column(columns.getString("COLUMN_NAME"));
                try {
                    c.type(DataType.getDataType(columns.getInt("DATA_TYPE"), dbtype));
                } catch(Exception ex) {
                    System.out.println("暂不支持数据类型 ("+columns.getInt("DATA_TYPE")+")"+columns.getString("TYPE_NAME"));    
                }
                if (columns.getInt("COLUMN_SIZE")!=0)
                    c.len(columns.getInt("COLUMN_SIZE"));
                if (columns.getInt("NULLABLE")==0) {
                    c.required();
                }
                if (columns.getString("REMARKS")!=null)
                    c.remark(columns.getString("REMARKS"));
                else
                    System.out.println("REMARKS "+columns.getString("COLUMN_NAME")+" 为空,请在数据库中设置.");    
                entityColumns.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entityColumns;
    }

    /**
     * 取得表主键列表
     * @param conn 连接
     * @param entityName 表名
     * @return 主键字符串列表
     */
    public static List<String> getEntityPrimaryKeys(Connection conn, String entityName) {
        List<String> entityPrimaryKeys = new ArrayList<String>();
        try {
            ResultSet columns = conn.getMetaData().getPrimaryKeys(null, null, entityName);

            while (columns.next()) {
                entityPrimaryKeys.add(columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entityPrimaryKeys;
    }

    /**
     * todo 取得Uniques列表
     * @param conn   连接
     * @param entityName 表名
     * @return Uniques字符串列表
     */
    public static List<String> getEntityUniques(Connection conn, String entityName) {
        List<String> entityUniques = new ArrayList<String>();

        return entityUniques;
    }

    /**
     * 生成Bulder java 文件(使用 config/template/builder.jt模板)
     * @param conn  连接
     * @param entityName 表名
     * @param sourceBase 本地源代码根路径
     * @param packageName 生成代码位于包名
     */
    public static void createBuilder(Connection conn, String entityName, String sourceBase, String packageName) {
        StringBuffer builderTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "builder.jt", "gb2312");
        StringBuffer dictDesciption = new StringBuffer();
        String javaBeanName = entityName.replaceAll("_", "");
        dictDesciption.append("Database.instance().table(\"").append(entityName).append("\")\n");

        //填充字段
        //.column(new Column("isSend").remark("是否已经发送").type(DataType.Numeric).required().defaultv("0"))
        List<Column> entityColumns = BfmpBuilder.getEntityColumns(conn, DBType.Oracle, entityName.toUpperCase());
        for (int i=0; i<entityColumns.size(); i++) {
            Column c = entityColumns.get(i);

            dictDesciption.append("        .column(new Column(\"");
            dictDesciption.append(c.getProperty()).append("\")");
            if (c.getRemark()!=null)
                dictDesciption.append(".remark(\"").append(c.getRemark()).append("\")");
            if (c.getDataType()!=null) {
                dictDesciption.append(".type(DataType.").append(c.getDataType().toString()).append(")");
            }
            else
                dictDesciption.append(".type(UNSUPPORTTYPE)");
            if (!c.getLenCaption().equals(""))
                dictDesciption.append(".len").append(c.getLenCaption());
            if (c.getDefaultv()!=null)
                dictDesciption.append(".defaultv(\"").append(c.getDefaultv()).append("\")");
            if (c.isRequired())
                dictDesciption.append(".required()");
            dictDesciption.append(")\n");
        }
        dictDesciption.append("\n");
        //填充主键
        //.primarykey(new PrimaryKey("sn").and("name"))
        List<String> entityPrimaryKeys = BfmpBuilder.getEntityPrimaryKeys(conn, entityName);
        if (entityPrimaryKeys.size()>0) {
            dictDesciption.append("        .primarykey(new PrimaryKey");
            for (int i = 0; i<entityPrimaryKeys.size(); i++){
                if (i!=0) dictDesciption.append(".and");
                dictDesciption.append("(\"").append(entityPrimaryKeys.get(i)).append("\")");
            }
            dictDesciption.append(")");
        } else {
            System.out.println("警告："+entityName+"没有设置主键");
        }

        //填充完毕
        dictDesciption.append(";");

        String builderCode = builderTemplateStringBuffer.toString();
        builderCode = builderCode.replaceAll("#packageName", packageName);
        builderCode = builderCode.replaceAll("#EntityName", javaBeanName);
        builderCode = builderCode.replaceAll("#tableName", entityName);
        builderCode = builderCode.replaceAll("#DictDesciption", dictDesciption.toString());

        //System.out.println(builderCode);


        String fileName = sourceBase+"\\src\\"+packageName.replaceAll("\\.","\\\\")+"\\dict\\"+
                           javaBeanName+"Builder.java";
        if (FileOperator.saveToFile(builderCode, fileName, "gb2312"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }

    }
}
