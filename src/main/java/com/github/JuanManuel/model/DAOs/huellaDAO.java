package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Huella;
import com.github.JuanManuel.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the Huella entity.
 * Provides methods to perform CRUD operations on the Huella table in the database.
 */
public class huellaDAO implements DAO<Huella> {

    /**
     * Inserts a new Huella entity into the database.
     *
     * @param entity the Huella entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Huella entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Updates an existing Huella entity in the database.
     *
     * @param entity the Huella entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Huella entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Deletes an existing Huella entity from the database.
     *
     * @param entity the Huella entity to be deleted.
     * @return true if the deletion was successful.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public boolean delete(Huella entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    /**
     * Finds a Huella entity by its primary key.
     *
     * @param pk the primary key of the Huella entity to be found.
     * @return the found Huella entity, or null if not found.
     */
    @Override
    public Huella findByPK(Huella pk) {
        Session sn = sessionFactory.openSession();
        Huella result = new Huella();
        sn.beginTransaction();
        result = sn.get(Huella.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return result;
    }

    /**
     * Finds all Huella entities in the database.
     *
     * @return a list of all Huella entities.
     */
    @Override
    public List<Huella> findAll() {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella", Huella.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Huella entities by the associated Usuario.
     *
     * @param usr the Usuario entity to search by.
     * @return a list of Huella entities associated with the given Usuario.
     */
    public List<Huella> findByUser(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella where idUsuario = :usr", Huella.class);
        all.setParameter("usr", usr);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Huella entities by the associated Actividad.
     *
     * @param act the Actividad entity to search by.
     * @return a list of Huella entities associated with the given Actividad.
     */
    public List<Huella> findByAct(Actividad act) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella where idActividad = :act", Huella.class);
        all.setParameter("act", act);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Huella entities by a date range and associated Usuario.
     *
     * @param min the start date of the range.
     * @param max the end date of the range.
     * @param usr the Usuario entity to search by.
     * @return a list of Huella entities within the given date range and associated with the given Usuario.
     */
    public List<Huella> findByDateRange(LocalDate min, LocalDate max, Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella where idUsuario = :usr and fecha between :min and :max", Huella.class);
        all.setParameter("usr", usr);
        all.setParameter("min", min);
        all.setParameter("max", max);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Finds Huella entities by the associated Usuario and Categoria.
     *
     * @param u the Usuario entity to search by.
     * @param c the Categoria entity to search by.
     * @return a list of Huella entities associated with the given Usuario and Categoria.
     */
    public List<Huella> findByUserFiltByCat(Usuario u, Categoria c) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella h where h.idUsuario = :usr and h.idActividad.idCategoria = :cat", Huella.class);
        all.setParameter("usr", u);
        all.setParameter("cat", c);
        ls = all.list();
        sn.close();
        return ls;
    }

    /**
     * Counts Huella entities by Categoria for a given Usuario.
     *
     * @param u the Usuario entity to search by.
     * @return a list of objects containing Categoria names and their respective counts.
     */
    public List<Object[]> countByCategorias(Usuario u) {
        Session sn = sessionFactory.openSession();
        List<Object[]> results = new ArrayList<>();
        String hql = "SELECT c.nombre, COUNT(h) " +
                "FROM Huella h " +
                "JOIN h.idActividad a " +
                "JOIN a.idCategoria c " +
                "WHERE h.idUsuario = :userId " +
                "GROUP BY c.nombre";
        Query<Object[]> query = sn.createQuery(hql, Object[].class);
        query.setParameter("userId", u);
        results = query.list();
        sn.close();
        return results;
    }

    /**
     * Counts Huella entities by Actividad for a given Usuario and Categoria.
     *
     * @param u the Usuario entity to search by.
     * @param c the Categoria entity to search by.
     * @return a list of objects containing Actividad names and their respective counts.
     */
    public List<Object[]> countByCat(Usuario u, Categoria c) {
        Session sn = sessionFactory.openSession();
        List<Object[]> results = new ArrayList<>();
        String hql = "SELECT a.nombre, COUNT(h) " +
                "FROM Huella h " +
                "JOIN h.idActividad a " +
                "JOIN a.idCategoria c " +
                "WHERE h.idUsuario = :userId AND c.id = :catId " +
                "GROUP BY a.nombre";
        Query<Object[]> query = sn.createQuery(hql, Object[].class);
        query.setParameter("userId", u);
        query.setParameter("catId", c.getId());
        results = query.list();
        sn.close();
        return results;
    }

    /**
     * Finds weekly Huella entities by the associated Usuario.
     *
     * @param usr the Usuario entity to search by.
     * @return a list of objects containing the start date of the week and the sum of values.
     */
    public List<Object[]> findWeeksByUsr(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Object[]> results = new ArrayList<>();
        String hql = "SELECT MIN(h.fecha), SUM(h.valor * c.factorEmision) " +
                "FROM Huella h " +
                "JOIN h.idActividad a " +
                "JOIN a.idCategoria c " +
                "WHERE h.idUsuario = :usr " +
                "GROUP BY WEEK(h.fecha)" +
                "ORDER BY WEEK(h.fecha)";
        Query<Object[]> query = sn.createQuery(hql, Object[].class);
        query.setParameter("usr", usr);
        results = query.list();
        sn.close();
        return results;
    }

    /**
     * Finds monthly Huella entities by the associated Usuario.
     *
     * @param usr the Usuario entity to search by.
     * @return a list of objects containing the year, month, and the sum of values.
     */
    public List<Object[]> findMonthsByUsr(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Object[]> results = new ArrayList<>();
        String hql = "SELECT YEAR(h.fecha), MONTH(h.fecha), SUM(h.valor * c.factorEmision) " +
                "FROM Huella h " +
                "JOIN h.idActividad a " +
                "JOIN a.idCategoria c " +
                "WHERE h.idUsuario = :usr " +
                "GROUP BY YEAR(h.fecha), MONTH(h.fecha) " +
                "ORDER BY YEAR(h.fecha), MONTH(h.fecha)";
        Query<Object[]> query = sn.createQuery(hql, Object[].class);
        query.setParameter("usr", usr);
        results = query.list();
        sn.close();
        return results;
    }

    /**
     * Finds yearly Huella entities by the associated Usuario.
     *
     * @param usr the Usuario entity to search by.
     * @return a list of objects containing the year and the sum of values.
     */
    public List<Object[]> findYearsByUsr(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Object[]> results = new ArrayList<>();
        String hql = "SELECT YEAR(h.fecha), SUM(h.valor * c.factorEmision) " +
                "FROM Huella h " +
                "JOIN h.idActividad a " +
                "JOIN a.idCategoria c " +
                "WHERE h.idUsuario = :usr " +
                "GROUP BY YEAR(h.fecha)";
        Query<Object[]> query = sn.createQuery(hql, Object[].class);
        query.setParameter("usr", usr);
        results = query.list();
        sn.close();
        return results;
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
     * Builds a new instance of huellaDAO.
     *
     * @return a new instance of huellaDAO.
     */
    public static huellaDAO build() {
        return new huellaDAO();
    }
}