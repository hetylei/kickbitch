package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("TaskLog")
public class TaskLog implements Serializable{
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
    /**��־*/
    @Column
    private String taskLog;


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

    public String getTaskLog(){
        return taskLog;
    }
    public void setTaskLog(String value){
        this.taskLog = value;
    }


}
