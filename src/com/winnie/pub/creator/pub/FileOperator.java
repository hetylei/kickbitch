package com.winnie.pub.creator.pub;


import java.io.*;



/**
 * Created by IntelliJ IDEA.
 * User: cl
 * Date: 2008-7-17
 * Time: 15:59:26
 * To change this template use File | Settings | File Templates.
 */
public class FileOperator {
    /**
     * ��utf-8�����ȡһ���ļ����ַ���
     * @param fileName  �ļ���
     * @return �ļ��ַ���
     */
    public static StringBuffer getFile(String fileName, String encode) {
        StringBuffer result = new StringBuffer();
        try {
            File template = new File(fileName);
            InputStreamReader reader=new InputStreamReader(new FileInputStream(template), encode);
            BufferedReader bufReader=new BufferedReader(reader);
            String line = null;
            while ((line = bufReader.readLine())!=null) {
                result.append(line).append("\n");
            }
            bufReader.close();
            reader.close();
            return result;
        } catch (IOException e) {
            //e.printStackTrace();  
            return null;
        }
    }

    /**
     * ��ȡ%SourceBase%\config \template \ %templateName% ģ�� (gb2312) ���ַ���
     * @param SourceBase    ����Դ����·��
     * @param templateName  ģ������
     * @return  �ļ��ַ���
     */
    public static StringBuffer getTemplate(String SourceBase, String templateName, String encode) {
        return getFile(SourceBase+"\\codeCreatorTemplate\\"+templateName, encode);
    }

    /**
     * ��utf-8 ���뱣��һ���ַ��������ļ�
     * @param filebuf  �ļ��ַ���
     * @param fileName �ļ���
     * @return  �Ƿ񱣴�ɹ�
     */
    public static boolean saveToFile(String filebuf, String fileName, String encode) {
        String filepath = fileName.substring(0, fileName.lastIndexOf("\\"));
        //����Ŀ¼
        File dirs = new File(filepath);
        if (!dirs.exists()) dirs.mkdirs();
        //�����ļ�
        File createFile = new File(fileName);
        try {
            if (!createFile.exists()) {
                createFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(createFile);
                Writer out = new OutputStreamWriter(fos, encode);
                out.write(filebuf);
                out.close();
                fos.close();
                return true;
            } else {
                System.out.println("[FileOpertor]"+fileName+"�Ѿ����ڣ��ļ�����������");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[FileOpertor]"+fileName+" ����ʧ�ܡ�");
            return false;
        }

    }

    /**
     * ��utf-8 ���뱣��һ���ַ������ļ�
     * @param filebuf  �ļ��ַ���
     * @param fileName �ļ���
     * @return  �Ƿ񱣴�ɹ�
     */
    public static boolean reWriteFile(String filebuf, String fileName, String enocde) {
        String filepath = fileName.substring(0, fileName.lastIndexOf("\\"));
        //����Ŀ¼
        File dirs = new File(filepath);
        if (!dirs.exists()) dirs.mkdirs();
        //�����ļ�
        File createFile = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(createFile);
            Writer out = new OutputStreamWriter(fos, enocde);
            out.write(new String(filebuf.getBytes(),enocde));
            out.close();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[FileOpertor]"+fileName+" ����ʧ�ܡ�");
            return false;
        }

    }


    /**
     * ��� ioc ���� ��com\winnie\pub\IocFactory.java��
     * @param sourceBase Դ�����·��
     * @param entityName ����
     * @param packageName ����
     * @return �Ƿ���ӳɹ�
     */
    public static boolean addIocFactoryMethod(String sourceBase, String entityName, String packageName){
        String fileName = sourceBase +"\\src\\com\\winnie\\pub\\IocFactory.java";
        StringBuffer daoFactory = getFile(fileName, "gb2312");

        if (daoFactory.indexOf("get"+entityName+"Dao")==-1) {
            //add import
            daoFactory.insert(daoFactory.indexOf(";",daoFactory.lastIndexOf("import"))+1 ,
                              "\nimport "+packageName+".dao."+entityName+"Dao;");

            //add method
            daoFactory.insert(daoFactory.lastIndexOf("}")-1,
                              "\n\tpublic static "+entityName+"Dao get"+entityName+"Dao() {\n"+
                              "\t\t//Created by FileOperator addIocFactoryMethod \n"+
                              "\t\treturn ioc.get(" + entityName + "Dao.class, \"" + entityName + "Dao\");\n"+
                              "\t}\n");

            reWriteFile(daoFactory.toString(), fileName, "gb2312");
            System.out.println("[FileOperator]IocFactory�����Ѵ���");
            return true;
        } else {
            System.out.println("[FileOperator]IocFactory����("+"get"+entityName+"Dao)�Ѵ��ڡ�");
            return false;    
        }

    }

