package com.github.JuanManuel.model.services;

import java.util.List;

public interface service<O> {
    boolean insert(O entity);

    boolean update(O entity);

    boolean delete(O entity);

    O findByPK(O pk);

    List<O> findAll();

    boolean validate(O entity);

}
