package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Usuario entity.
 * Provides methods to perform CRUD operations on the Usuario table in the database.
 */
public class usuarioDAO implements DAO<Usuario> {

    /**
     * Inserts a new Usuario entity into the database.
     *
     * @param entity the Usuario entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Usuario entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Updates an existing Usuario entity in the database.
     *
     * @param entity the Usuario entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Usuario entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Deletes an existing Usuario entity from the database.
     *
     * @param entity the Usuario entity to be deleted.
     * @return true if the deletion was successful.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public boolean delete(Usuario entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Finds a Usuario entity by its primary key.
     *
     * @param pk the primary key of the Usuario entity to be found.
     * @return the found Usuario entity, or null if not found.
     */
    @Override
    public Usuario findByPK(Usuario pk) {
        Session sn = sessionFactory.openSession();
        Usuario usr = new Usuario();
        sn.beginTransaction();
        usr = sn.get(Usuario.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return usr;
    }

    /**
     * Finds all Usuario entities in the database.
     *
     * @return a list of all Usuario entities.
     */
    @Override
    public List<Usuario> findAll() {
        Session sn = sessionFactory.openSession();
        List<Usuario> ls = new ArrayList<>();
        Query<Usuario> all = sn.createQuery("from Usuario", Usuario.class);
        ls = all.list();
        return ls;
    }

    /**
     * Finds a Usuario entity by its email.
     *
     * @param email the email of the Usuario entity to be found.
     * @return the found Usuario entity, or null if not found.
     */
    public Usuario findByEmail(String email) {
        Session sn = sessionFactory.openSession();
        Usuario usr = new Usuario();
        sn.beginTransaction();
        Query<Usuario> all = sn.createQuery("from Usuario where email = :email", Usuario.class);
        all.setParameter("email", email);
        usr = all.uniqueResult();
        sn.getTransaction().commit();
        sn.close();
        return usr;
    }

    /**
     * Closes any resources held by this DAO.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        // No resources to close in this implementation.
    }

    /**
     * Builds a new instance of usuarioDAO.
     *
     * @return a new instance of usuarioDAO.
     */
    public static usuarioDAO build() {
        return new usuarioDAO();
    }
}