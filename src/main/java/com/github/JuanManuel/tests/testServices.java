package com.github.JuanManuel.tests;

import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Usuario;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.usuarioService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class testServices {
    public static void main(String[] args) {
        try {
            /*
            Usuario usr = new Usuario();
            usr.setId(1);
            Usuario result = usuarioService.build().findByPK(usr);
            System.out.println(result);
            /*
            String correo = "jperram1803b@gmail.com";
            Usuario result2 = usuarioService.build().findByEmail(correo);
            System.out.println(result2);
            /*
            List<Usuario> result3 = usuarioService.build().findAll();
            for (Usuario u : result3) {
                System.out.println(u);
            }
            /*
            Usuario u = new Usuario();
            u.setId(2);
            u = usuarioService.build().findByPK(u);
            boolean result4 = usuarioService.build().delete(u);
            System.out.println("Resultado: "+result4);
            /*
            Usuario u2 = usuarioService.build().findByEmail("panchitomuerto@gmail.com");
            //Usuario u2 = new Usuario();
            u2.setNombre("Tate");
            u2.setEmail("tategamer111@gmail.com");
            u2.setContraseña("12aa34");
            u2.setFechaRegistro(LocalDate.now());
            boolean result5 = usuarioService.build().update(u2);
            System.out.println("Resultado: "+result5);
            /*
            Usuario u3 = new Usuario();
            u3.setNombre("Panchito");
            u3.setEmail("panchitoland@gmail.com");
            u3.setContraseña("12aa34");
            u3.setFechaRegistro(LocalDate.now());
            boolean result6 = usuarioService.build().insert(u3);
            System.out.println("Resultado: "+result6);
            /*
            Categoria cat = new Categoria();
            cat.setId(1);
            Categoria result = categoriaService.build().findByPK(cat);
            System.out.println(result);
            /*
            Categoria cat2 = new Categoria();
            cat2.setNombre("asdasdasd");
            cat2.setFactorEmision(BigDecimal.valueOf(0.530));
            cat2.setUnidad("kg");
            boolean result2 = categoriaService.build().insert(cat2);
            System.out.println("Resultado: "+result2);
            /*
            Categoria cat2 = new Categoria();
            cat2.setNombre("asdasdasd");
            cat2.setFactorEmision(BigDecimal.valueOf(0.357));
            cat2.setUnidad("ASD");
            boolean result2 = categoriaService.build().update(cat2);
            System.out.println("Resultado: "+result2);
            /*
            Categoria cat2 = new Categoria();
            cat2.setId(6);
            boolean result2 = categoriaService.build().delete(cat2);
            System.out.println("Resultado: "+result2);
            /*
            Categoria cat4 = new Categoria();
            cat4.setNombre("Transporte");
            Categoria result4 = categoriaService.build().findByName(cat4);
            System.out.println(result4);
            /*
            Actividad act = new Actividad();
            act.setNombre("Defecar");
            Categoria cat = new Categoria();
            cat.setId(1);
            act.setIdCategoria(categoriaService.build().findByPK(cat));
            boolean result = actividadService.build().insert(act);
            System.out.println("Resultado: "+result);
            /*
            Actividad act2 = new Actividad();
            act2.setNombre("Defecar");
            Categoria cat2 = new Categoria();
            cat2.setId(4);
            act2.setIdCategoria(categoriaService.build().findByPK(cat2));
            boolean result2 = actividadService.build().update(act2);
            System.out.println("Resultado: "+result2);
            /*
            Actividad act3 = new Actividad();
            act3.setNombre("Defecar");
            boolean result3 = actividadService.build().delete(act3);
            System.out.println("Resultado: "+result3);
            /* */

            /* */

            /* */

            /* */

            /* */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
