package com.github.JuanManuel.model.entity;

public class Session {
    private static Session _instance;

    private Session() {}

    public static Session getInstance() {
        if(_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }


}
