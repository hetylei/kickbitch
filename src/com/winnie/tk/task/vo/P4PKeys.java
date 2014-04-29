package com.winnie.tk.task.vo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.nutz.dao.entity.annotation.*;

/**
 * Created by VoCreator (vo.jt)
 */
@Table("P4PKeys")
public class P4PKeys implements Serializable{
    /**主键*/
    @Column
    @Id
    private int id;
    /**直通车主图关键词*/
    @Column
    private String p4pids;


    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public String getP4pids(){
        return p4pids;
    }
    public void setP4pids(String value){
        this.p4pids = value;
    }


}
