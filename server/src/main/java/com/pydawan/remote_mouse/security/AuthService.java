package com.pydawan.remote_mouse.security;

public class AuthService {
    // Randomly generated
    private final String PASSWORD = "o?Rhtb5jYLrB$NjQ";

    public boolean authenticate(String password) {
        return PASSWORD.equals(password);
    }
}
