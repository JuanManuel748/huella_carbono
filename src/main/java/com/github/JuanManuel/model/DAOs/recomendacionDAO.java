package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Recomendacion;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class recomendacionDAO implements DAO<Recomendacion>{

    @Override
    public boolean insert(Recomendacion entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean update(Recomendacion entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean delete(Recomendacion entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

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

    @Override
    public List<Recomendacion> findAll() {
        Session sn = sessionFactory.openSession();
        List<Recomendacion> ls = new ArrayList<>();
        Query<Recomendacion> all = sn.createQuery("from Recomendacion", Recomendacion.class);
        ls = all.list();
        sn.close();
        return ls;
    }

    public List<Recomendacion> findByCat(Categoria cat) {
        Session sn = sessionFactory.openSession();
        List<Recomendacion> ls = new ArrayList<>();
        Query<Recomendacion> all = sn.createQuery("from Recomendacion where idCategoria = :cat", Recomendacion.class);
        all.setParameter("cat", cat);
        ls = all.list();
        sn.close();
        return ls;
    }

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

    @Override
    public void close() throws IOException {

    }

    public static recomendacionDAO build() {
        return new recomendacionDAO();
    }

}
