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
    /**����*/
    @Column
    @Id
    private int id;
    /**cpu�ͺ�*/
    @Column
    private String cpu;
    /**�ڴ��ͺ�*/
    @Column
    private String memory;
    /**Ӳ���ͺ�*/
    @Column
    private String hardDisk;
    /**�����ͺ�*/
    @Column
    private String keyBoard;
    /**����ͺ�*/
    @Column
    private String mouse;
    /**cpu��������*/
    @Column
    private int cpuCoreCount;
    /**��װʱ��*/
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
