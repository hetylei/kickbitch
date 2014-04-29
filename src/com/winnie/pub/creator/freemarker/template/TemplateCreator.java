package com.winnie.pub.creator.freemarker.template;


import com.winnie.pub.creator.pub.FileOperator;
import com.winnie.pub.dict.*;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-30
 * Time: 13:56:36
 * To change this template use File | Settings | File Templates.
 */
public class TemplateCreator {

    /**
     * 生成 页面列表HTML (使用模板config/template/templateList.ht)
     * @param table         bfmpbuilder table
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     */
    public static void createListHtml(Table table, String sourceBase, String packageName) {
        createListHtmlFile(table, sourceBase, packageName);
    }

    /**
     * 创建列表HTML文件
     * @param table    表
     * @param sourceBase 本地代码根路径
     * @param packageName 生成文件位于包
     */
    private static void createListHtmlFile(Table table , String sourceBase, String packageName){

        String _entityName=table.getName().replaceAll("_", "");
        StringBuilder fieldStringBuffer = new StringBuilder();
        StringBuilder queryStringBuffer = new StringBuilder();

        for (Column c : table.getColumns()) {
            //{display : '客户名称', name : 'name', width : 180, sortable : true, align : 'left'},
            if (!table.getPrimaryKey().hasPrimaryKey(c.getProperty())) {
                if (!fieldStringBuffer.toString().equals("")) fieldStringBuffer.append(",\n");
                fieldStringBuffer.append("                            {display : '").append(c.getRemark()).
                        append("', name : '").append(c.getProperty()).append("', width : 50, sortable : true, align : 'left'}");

                //{display : '客户类型',name : 'custTypeName',isdefault : true}
                //{display : '客户名称',name : 'name' },
                if (!queryStringBuffer.toString().equals("")) queryStringBuffer.append(",\n");
                queryStringBuffer.append("                            {display : '").append(c.getRemark()).
                        append("', name : '").append(c.getProperty()).append("'");
                if (queryStringBuffer.toString().equals("")) queryStringBuffer.append(",isdefault : true");
                queryStringBuffer.append("}");

            }
        }


        StringBuffer listHtmlTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "templateList.ht", "utf-8");
        String pageList = listHtmlTemplateStringBuffer.toString();
        pageList = pageList.replaceAll("#EntityName", _entityName);//entityName
        pageList = pageList.replaceAll("#EntityRemark", table.getRemark());//entityName
        pageList = pageList.replaceAll("#field", fieldStringBuffer.toString());
        pageList = pageList.replaceAll("#query", queryStringBuffer.toString());
        pageList = pageList.replaceAll("#primarykey", table.getPrimaryKey().getColumns().get(0));


        String fileName = sourceBase+"\\web\\"+packageName.replaceAll("\\.","\\\\")+"\\"+_entityName+"List.ftl";//entityName
        if (FileOperator.saveToFile(pageList, fileName, "utf-8"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }
    }


    /**
     * 生成 页面编辑HTML (使用模板config/template/templateEdit.ht)
     * @param table         bfmpbuilder table
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     */
    public static void createEditHtml(Table table, String sourceBase, String packageName) {
        createEditHtmlFile(table, sourceBase, packageName);
    }

