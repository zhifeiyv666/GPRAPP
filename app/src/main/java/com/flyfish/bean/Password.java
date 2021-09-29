package com.flyfish.bean;

public class Password {

    private String value;
    private long createTimeStamp;

    public Password() {}

    public Password(String value, long createTimeStamp) {
        this.value = value;
        this.createTimeStamp = createTimeStamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }
}
