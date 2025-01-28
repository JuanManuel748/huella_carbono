package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Actividad;
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
    public Huella save(Huella entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.save(entity);
        sn.getTransaction().commit();
        sn.close();
        return entity;
    }

    @Override
    public Huella delete(Huella entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return entity;
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

    public List<Huella> findByDateRange(LocalDate min, LocalDate max) {
        Session sn = sessionFactory.openSession();
        List<Huella> ls = new ArrayList<>();
        Query<Huella> all = sn.createQuery("from Huella where fecha between :min and :max", Huella.class);
        all.setParameter("min", min);
        all.setParameter("max", max);
        ls = all.list();
        sn.close();
        return ls;
    }

    @Override
    public void close() throws IOException {

    }

    public static huellaDAO build() {
        return new huellaDAO();
    }

}
