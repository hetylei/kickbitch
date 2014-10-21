package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("PublicLog")
public class PublicLog implements Serializable{
    /**����*/
    @Column
    @Id
    private int id;
    /**����*/
    @Column
    private int taskId;
    /**����ʱ��*/
    @Column
    private Date taskTime;
    /**����ؼ���*/
    @Column
    private String taskKey;
    /**����url*/
    @Column
    private String taskUrl;
    /**������־*/
    @Column
    private String pLog;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public int getTaskId(){
        return taskId;
    }
    public void setTaskId(int value){
        this.taskId = value;
    }

    public Date getTaskTime(){
        return taskTime;
    }
    public void setTaskTime(Date value){
        this.taskTime = value;
    }

    public String getTaskKey(){
        return taskKey;
    }
    public void setTaskKey(String value){
        this.taskKey = value;
    }

    public String getTaskUrl(){
        return taskUrl;
    }
    public void setTaskUrl(String value){
        this.taskUrl = value;
    }

    public String getPLog(){
        return pLog;
    }
    public void setPLog(String value){
        this.pLog = value;
    }


}
