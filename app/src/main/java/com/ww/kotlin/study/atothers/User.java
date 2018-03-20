package com.ww.kotlin.study.atothers;

/**
 * Author:      WW
 * Date:        2018/3/19 17:05
 * Description: This is User
 */

public class User {
    String userName;
    String userId;

    public User() {
    }

    public User( String userId,String userName) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
