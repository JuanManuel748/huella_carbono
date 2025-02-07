package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Categoria;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class categoriaDAO implements DAO<Categoria> {

    @Override
    public boolean insert(Categoria entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean update(Categoria entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean delete(Categoria entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

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

    @Override
    public List<Categoria> findAll() {
        Session sn = sessionFactory.openSession();
        List<Categoria> ls = new ArrayList<>();
        Query<Categoria> all = sn.createQuery("from Categoria", Categoria.class);
        ls = all.list();
        sn.close();
        return ls;
    }

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

    @Override
    public void close() throws IOException {

    }
    public static categoriaDAO build() {
        return new categoriaDAO();
    }

}
