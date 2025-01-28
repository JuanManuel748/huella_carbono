package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Actividad;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class actividadDAO implements DAO<Actividad>{
    @Override
    public Actividad save(Actividad entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.save(entity);
        sn.getTransaction().commit();
        sn.close();
        return entity;
    }

    @Override
    public Actividad delete(Actividad entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return entity;
    }

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

    @Override
    public List<Actividad> findAll() {
        Session sn = sessionFactory.openSession();
        List<Actividad> ls = new ArrayList<>();
        Query<Actividad> all = sn.createQuery("from Actividad", Actividad.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    @Override
    public void close() throws IOException {

    }

    public static actividadDAO build() {
        return new actividadDAO();
    }

}
