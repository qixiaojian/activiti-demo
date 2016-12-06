package com.activiti.model;

/**
 * Created by qixiaojian on 2016/12/2.
 */

import java.io.Serializable;
import java.util.Date;

public class Leave implements Serializable{

    private String name;
    private String content;
    private Date  createTime;
    private int days;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
