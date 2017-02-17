package com.example.chenxuanhe.myapplication.utils;

/**
 * Created by ChenXuanHe on 2017/2/17.
 */

public class MarkUtil {
    public String course;
    public String mark;
    public String mode;
    public String credit;

    public String message;


    public MarkUtil(String message){
        this.message = message;
    }

    public MarkUtil(String course, String mark, String mode, String credit) {
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
