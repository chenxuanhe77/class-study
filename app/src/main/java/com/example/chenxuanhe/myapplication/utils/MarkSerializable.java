package com.example.chenxuanhe.myapplication.utils;

import java.io.Serializable;

/**
 * Created by ChenXuanHe on 2017/2/17.
 */

public class MarkSerializable implements Serializable {

    private String course;
    private String mark;
    private String mode;
    private String credit;

    public MarkSerializable(){
        super();
    }

    public MarkSerializable(String course,String mark,String mode,String credit){
        this.course = course;
        this.mark = mark;
        this.mode = mode;
        this.credit = credit;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }


}