    /**
     * 生成 页面编辑HTML文件 (使用模板config/template/templateEdit.ht)

     * @param table            表
     * @param sourceBase       本地代码根路径
     * @param packageName      生成文件位于包
     */
    private static void createEditHtmlFile(Table table,  String sourceBase, String packageName) {
        String _entityName = table.getName().replaceAll("_","");
        StringBuffer fieldStringBuffer = new StringBuffer();
        StringBuffer propertiesStringBuffer = new StringBuffer();
        StringBuffer primaryKeysStringBuffer = new StringBuffer();
        int tdCount =0;
        String b2r = "", b2t = " b2t";
        for (Column c : table.getColumns()) {
            if (!table.getPrimaryKey().hasPrimaryKey(c.getProperty())) {
                fieldStringBuffer.append("<p>\n").append(c.getRemark())
                        .append("  <input type=\"text\"  size=\"20\" id=\"")
                        .append(c.getProperty()).append("\" name=\"").append(c.getProperty()).append("\" ");

                //添加是否不能为空/最大长度校验
                fieldStringBuffer.append("class=\"")
                        .append(c.isRequired()?"required ":"");


                //根据数据类型 添加JS校验
                //Varchar,Smallint,Date,Time,Double,Float,Numeric,
                //DateTime,Int,Blob,Bool,Char;

                if (c.getDataType()==DataType.Varchar || c.getDataType()==DataType.Char) {
                    //无格式要求
                    fieldStringBuffer.append(c.getLen()==-1?"":"max-length-"+c.getLen()+" "); 
                }else if (c.getDataType()==DataType.Smallint || c.getDataType()==DataType.Int) {
                    fieldStringBuffer.append("validate-number ").append(c.getLen()==-1?"":"max-length-"+c.getLen()+" ");
                } else if (c.getDataType()==DataType.Double || c.getDataType()==DataType.Float || c.getDataType()==DataType.Numeric) {
                    fieldStringBuffer.append("validate-float ").append(c.getLen()==-1?"":"max-length-"+c.getLen()+" ");
                } else if (c.getDataType()==DataType.Date) {
                    fieldStringBuffer.append("validate-date-yyyy-MM-dd");
                } else if (c.getDataType()==DataType.Time) {
                    fieldStringBuffer.append("validate-date-hh:mm:ss");
                } else if (c.getDataType()==DataType.DateTime) {
                    fieldStringBuffer.append("validate-date-yyyy-MM-dd hh:mm:ss");
                }

                fieldStringBuffer.append("\"")
                        .append((c.getDataType()== DataType.Date || c.getDataType()==DataType.DateTime)
                                ?
                                    " onClick=\"popCalendar(this)\" "
                                :
                                    "")
                        .append(">\n")
                        .append(c.isRequired()?"(必填)":"");

                //赋值js语句
                fieldStringBuffer.append("\n<script>##(\"#").append(c.getProperty()).append("\").val(\"")
                        .append("##{(obj.").append(_entityName) //entityName
                        .append("Obj.").append(c.getProperty());
                //设置日期类型输出格式
                if (c.getDataType()==DataType.Date) {
                    fieldStringBuffer.append("?string(\'yyyy-MM-dd\'))");
                } else if (c.getDataType()==DataType.Time) {
                    fieldStringBuffer.append("?string(\'HH:mm:ss\'))");
                } else if (c.getDataType()==DataType.DateTime) {
                    fieldStringBuffer.append("?string(\'yyyy-MM-dd HH:mm:ss\'))");
                } else {
                    fieldStringBuffer.append(")");
                }
                fieldStringBuffer.append("?default('')}\");</script>\n ").append("</p>\n");



            } else {
                primaryKeysStringBuffer.append("<INPUT TYPE=\"hidden\" NAME=\"").append(c.getProperty())
                        .append("\" VALUE=\"").append("##{obj.").append(_entityName).append("Obj.") //entityName
                        .append(c.getProperty()).append("?default('')}").append("\">\n");
            }
        }


        StringBuffer editHtmlTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "templateEdit.ht", "utf-8");
        String pageList = editHtmlTemplateStringBuffer.toString();
        pageList = pageList.replaceAll("#EntityName", _entityName); //entityName
        pageList = pageList.replaceAll("#EntityRemark", table.getRemark()); //entityName
        pageList = pageList.replaceAll("#primaryKeys", primaryKeysStringBuffer.toString());
        pageList = pageList.replaceAll("#propertyEdits", fieldStringBuffer.toString());
        pageList = pageList.replaceAll("##", "\\$");

        String fileName = sourceBase+"\\web\\"+packageName.replaceAll("\\.","\\\\")+"\\"+_entityName+"Edit.ftl"; //entityName
        if (FileOperator.saveToFile(pageList, fileName, "utf-8"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }
    }

