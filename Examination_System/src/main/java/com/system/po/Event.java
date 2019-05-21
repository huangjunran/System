package com.system.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Event {
    private String eventid;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date starttime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endtime;

    private String title;

    private Integer executed;

    public String geteventid() {
        return eventid;
    }

    public void seteventid(String eventid) {
        this.eventid = eventid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExecuted() {
        return executed;
    }

    public void setExecuted(Integer executed) {
        this.executed = executed;
    }
}