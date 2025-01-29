package com.github.JuanManuel.model.DAOs;

import com.github.JuanManuel.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usuarioDAO implements DAO<Usuario> {

    @Override
    public boolean insert(Usuario entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.persist(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean update(Usuario entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.update(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public boolean delete(Usuario entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return true;
    }

    @Override
    public Usuario findByPK(Usuario pk) {
        Session sn = sessionFactory.openSession();
        Usuario usr = new Usuario();
        sn.beginTransaction();
        usr = sn.get(Usuario.class, pk.getId());
        sn.getTransaction().commit();
        sn.close();
        return usr;
    }

    @Override
    public List<Usuario> findAll() {
        Session sn = sessionFactory.openSession();
        List<Usuario> ls = new ArrayList<>();
        Query<Usuario> all = sn.createQuery("from Usuario", Usuario.class);
        ls = all.list();
        return ls;
    }

    public Usuario findByEmail(String email) {
        Session sn = sessionFactory.openSession();
        Usuario usr = new Usuario();
        sn.beginTransaction();
        Query<Usuario> all = sn.createQuery("from Usuario where email = :email", Usuario.class);
        all.setParameter("email", email);
        usr = all.uniqueResult();
        sn.getTransaction().commit();
        sn.close();
        return usr;
    }


    @Override
    public void close() throws IOException {

    }

    public static usuarioDAO build() {
        return new usuarioDAO();
    }
}
