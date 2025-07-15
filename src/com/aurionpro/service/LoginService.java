package com.aurionpro.service;

import java.util.List;
import com.aurionpro.user.User;

public class LoginService {

    private final List<User> users;

    public LoginService(List<User> users) {
        this.users = users;
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}

