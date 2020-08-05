package com.thoughtworks.rslist.domain;

import com.thoughtworks.rslist.domain.UsersDetail;

public class RsEvent {
    private String eventName;
    private String keyword;
    private UsersDetail user;

    public RsEvent() {
    }

    public RsEvent(String eventName, String keyword,UsersDetail user) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.user=user;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public UsersDetail getUser() { return user; }

    public void setUser(UsersDetail user) { this.user = user; }
}