    /**
     * ���dao.js ���� (\conf\classes\dao.js)
     * @param sourceBase Դ�����·��
     * @param entityName ����
     * @param packageName ����
     * @return �Ƿ���ӳɹ�
     */
    public static boolean addDaoConfig(String sourceBase, String entityName, String packageName){
        String daojsFile = sourceBase +"\\conf\\classes\\dao.js";

        StringBuffer daojs = getFile(daojsFile, "GBK");

        if (daojs.indexOf(packageName + ".dao." + entityName + "JdbcDao")==-1) {
            //add method
            daojs.insert(daojs.lastIndexOf("//")-1,
                    "\n    ," + entityName +"Dao : { type : \"" + packageName + ".dao." + entityName + "JdbcDao\", args : [\n" +
                            "        { refer : \"dao\" }\n" +
                            "    ] }\n");

            reWriteFile(daojs.toString(), daojsFile, "GBK");
            System.out.println("[FileOperator]dao.js �����Ѵ���");
            return true;
        } else {
            System.out.println("[FileOperator]dao.js ����("+entityName+"JdbcDao)�Ѵ��ڡ�");
            return false;
        }
    }


    /**
     * ɾ�� com.yxsoft.pub.YXDaoFactory ����(com\yxsoft\pub\YXDaoFactory.java��
     * @param sourceBase Դ�����·��
     * @param entityName ����
     * @param packageName ����
     * @return �Ƿ�ɾ���ɹ�
     */
    public static boolean delYXDaoFactoryMethod(String sourceBase, String entityName, String packageName){
        String fileName = sourceBase +"\\src\\com\\yxsoft\\pub\\YXDaoFactory.java";
        StringBuffer daoFactory = getFile(fileName, "gb2312");

        if (daoFactory.indexOf("get"+entityName+"Dao")!=-1) {
            //del import
            daoFactory.delete(daoFactory.indexOf("import "+packageName+".dao."+entityName+"Dao;") ,
                              daoFactory.indexOf(entityName+"Dao;")+1+entityName.length()+4);

            //del method
            daoFactory.delete(daoFactory.indexOf("public static "+entityName+"Dao"),
                              daoFactory.indexOf("}", daoFactory.indexOf("public static "+entityName+"Dao"))+1);

            reWriteFile(daoFactory.toString(), fileName, "gb2312");
            System.out.println("[FileOperator]DaoFactory������ɾ��");
        } 

        return true;
    }

    /**
     * ������׷�ӵ��ļ�ĩβ 
     * @param fileName      �ļ�
     * @param source        ׷�Ӵ���
     * @param lastString    ׷�ӵ��˽�����֮ǰ
     * @return              �Ƿ�׷�ӳɹ� 
     */
    public static boolean appendToFileEnd(String fileName, String source, String lastString, String encode){
        StringBuffer classFile = getFile(fileName, encode);

        if (classFile.indexOf(source)==-1) {
            //add 
            classFile.insert(classFile.lastIndexOf(lastString)-1, "\n"+source+"\n");

            reWriteFile(classFile.toString(), fileName, encode);
            System.out.println("[FileOperator]���������:"+fileName);
            return true;
        } else {
            System.out.println("[FileOperator]�����Ѵ���:"+fileName);
            return false;
        }
    }

    /**
     * �ļ��Ƿ����
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String fileName) {
        return new File(fileName).exists();
    }
}
