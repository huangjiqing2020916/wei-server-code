package com.aifei.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("cloud")
public class Cloud implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String name;

    public Cloud(String name, String pwd, Integer number) {
        this.name = name;
        this.pwd = pwd;
        this.number = number;
    }


    public Cloud() {
    }

    @Override
    public String toString() {
        return "Cloud{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", number='" + number + '\'' +
                ", time=" + time +
                '}';
    }

    private String pwd;

    private Date time;

    private Integer number;
}
