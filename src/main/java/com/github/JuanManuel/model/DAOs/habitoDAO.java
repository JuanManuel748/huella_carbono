package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Habito;
import com.github.JuanManuel.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class habitoDAO implements DAO<Habito> {

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

    @Override
    public boolean update(Habito entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean delete(Habito entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

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

    @Override
    public List<Habito> findAll() {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito", Habito.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    public List<Habito> findByUser(Usuario usr) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where idUsuario = :usr", Habito.class);
        all.setParameter("usr", usr);
        ls = all.list();
        sn.close();
        return ls;
    }

    public List<Habito> findByAct(Actividad act) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where idActividad = :act", Habito.class);
        all.setParameter("act", act);
        ls = all.list();
        sn.close();
        return ls;
    }

    public List<Habito> findByType(String type) {
        Session sn = sessionFactory.openSession();
        List<Habito> ls = new ArrayList<>();
        Query<Habito> all = sn.createQuery("from Habito where tipo = :type", Habito.class);
        all.setParameter("type", type);
        ls = all.list();
        sn.close();
        return ls;
    }

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



    @Override
    public void close() throws IOException {

    }

    public static habitoDAO build() {
        return new habitoDAO();
    }



}
