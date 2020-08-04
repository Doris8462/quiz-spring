package com.thoughtworks.rslist.domain;

public class RsEvent {
    private String eventName;
    private String keyWord;
    public RsEvent(String eventName,String keyWord){
        this.eventName=eventName;
        this.keyWord=keyWord;
    }
    public String getEventName(){
        return eventName;
    }
    public String getKeyWord(){
        return keyWord;
    }
    public void setEventName(){
        this.eventName=eventName;
    }
    public void setKeyWord(){
        this.keyWord=keyWord;
    }
}
