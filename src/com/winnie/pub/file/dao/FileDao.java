package com.winnie.pub.file.dao;

import com.winnie.pub.file.vo.*;

import java.util.Map;
import java.util.List;

/**
 * Created by DaoCreator (daoInterface.jt)
 */
public interface FileDao {
//#appendbegin
    //==============UploadFile==============
    public void insertUploadFile(UploadFile vo);
    public void updateUploadFile(UploadFile vo);

    public void deleteUploadFile(UploadFile vo);
    public void deleteUploadFileByPrimaryKey(String sn );
    public void deleteUploadFileByParam(Object... param);
    public void deleteUploadFileByParam(Map<String, Object> param);

    public UploadFile getUploadFileByPrimaryKey(String sn );
    
    public int getUploadFileCountByPrimaryKey(String sn );
    public int getUploadFileCountByParam(Object... param);
    public int getUploadFileCountByParam(Map<String, Object> param);

    public List<UploadFile> getUploadFileListByParam(String orderBy, Object... param);
    public List<UploadFile> getUploadFileListByParam(int page, int count, String orderBy, Object... param);
    public List<UploadFile> getUploadFileListByParam(String orderBy, Map<String, Object> param);
    public List<UploadFile> getUploadFileListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<UploadFile> getAllUploadFileList(String orderBy);
    //==============end of UploadFile==============	
//#appendend   
}
