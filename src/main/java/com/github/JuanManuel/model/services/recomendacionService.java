package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.entities.Recomendacion;

import java.util.List;

public class recomendacionService implements  service<Recomendacion> {
    @Override
    public boolean insert(Recomendacion entity) {
        return false;
    }

    @Override
    public boolean update(Recomendacion entity) {
        return false;
    }

    @Override
    public boolean delete(Recomendacion entity) {
        return false;
    }

    @Override
    public Recomendacion findByPK(Recomendacion pk) {
        return null;
    }

    @Override
    public List<Recomendacion> findAll() {
        return List.of();
    }

    @Override
    public boolean validate(Recomendacion entity) {
        return false;
    }
}
