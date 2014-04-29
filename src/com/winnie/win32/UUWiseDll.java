package com.winnie.win32;

import com.sun.jna.Library;
import com.sun.jna.Native;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: winnie
 * Date: 14-2-3
 * Time: ����6:55
 * To change this template use File | Settings | File Templates.
 */
public class UUWiseDll {
    public static String	USERNAME	= "hi51zz2";							//UU�û���
    public static String	PASSWORD	= "hi51uuwise";							//UU����
    public static String	DLLPATH		= "C:\\UUWiseHelper";					//DLL
    public static String	IMGPATH		= "c:\\test.jpg";
    public static int		SOFTID		= 95410;								//���ID
    public static String	SOFTKEY		= "fec954040ca8484a9a1383559c4d346c";	//���KEY
    public static String	UUDLLMD5	="8DDF5BA7DE4ECAEA1DFACBEC098D7EF3";	//������DLL MD5,����������У��MD5�õ��Ĳ���

    /*	������DLL �ļ�MD5ֵУ��
      *  �ô��������в������Ӳ����滻�����ƹٷ�dll�ļ��ķ�ʽ��������ƻ��˿����ߵ�����
      *  �û�ʹ���滻����DLL���룬���¿����߷ֳɱ�ɱ��˵ģ���������
      *  ���Խ������п������������������У��ٷ�MD5ֵ�ĺ���
      *  ��λ�ȡ�ļ���MD5ֵ��ͨ�������GetFileMD5(�ļ�)�����������ļ�MD5
      */
    //MD5У�麯����ʼ

    public static String GetFileMD5(String inputFile) throws IOException {
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
            byte[] buffer =new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0);
            messageDigest= digestInputStream.getMessageDigest();
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }finally {
            try {
                digestInputStream.close();
            }catch (Exception e) {

            }try {
                fileInputStream.close();
            }catch (Exception e) {

            }
        }
    }
    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        return new String(resultCharArray);
    }

    //MD5У�麯������

    public interface DM extends Library
    {
        DM	INSTANCE	= (DM) Native.loadLibrary(DLLPATH, DM.class);
        public int uu_reportError(int id);
        public int uu_setTimeOut(int nTimeOut);
        public void uu_setSoftInfoA(int softId, String softKey);
        public int uu_loginA(String UserName, String passWord);
        public int uu_getScoreA (String UserName, String passWord);
        public int uu_recognizeByCodeTypeAndBytesA (byte[] picContent, int piclen, int codeType, byte[] returnResult);
        public void uu_getResultA(int nCodeID,String pCodeResult);
    }


    public static void main(String[] args) throws Exception
    {
        /*	������DLL �ļ�MD5ֵУ��
           *  �ô��������в������Ӳ����滻�����ƹٷ�dll�ļ��ķ�ʽ��������ƻ��˿����ߵ�����
           *  �û�ʹ���滻����DLL���룬���¿����߷ֳɱ�ɱ��˵ģ���������
           *  ���Խ������п������������������У��ٷ�MD5ֵ�ĺ���
           *  ��λ�ȡ�ļ���MD5ֵ��ͨ�������GetFileMD5(�ļ�)�����������ļ�MD5
           */

        /*
        //У��DLL�ļ���MD5ֵ�Ƿ���ȷ
        String md5=GetFileMD5(DLLPATH);	//��ȡDLL�ļ���MD5ֵ�����ο���ʹ�ô˺����õ�MD5��Ȼ��ŵ�������
        System.out.println(md5);	//��ʾMD5ֵ,��һ��������dll�����ô˺�����ȡDLL��MD5ֵ��

        if(!md5.equalsIgnoreCase(UUDLLMD5)){	//�ж�DLL�ļ��Ƿ��滻
            System.out.println("�Բ������滻��ͼƬʶ���ļ��������عٷ�ԭ�档");
            System.exit(0);	//�˳�����
        }
        */
        //���ϴ���ֻ���˼򵥴��������ӵĴ�����Ҫ��������һ�´���

        int userID;
        DM.INSTANCE.uu_setSoftInfoA(SOFTID, SOFTKEY);	//setsoftinfo��login����ֻ��Ҫִ��һ�Σ��Ϳ�������ִ��ͼƬʶ������

        userID=DM.INSTANCE.uu_loginA(USERNAME, PASSWORD);
        if(userID>0){
            System.out.println("userID is:"+userID);
            System.out.println("user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));

            File f = new File(IMGPATH);
            byte[] by = toByteArray(f);

            byte[] resultBtye=new byte[30];		//Ϊʶ���������ڴ�ռ�
            int codeID=DM.INSTANCE.uu_recognizeByCodeTypeAndBytesA(by, by.length, 8003, resultBtye);	//����ʶ����,resultBtyeΪʶ����
            String  resultResult = new String(resultBtye,"UTF-8");
            resultResult=resultResult.trim();
            System.out.println("this img codeID:"+codeID);
            System.out.println("return recongize Result:"+resultResult);

            /*
                   //���Ա��� ��ʼ����ʵ��������������,��Ҫ��ʵ����֤���������£�ִ�б��������б���,���ⱨ��ᵼ�·��
                   // ��ô���֪���Ƿ����أ�
                   // һ����˵����룬��������������Ӧ����Ӧ��

                   System.out.println("����ǰ user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));
                   int reportErrorResult;
                   reportErrorResult=DM.INSTANCE.uu_reportError(codeID);
                   if(reportErrorResult==0)
                   {
                       System.out.println("����� user score is:"+DM.INSTANCE.uu_getScoreA(USERNAME, PASSWORD));
                   }else
                   {
                       System.out.println("����ʧ�ܣ�ԭ��δ֪");
                   }
                   //���Ա��� ��ʼ����ʵ��������������,��Ҫ��ʵ����֤���������£�ִ�б��������б���,���ⱨ��ᵼ�·��
               */
        }else{
            System.out.println("��¼ʧ�ܣ��������Ϊ��"+userID);	//����������Ӧdll.uuwise.com������ֵ�鿴
        }
    }


    public static byte[] toByteArray(File imageFile) throws Exception
    {
        BufferedImage img = ImageIO.read(imageFile);
        ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
        try
        {
            ImageIO.write(img, "jpg", buf);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return buf.toByteArray();
    }

    public static byte[] toByteArrayFromFile(String imageFile) throws Exception
    {
        InputStream is = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try
        {
            is = new FileInputStream(imageFile);

            byte[] b = new byte[1024];

            int n;

            while ((n = is.read(b)) != -1)
            {

                out.write(b, 0, n);

            }// end while

        } catch (Exception e)
        {
            throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
        } finally
        {

            if (is != null)
            {
                try
                {
                    is.close();
                } catch (Exception e)
                {}// end try
            }// end if

        }// end try
        return out.toByteArray();
    }


}
