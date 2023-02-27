package com.cinema.user;

public class UserBuilder {
    int id;
    String username;
    String password;
    Role userRole;

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setUserRole(Role userRole) {
        this.userRole = userRole;
        return this;
    }

    public User build() {
        return new User(id, username, password, userRole);
    }
}