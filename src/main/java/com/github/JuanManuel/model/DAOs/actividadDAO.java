package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Actividad entity.
 * Provides methods to perform CRUD operations on the Actividad table in the database.
 */
public class actividadDAO implements DAO<Actividad> {

    /**
     * Inserts a new Actividad entity into the database.
     *
     * @param entity the Actividad entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Actividad entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Updates an existing Actividad entity in the database.
     *
     * @param entity the Actividad entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Actividad entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Deletes an existing Actividad entity from the database.
     *
     * @param entity the Actividad entity to be deleted.
     * @return true if the deletion was successful.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public boolean delete(Actividad entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Finds an Actividad entity by its primary key.
     *
     * @param pk the primary key of the Actividad entity to be found.
     * @return the found Actividad entity, or null if not found.
     */
    @Override
    public Actividad findByPK(Actividad pk) {
        Session sn = sessionFactory.openSession();
        Actividad result = new Actividad();
        sn.beginTransaction();
        result = sn.get(Actividad.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return result;
    }

    /**
     * Finds all Actividad entities in the database.
     *
     * @return a list of all Actividad entities.
     */
    @Override
    public List<Actividad> findAll() {
        Session sn = sessionFactory.openSession();
        List<Actividad> ls = new ArrayList<>();
        Query<Actividad> all = sn.createQuery("from Actividad", Actividad.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds an Actividad entity by its name.
     *
     * @param entity the Actividad entity with the name to be searched.
     * @return the found Actividad entity, or null if not found.
     */
    public Actividad findByName(Actividad entity) {
        Session sn = sessionFactory.openSession();
        Actividad result = new Actividad();
        sn.beginTransaction();
        Query<Actividad> query = sn.createQuery("from Actividad where nombre = :nombre", Actividad.class);
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
     * Builds a new instance of actividadDAO.
     *
     * @return a new instance of actividadDAO.
     */
    public static actividadDAO build() {
        return new actividadDAO();
    }
}