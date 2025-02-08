package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Habito;
import com.github.JuanManuel.model.entities.Usuario;
import com.github.JuanManuel.model.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Habito entity.
 * Provides methods to perform CRUD operations on the Habito table in the database.
 */
public class habitoDAO implements DAO<Habito> {

    /**
     * Inserts a new Habito entity into the database.
     *
     * @param entity the Habito entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Habito entity) {
        boolean result = false;
        Session sn = sessionFactory.openSession();
        Transaction tx = sn.beginTransaction();
        try {
            if (entity.getIdActividad() != null) {
                entity.setIdActividad(sn.merge(entity.getIdActividad()));
            }
            if (entity.getIdUsuario() != null) {
                entity.setIdUsuario(sn.merge(entity.getIdUsuario()));
            }
            sn.persist(entity);
            tx.commit();
            result = true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            sn.close();
            return result;
        }
    }

    /**
     * Updates an existing Habito entity in the database.
     *
     * @param entity the Habito entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Habito entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Deletes an existing Habito entity from the database.
     *
     * @param entity the Habito entity to be deleted.
     * @return true if the deletion was successful.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public boolean delete(Habito entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Finds a Habito entity by its primary key.
     *
     * @param pk the primary key of the Habito entity to be found.
     * @return the found Habito entity, or null if not found.
     */
    @Override
    public Habito findByPK(Habito pk) {
        Session sn = sessionFactory.openSession();
        Habito result = new Habito();
        sn.beginTransaction();
        result = sn.get(Habito.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return result;
    }

    /**
     * Finds all Habito entities in the database.
     *
     * @return a list of all Habito entities.
     */
    @Override
    public List<Habito> findAll() {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito", Habito.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Habito entities by the associated Usuario.
     *
     * @param usr the Usuario entity to search by.
     * @return a list of Habito entities associated with the given Usuario.
     */
    public List<Habito> findByUser(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where idUsuario = :usr", Habito.class);
        all.setParameter("usr", usr);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Habito entities by the associated Actividad.
     *
     * @param act the Actividad entity to search by.
     * @return a list of Habito entities associated with the given Actividad.
     */
    public List<Habito> findByAct(Actividad act) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where idActividad = :act", Habito.class);
        all.setParameter("act", act);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Habito entities by their type.
     *
     * @param type the type of Habito to search by.
     * @return a list of Habito entities of the given type.
     */
    public List<Habito> findByType(String type) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where tipo = :type", Habito.class);
        all.setParameter("type", type);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Habito entities by their frequency range.
     *
     * @param min the minimum frequency.
     * @param max the maximum frequency.
     * @return a list of Habito entities within the given frequency range.
     */
    public List<Habito> findByFrecuency(int min, int max) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where frecuencia between :min and :max", Habito.class);
        all.setParameter("min", min);
        all.setParameter("max", max);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Habito entities by a date range and associated Usuario.
     *
     * @param min the start date of the range.
     * @param max the end date of the range.
     * @param usr the Usuario entity to search by.
     * @return a list of Habito entities within the given date range and associated with the given Usuario.
     */
    public List<Habito> findByDateRange(LocalDate min, LocalDate max, Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where idUsuario = :usr and ultimaFecha between :min and :max", Habito.class);
        all.setParameter("usr", usr);
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
     * Builds a new instance of habitoDAO.
     *
     * @return a new instance of habitoDAO.
     */
    public static habitoDAO build() {
        return new habitoDAO();
    }
}