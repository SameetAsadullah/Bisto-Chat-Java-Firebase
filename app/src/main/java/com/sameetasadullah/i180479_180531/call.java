package com.sameetasadullah.i180479_180531;

public class call {
    private String name, status, time;
    private Boolean received;

    public call(String name, String status, String time, Boolean received) {
        this.name = name;
        this.status = status;
        this.time = time;
        this.received = received;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }
}
