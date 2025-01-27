package com.github.JuanManuel.test;

import com.github.JuanManuel.model.entity.Actividad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class testActividad {
    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        System.out.println("GORA ETA");
        Session sn = sessionFactory.openSession();

        Query<Actividad> todoslosAlumnos = sn.createQuery("from Actividad", Actividad.class);
        for (Actividad alumnos : todoslosAlumnos.list()) {
            System.out.println(alumnos);
        }

        System.out.println("A CHUPARLA");
        sn.close();
    }
}