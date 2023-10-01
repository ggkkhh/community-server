package com.roydon.business.chat.domain.dto;

import java.util.Set;

public class ChatUserList {

    private Set<ChatUser> users;

    public ChatUserList() {
    }

    public ChatUserList(Set<ChatUser> users) {
        this.users = users;
    }

    public Set<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(Set<ChatUser> users) {
        this.users = users;
    }
}
