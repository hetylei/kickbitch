package com.winnie.pub.creator.freemarker;



import com.winnie.pub.dict.*;
import com.winnie.pub.creator.freemarker.template.TemplateCreator;

/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-30
 * Time: 15:35:08
 * To change this template use File | Settings | File Templates.
 */
public class FreemarkerCreator {


    /**
     * 生成 创建FreeMarker 列表编辑EHO及HTML页面
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
    public static void createEHO(Table table, String sourceBase, String packageName, String useEntityDao) {
        TemplateCreator.createListHtml(table, sourceBase, packageName);
        //EHOCreator.createListEHO(table, sourceBase, packageName, useEntityDao);

        TemplateCreator.createEditHtml(table, sourceBase, packageName);
        //TemplateCreator.createViewHtml(table, sourceBase, packageName);
        //EHOCreator.createEditEHO(table, sourceBase, packageName, useEntityDao);

        System.out.println("freemarker 页面创建完成: 访问地址 bfmp\\"+packageName.replaceAll("\\.","\\\\")+"\\"+table.getName()+"List.shtml");
    }
}
