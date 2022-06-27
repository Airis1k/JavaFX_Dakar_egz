package com.example.javafx_dakar.model;

public class UserSingleton {
    private static UserSingleton instance;

    private String username;

    private UserSingleton() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }

        return instance;
    }
}
