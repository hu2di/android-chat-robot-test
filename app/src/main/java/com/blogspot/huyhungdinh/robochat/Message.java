package com.blogspot.huyhungdinh.robochat;

/**
 * Created by HUNGDH on 12/22/2015.
 */
public class Message {

    private String username;
    private String message;
    private boolean mine;

    public Message(String username, String message, boolean mine) {
        this.username = username;
        this.message = message;
        this.mine = mine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    @Override
    public String toString() {
        return "{ " + "\"username\": \"" + username + "\", \"message\": \"" + message + "\" }";
    }
}
