package com.github.JuanManuel.model.DAO;

import com.github.JuanManuel.model.entity.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usuarioDAO implements DAO<Usuario> {
    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public Usuario save(Usuario entity) {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.save(entity);
        sn.getTransaction().commit();
        sn.close();
        return entity;
    }

    @Override
    public Usuario delete(Usuario entity) throws SQLException {
        Session sn = sessionFactory.openSession();
        sn.beginTransaction();
        sn.delete(entity);
        sn.getTransaction().commit();
        sn.close();
        return entity;
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
        Query<Usuario> allUsers = sn.createQuery("from Usuario", Usuario.class);
        ls = allUsers.list();
        return ls;
    }


    @Override
    public void close() throws IOException {

    }

    public static usuarioDAO build() {
        return new usuarioDAO();
    }
}
