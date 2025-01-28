package com.github.JuanManuel.tests;

import com.github.JuanManuel.model.entities.Categoria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class testGeneral {
    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        findAll(new Categoria());
    }

    private static void findAll(Object entity) {
        Session snFindAll = sessionFactory.openSession();
        Query<Object> query = (Query<Object>) snFindAll.createQuery("from " + entity.getClass().getSimpleName(), entity.getClass());

        if (query.list().isEmpty()) {
            System.out.println("No hay datos en la base de datos.");
        } else {
            for (Object obj : query.list()) {
                System.out.println(obj);
            }
        }
        snFindAll.close();
    }
}