package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Categoria;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Categoria entity.
 * Provides methods to perform CRUD operations on the Categoria table in the database.
 */
public class categoriaDAO implements DAO<Categoria> {

    /**
     * Inserts a new Categoria entity into the database.
     *
     * @param entity the Categoria entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Categoria entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Updates an existing Categoria entity in the database.
     *
     * @param entity the Categoria entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Categoria entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Deletes an existing Categoria entity from the database.
     *
     * @param entity the Categoria entity to be deleted.
     * @return true if the deletion was successful.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public boolean delete(Categoria entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Finds a Categoria entity by its primary key.
     *
     * @param pk the primary key of the Categoria entity to be found.
     * @return the found Categoria entity, or null if not found.
     */
    @Override
    public Categoria findByPK(Categoria pk) {
        Session sn = sessionFactory.openSession();
        Categoria result = new Categoria();
        sn.beginTransaction();
        result = sn.get(Categoria.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return result;
    }

    /**
     * Finds all Categoria entities in the database.
     *
     * @return a list of all Categoria entities.
     */
    @Override
    public List<Categoria> findAll() {
        Session sn = sessionFactory.openSession();
        List<Categoria> ls = new ArrayList<>();
        Query<Categoria> all = sn.createQuery("from Categoria", Categoria.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds a Categoria entity by its name.
     *
     * @param entity the Categoria entity with the name to be searched.
     * @return the found Categoria entity, or null if not found.
     */
    public Categoria findByName(Categoria entity) {
        Session sn = sessionFactory.openSession();
        Categoria result = null;
        sn.beginTransaction();
        Query<Categoria> query = sn.createQuery("from Categoria where nombre = :nombre", Categoria.class);
        query.setParameter("nombre", entity.getNombre());
        result = query.uniqueResult();
        sn.getTransaction().commit();
        sn.close();
        return result;
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
     * Builds a new instance of categoriaDAO.
     *
     * @return a new instance of categoriaDAO.
     */
    public static categoriaDAO build() {
        return new categoriaDAO();
    }
}