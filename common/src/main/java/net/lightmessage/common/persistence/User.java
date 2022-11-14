package net.lightmessage.common.persistence;

public class User {
    String username;

    public User(String username) {
        this.username = username;
    }

    public User(User other) {
        this.username = other.username;
    }

    public String getUsername() {
        return username;
    }
}
