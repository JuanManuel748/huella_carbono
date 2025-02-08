package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Recomendacion;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Recomendacion entity.
 * Provides methods to perform CRUD operations on the Recomendacion table in the database.
 */
public class recomendacionDAO implements DAO<Recomendacion> {

    /**
     * Inserts a new Recomendacion entity into the database.
     *
     * @param entity the Recomendacion entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Recomendacion entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Updates an existing Recomendacion entity in the database.
     *
     * @param entity the Recomendacion entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Recomendacion entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Deletes an existing Recomendacion entity from the database.
     *
     * @param entity the Recomendacion entity to be deleted.
     * @return true if the deletion was successful.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public boolean delete(Recomendacion entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Finds a Recomendacion entity by its primary key.
     *
     * @param pk the primary key of the Recomendacion entity to be found.
     * @return the found Recomendacion entity, or null if not found.
     */
    @Override
    public Recomendacion findByPK(Recomendacion pk) {
        Session sn = sessionFactory.openSession();
        Recomendacion result = new Recomendacion();
        sn.beginTransaction();
        result = sn.get(Recomendacion.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return result;
    }

    /**
     * Finds all Recomendacion entities in the database.
     *
     * @return a list of all Recomendacion entities.
     */
    @Override
    public List<Recomendacion> findAll() {
        Session sn = sessionFactory.openSession();
        List<Recomendacion> ls = new ArrayList<>();
        Query<Recomendacion> all = sn.createQuery("from Recomendacion", Recomendacion.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Recomendacion entities by the associated Categoria.
     *
     * @param cat the Categoria entity to search by.
     * @return a list of Recomendacion entities associated with the given Categoria.
     */
    public List<Recomendacion> findByCat(Categoria cat) {
        Session sn = sessionFactory.openSession();
        List<Recomendacion> ls = new ArrayList<>();
        Query<Recomendacion> all = sn.createQuery("from Recomendacion where idCategoria = :cat", Recomendacion.class);
        all.setParameter("cat", cat);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Recomendacion entities by their estimated impact range.
     *
     * @param min the minimum estimated impact.
     * @param max the maximum estimated impact.
     * @return a list of Recomendacion entities within the given impact range.
     */
    public List<Recomendacion> findByRange(Double min, Double max) {
        Session sn = sessionFactory.openSession();
        List<Recomendacion> ls = new ArrayList<>();
        Query<Recomendacion> all = sn.createQuery("from Recomendacion where impactoEstimado between :min and :max", Recomendacion.class);
        all.setParameter("min", min);
        all.setParameter("max", max);
        ls = all.list();
        sn.close();
        return ls;
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
     * Builds a new instance of recomendacionDAO.
     *
     * @return a new instance of recomendacionDAO.
     */
    public static recomendacionDAO build() {
        return new recomendacionDAO();
    }
}