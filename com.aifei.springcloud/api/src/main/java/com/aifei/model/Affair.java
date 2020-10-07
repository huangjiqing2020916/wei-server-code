package com.aifei.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
@TableName("affair")
public class Affair implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String name;

    @Override
    public String toString() {
        return "Affair{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", time=" + time +
                '}';
    }

    private String pwd;

    private Date time;
}
