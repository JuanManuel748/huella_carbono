package com.github.JuanManuel.model.entities;

public class Session {
    private static Session _instance;
    private static Usuario currentUser;

    private Session() {}

    public static Session getInstance() {
        if(_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }

    public Usuario getCurrentUser() {return currentUser;}

    public void logIn(Usuario usr) {
        currentUser = usr;
    }

    public void logOut(Usuario usr) {
        currentUser = null;
    }
}
