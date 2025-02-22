package com.github.JuanManuel.model.DAOs;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;
/**
 * Interface DAO
 * @param <O>
 */
public interface DAO<O> extends Closeable {
    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    boolean insert(O entity);

    boolean update(O entity);

    boolean delete(O entity) throws SQLException;

    O findByPK(O pk);

    List<O> findAll();


}