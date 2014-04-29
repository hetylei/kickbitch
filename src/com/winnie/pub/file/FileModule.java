package com.winnie.pub.file;

import com.winnie.pub.IocFactory;
import com.winnie.pub.utils.KeyCreator;
import com.winnie.pub.utils.SessionHelper;
import com.winnie.pub.file.vo.UploadFile;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-10-28
 * Time: ÏÂÎç3:01
 * To change this template use File | Settings | File Templates.
 */
public class FileModule {
    Logger logger = Logger.getLogger(FileModule.class);

    @At("/file/upload/?/?/?")
    @Ok("fm:/member/file/edit.ftl")
    public Map<String, Object> doUpload(String bizType, String bizSn, String bizSign) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("bizType", bizType);
        obj.put("bizSn", bizSn);
        obj.put("bizSign", bizSign);
        return obj;
    }

    @At("/file/save")
    @Ok("fm:/member/file/edit.ftl")
    @AdaptBy(type = UploadAdaptor.class, args = { "ioc:tmpFileUpload" })
    public Map<String, Object> doSave(@Param("bizType") String bizType,
            @Param("bizSn") String bizSn, @Param("bizSign") String bizSign, @Param("upfile") TempFile[] tfs,
            HttpSession session) {
        List<UploadFile> uploadFileList = new ArrayList<UploadFile>();
        if (tfs != null) {
            for (TempFile tf : tfs) {
                UploadFile uf = new UploadFile();
                uf.setSn(KeyCreator.getUuid());
                uf.setBizSign(bizSign);
                uf.setBizSn(bizSn);
                uf.setBizType(bizType);
                uf.setCreateTime(Calendar.getInstance().getTime());
                //uf.setCreateUser(SessionHelper.getUserSn(session));
                uf.setFileName(tf.getMeta().getFileLocalName());
                uf.setFilePath(tf.getFile().getPath());
                uf.setFileSize(tf.getFile().length());
                uf.setContentType(tf.getMeta().getContentType());

                uploadFileList.add(uf);
                IocFactory.getFileDao().insertUploadFile(uf);
            }
        }
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("saved", "ok");
        obj.put("result", Json.toJson(uploadFileList));
        return obj;
    }

    @At("/file/download/?")
    public void doDownload(String fileSn, HttpServletRequest request,
                                          HttpServletResponse response, HttpSession session) {
        try {
            request.setCharacterEncoding("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        UploadFile uf = IocFactory.getFileDao().getUploadFileByPrimaryKey(fileSn);


        if (uf.getContentType()== null || uf.getContentType().equals(""))
            response.setContentType("application/x-msdownload");
        else
            response.setContentType(uf.getContentType());

        response.setContentLength((int) uf.getFileSize());


        if (uf.getFileName() == null || uf.getFileName().length() == 0)
            response.setHeader("Content-Disposition", "attachment;");
        else {

            try {
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(uf.getFileName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


        OutputStream out = null;
        FileInputStream fis = null;
        try {
            out = response.getOutputStream();
            byte[] b = new byte[1024];
            File file = new File(uf.getFilePath());
            fis = new FileInputStream(file);
            int i = fis.read(b);
            while (i != -1){
                out.write(b, 0, i);
                i = fis.read(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } 
        
    }

    @At("/file/photoview/?")
    @Ok("fm:/member/file/photoview.ftl")
    public Map<String, Object> doPhotoView(String sn){
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("sn", sn);
        return obj;
    }

    @At("/file/del/?")
    public void doDownload(String fileSn, HttpServletResponse response) throws IOException {
        IocFactory.getFileDao().deleteUploadFileByPrimaryKey(fileSn);
        response.getWriter().write("ok");
    }
}
