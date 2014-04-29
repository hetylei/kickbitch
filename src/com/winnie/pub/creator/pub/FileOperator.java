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
     * 以utf-8编码读取一个文件到字符串
     * @param fileName  文件名
     * @return 文件字符串
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
     * 获取%SourceBase%\config \template \ %templateName% 模板 (gb2312) 到字符串
     * @param SourceBase    本地源代码路径
     * @param templateName  模板名称
     * @return  文件字符串
     */
    public static StringBuffer getTemplate(String SourceBase, String templateName, String encode) {
        return getFile(SourceBase+"\\codeCreatorTemplate\\"+templateName, encode);
    }

    /**
     * 以utf-8 编码保存一个字符串到新文件
     * @param filebuf  文件字符串
     * @param fileName 文件名
     * @return  是否保存成功
     */
    public static boolean saveToFile(String filebuf, String fileName, String encode) {
        String filepath = fileName.substring(0, fileName.lastIndexOf("\\"));
        //创建目录
        File dirs = new File(filepath);
        if (!dirs.exists()) dirs.mkdirs();
        //创建文件
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
                System.out.println("[FileOpertor]"+fileName+"已经存在，文件创建跳过。");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[FileOpertor]"+fileName+" 创建失败。");
            return false;
        }

    }

    /**
     * 以utf-8 编码保存一个字符串到文件
     * @param filebuf  文件字符串
     * @param fileName 文件名
     * @return  是否保存成功
     */
    public static boolean reWriteFile(String filebuf, String fileName, String enocde) {
        String filepath = fileName.substring(0, fileName.lastIndexOf("\\"));
        //创建目录
        File dirs = new File(filepath);
        if (!dirs.exists()) dirs.mkdirs();
        //创建文件
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
            System.out.println("[FileOpertor]"+fileName+" 创建失败。");
            return false;
        }

    }


    /**
     * 添加 ioc 方法 （com\winnie\pub\IocFactory.java）
     * @param sourceBase 源代码根路径
     * @param entityName 表名
     * @param packageName 包名
     * @return 是否添加成功
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
            System.out.println("[FileOperator]IocFactory方法已创建");
            return true;
        } else {
            System.out.println("[FileOperator]IocFactory方法("+"get"+entityName+"Dao)已存在。");
            return false;    
        }

    }

    /**
     * 添加dao.js 配置 (\conf\classes\dao.js)
     * @param sourceBase 源代码根路径
     * @param entityName 表名
     * @param packageName 包名
     * @return 是否添加成功
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
            System.out.println("[FileOperator]dao.js 配置已创建");
            return true;
        } else {
            System.out.println("[FileOperator]dao.js 配置("+entityName+"JdbcDao)已存在。");
            return false;
        }
    }


    /**
     * 删除 com.yxsoft.pub.YXDaoFactory 方法(com\yxsoft\pub\YXDaoFactory.java）
     * @param sourceBase 源代码根路径
     * @param entityName 表名
     * @param packageName 包名
     * @return 是否删除成功
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
            System.out.println("[FileOperator]DaoFactory方法已删除");
        } 

        return true;
    }

    /**
     * 将代码追加到文件末尾 
     * @param fileName      文件
     * @param source        追加代码
     * @param lastString    追加到此结束符之前
     * @return              是否追加成功 
     */
    public static boolean appendToFileEnd(String fileName, String source, String lastString, String encode){
        StringBuffer classFile = getFile(fileName, encode);

        if (classFile.indexOf(source)==-1) {
            //add 
            classFile.insert(classFile.lastIndexOf(lastString)-1, "\n"+source+"\n");

            reWriteFile(classFile.toString(), fileName, encode);
            System.out.println("[FileOperator]方法已添加:"+fileName);
            return true;
        } else {
            System.out.println("[FileOperator]方法已存在:"+fileName);
            return false;
        }
    }

    /**
     * 文件是否存在
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String fileName) {
        return new File(fileName).exists();
    }
}