    /**
     * 生成 页面查看HTML (使用模板config/template/templateView.ht)
     * @param table         bfmpbuilder table
     * @param sourceBase    本地代码根路径
     * @param packageName   生成文件位于包
     */
    public static void createViewHtml(Table table, String sourceBase, String packageName) {
        createViewHtmlFile(table.getColumns(), table.getPrimaryKey().getColumns(), table.getName(), sourceBase, packageName);
    }

    /**
     * 生成 页面查看HTML文件 (使用模板config/template/templateView.ht)
     * @param columns          字段列表
     * @param primaryKeys      主键列表
     * @param entityName       实体名
     * @param sourceBase       本地代码根路径
     * @param packageName      生成文件位于包
     */
    private static void createViewHtmlFile(List<Column> columns, List<String> primaryKeys, String entityName, String sourceBase, String packageName) {
        String _entityName = entityName.replaceAll("_","");
        StringBuffer fieldStringBuffer = new StringBuffer();
        int tdCount =0;

        String b2r = "", b2t = " b2t";
        for (int i=0; i<columns.size(); i++) {
            Column c = columns.get(i);
            
            if (primaryKeys.indexOf(c.getProperty())==-1) {
                if (tdCount == 0) {
                    fieldStringBuffer.append("  <tr>\n");
                    b2r = "";
                } else {
                    b2r = " b2r";
                }
                fieldStringBuffer.append("        <td  align=\"center\" class=\"b2l b2b "+b2t+"\" bgcolor=\"#CCCCCC\">")
                        .append(c.getRemark()).append("</td>\n")
                        .append("        <td class=\"b2l b2b "+b2t+b2r+"\">\n")
                        .append("##{(obj.").append(_entityName) //entityName
                        .append("Obj.").append(c.getProperty());
                //设置日期类型输出格式
                if (c.getDataType()==DataType.Date) {
                    fieldStringBuffer.append("?string(\"yyyy-MM-dd\"))?default('&nbsp;')} ");
                } else if (c.getDataType()==DataType.Time) {
                    fieldStringBuffer.append("?string(\"HH:mm:ss\"))?default('&nbsp;')} ");
                } else if (c.getDataType()==DataType.DateTime) {
                    fieldStringBuffer.append("?string(\"yyyy-MM-dd HH:mm:ss\"))?default('&nbsp;')} ");
                } else {
                    fieldStringBuffer.append("?default('&nbsp;'))} ");
                }
            
                fieldStringBuffer.append("</td>\n");
                tdCount++;
                if (tdCount > 1) {
                    fieldStringBuffer.append("  </tr>\n");
                    tdCount = 0;
                    b2t = "";
                }

            } 
        }

        if (tdCount == 1) {
            fieldStringBuffer.append("        <td align=\"center\" class=\"b2l b2b\">")
                        .append("&nbsp;</td>\n")
                        .append("        <td class=\"b2l b2b b2r\">&nbsp;</td>\n")
                        .append("  </tr>\n");
        }

        StringBuffer listHtmlTemplateStringBuffer = FileOperator.getTemplate(sourceBase, "templateView.ht", "utf-8");
        String pageList = listHtmlTemplateStringBuffer.toString();
        pageList = pageList.replaceAll("#EntityName", _entityName); //entityName
        pageList = pageList.replaceAll("#PropertyViews", fieldStringBuffer.toString());
        pageList = pageList.replaceAll("##", "\\$");
        pageList = pageList.replaceAll("#eho", packageName+".eho."+_entityName+"EditEHO"); //entityName

        String fileName = sourceBase+"\\web\\"+packageName.replaceAll("\\.","\\\\")+"\\"+_entityName+"View.shtml"; //entityName
        if (FileOperator.saveToFile(pageList, fileName, "utf-8"))
        {
            System.out.println("[success]创建成功:"+fileName);
        }
    }
}
