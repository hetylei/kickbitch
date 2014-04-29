package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("Task")
public class Task implements Serializable{
    /**主键*/
    @Column
    @Id
    private int id;
    /**任务名*/
    @Column
    private String taskName;
    /**启动星期*/
    @Column
    private String taskWeekDay;
    /**启动日期*/
    @Column
    private String taskDay;
    /**启动时间*/
    @Column
    private String taskTime;
    /**命令*/
    @Column
    private String taskCommand;
    /**是否启用*/
    @Column
    private int isUse;
    /**是否启用*/
    @Column
    private int isDelete;
    /**任务最后状态*/
    @Column
    private String lastState;
    /**任务主链接**/
    @Column
    private String mainUrl;

    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public String getTaskName(){
        return taskName;
    }
    public void setTaskName(String value){
        this.taskName = value;
    }

    public String getTaskWeekDay(){
        return taskWeekDay;
    }
    public void setTaskWeekDay(String value){
        this.taskWeekDay = value;
    }

    public String getTaskDay(){
        return taskDay;
    }
    public void setTaskDay(String value){
        this.taskDay = value;
    }

    public String getTaskTime(){
        return taskTime;
    }
    public void setTaskTime(String value){
        this.taskTime = value;
    }

    public String getTaskCommand(){
        return taskCommand;
    }
    public void setTaskCommand(String value){
        this.taskCommand = value;
    }

    public int getIsUse(){
        return isUse;
    }
    public void setIsUse(int value){
        this.isUse = value;
    }

    public int getIsDelete(){
        return isDelete;
    }
    public void setIsDelete(int value){
        this.isDelete = value;
    }

    public String getLastState() {
        return lastState;
    }

    public void setLastState(String lastState) {
        this.lastState = lastState;
    }


    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }
}
