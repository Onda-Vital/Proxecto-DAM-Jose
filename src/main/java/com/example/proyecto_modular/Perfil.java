package com.example.proyecto_modular;

public class Perfil {
    private long id;
    private String username;
    private String email;
    private String displayName;

    public Perfil(long id, String username, String email, String displayName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}