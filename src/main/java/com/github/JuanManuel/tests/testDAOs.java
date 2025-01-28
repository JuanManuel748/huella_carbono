package com.github.JuanManuel.tests;

import com.github.JuanManuel.model.DAOs.actividadDAO;
import com.github.JuanManuel.model.DAOs.habitoDAO;
import com.github.JuanManuel.model.DAOs.huellaDAO;
import com.github.JuanManuel.model.entities.*;

import java.time.LocalDate;
import java.util.List;

public class testDAOs {
    public static void main(String[] args) {
        System.out.println("GORA ETA GORA EUSKADI");
        try {
            /*
            List<Usuario> listUsers = usuarioDAO.build().findAll();
            for (Usuario user : listUsers) {
                System.out.println(user);
            }
            /*
            Usuario usr = new Usuario();
            usr.setId(1);
            Usuario result = usuarioDAO.build().findByPK(usr);
            System.out.println(result);
            /*
            List<Categoria> listCats = categoriaDAO.build().findAll();
            for (Categoria cat : listCats) {
                System.out.println(cat);
            }
            /*
            Categoria cat = new Categoria();
            cat.setId(1);
            Categoria resultCat = categoriaDAO.build().findByPK(cat);
            System.out.println(resultCat.print());
            /*
            //List<Recomendacion> listCats = recomendacionDAO.build().findByRange(30.0, 50.0);
            List<Recomendacion> listCats = recomendacionDAO.build().findAll();
            for (Recomendacion cat : listCats) {
                System.out.println(cat);
            }
            /*
            Recomendacion cat = new Recomendacion();
            cat.setId(1);
            Recomendacion resultCat = recomendacionDAO.build().findByPK(cat);
            System.out.println(resultCat);
            /*
            Categoria cat = new Categoria();
            cat.setId(1);
            List<Recomendacion> listRecs = recomendacionDAO.build().findByCat(cat);
            for (Recomendacion rec : listRecs) {
                System.out.println(rec);
            }
            /*
            Usuario usr = new Usuario();
            usr.setNombre("Pepillo");
            usr.setEmail("pepillo@gmail.com");
            usr.setContraseña("12345678");
            usr.setFechaRegistro(LocalDate.now());
            usuarioDAO.build().save(usr);
            /*
            Actividad act = new Actividad();
            act.setId(1);
            Actividad resultAct = actividadDAO.build().findByPK(act);
            System.out.println(resultAct);
            /*
            List<Actividad> listActs = actividadDAO.build().findAll();
            for (Actividad acti : listActs) {
                System.out.println(acti);
            }
            /*
            Huella h = new Huella();
            h.setId(1);
            Huella result = huellaDAO.build().findByPK(h);
            System.out.println(result);
            /*
            Usuario u = new Usuario();
            u.setId(1);
            List<Huella> listHuellas = huellaDAO.build().findByUser(u);
            for (Huella h : listHuellas) {
                System.out.println(h);
            }
            /*
            Actividad act = new Actividad();
            act.setId(7);
            List<Huella> listHuellas = huellaDAO.build().findByAct(act);
            for (Huella h : listHuellas) {
                System.out.println(h);
            }
            /*
            LocalDate min = LocalDate.of(2025, 1, 20);
            LocalDate max = LocalDate.of(2025, 1, 30);
            List<Huella> listHuellas = huellaDAO.build().findByDateRange(min, max);
            for (Huella h : listHuellas) {
                System.out.println(h);
            }
            /*
            Habito hab = new Habito();
            hab.setId(new HabitoId(1, 1));
            Habito result = habitoDAO.build().findByPK(hab);
            System.out.println(result);
            /*
            Usuario usr = new Usuario();
            usr.setId(2);
            List<Habito> listHabitos = habitoDAO.build().findByUser(usr);
            for (Habito h : listHabitos) {
                System.out.println(h);
            }
            /*
            Actividad act = new Actividad();
            act.setId(1);
            List<Habito> listHabitos = habitoDAO.build().findByAct(act);
            for (Habito h : listHabitos) {
                System.out.println(h);
            }
            /*
            String tipo = "mensual";
            List<Habito> listHabitos = habitoDAO.build().findByType(tipo);
            for (Habito h : listHabitos) {
                System.out.println(h);
            }
            /*
            int min = 1;
            int max = 3;
            List<Habito> listHabitos = habitoDAO.build().findByFrecuency(min, max);
            for (Habito h : listHabitos) {
                System.out.println(h);
            }
            /* */

            /* */

            /* */

            /* */

            /* */

            /* */
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("ADIOSSSSSSSSSSSSSSSSSSSSSS");
    }
}
