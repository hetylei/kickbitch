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
     * ���� ����FreeMarker �б�༭EHO��HTMLҳ��
     * @param table         bfmpbuilder table
     * @param sourceBase    ���ش����·��
     * @param packageName   �����ļ�λ�ڰ�
     * @param useEntityDao  <br>
     * �����Ϊnull �� ���� ʹ�� useEntityDao ��ָ����dao <br>
     * �� createWicketEntityEditor(t, "e:\bfmp", "com.yxsoft.tester.mail", "Mail");<br>
     * ������е��õ�dao Ϊ<br>
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

        System.out.println("freemarker ҳ�洴�����: ���ʵ�ַ bfmp\\"+packageName.replaceAll("\\.","\\\\")+"\\"+table.getName()+"List.shtml");
    }
}
