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

public class huellaDAO implements DAO<Huella>{

    @Override
    public boolean insert(Huella entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean update(Huella entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean delete(Huella entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

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

    @Override
    public List<Huella> findAll() {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella", Huella.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    public List<Huella> findByUser(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella where idUsuario = :usr", Huella.class);
        all.setParameter("usr", usr);
        ls = all.list();
        sn.close();
        return ls;
    }

    public List<Huella> findByAct(Actividad act) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella where idActividad = :act", Huella.class);
        all.setParameter("act", act);
        ls = all.list();
        sn.close();
        return ls;
    }

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

    // huellaDAO.java
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


    @Override
    public void close() throws IOException {

    }

    public static huellaDAO build() {
        return new huellaDAO();
    }

}
