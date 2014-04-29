package com.winnie.test.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("Computer")
public class Computer implements Serializable{
    /**主键*/
    @Column
    @Id
    private int id;
    /**cpu型号*/
    @Column
    private String cpu;
    /**内存型号*/
    @Column
    private String memory;
    /**硬盘型号*/
    @Column
    private String hardDisk;
    /**键盘型号*/
    @Column
    private String keyBoard;
    /**鼠标型号*/
    @Column
    private String mouse;
    /**cpu核心数量*/
    @Column
    private int cpuCoreCount;
    /**组装时间*/
    @Column
    private Date madeDate;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public String getCpu(){
        return cpu;
    }
    public void setCpu(String value){
        this.cpu = value;
    }

    public String getMemory(){
        return memory;
    }
    public void setMemory(String value){
        this.memory = value;
    }

    public String getHardDisk(){
        return hardDisk;
    }
    public void setHardDisk(String value){
        this.hardDisk = value;
    }

    public String getKeyBoard(){
        return keyBoard;
    }
    public void setKeyBoard(String value){
        this.keyBoard = value;
    }

    public String getMouse(){
        return mouse;
    }
    public void setMouse(String value){
        this.mouse = value;
    }

    public int getCpuCoreCount(){
        return cpuCoreCount;
    }
    public void setCpuCoreCount(int value){
        this.cpuCoreCount = value;
    }

    public Date getMadeDate(){
        return madeDate;
    }
    public void setMadeDate(Date value){
        this.madeDate = value;
    }


}
