package com.github.JuanManuel.tests;

import com.github.JuanManuel.model.entities.*;
import com.github.JuanManuel.model.services.*;
import com.github.JuanManuel.utils.HashPass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        // Insertar datos en la base de datos
        // Usuario Juan Manuel
        //Usuario juanma = createUser("Juan Manuel");
        Usuario juanma = new Usuario("juanma@gmail.com");
        juanma = usuarioService.build().findByPK(juanma);

        // Usuario Antonio
        //Usuario antonio = createUser("Antonio");
        Usuario antonio = new Usuario("antonio@gmail.com");
        antonio = usuarioService.build().findByPK(antonio);

        // Huellas de Juan Manuel
        //huellasExample1(juanma);

        // Huellas de Antonio
        //huellasExample2(antonio);

        // Habitos de Juan Manuel
        //habitosExample1(juanma);

        // Habitos de Antonio
        //habitosExample2(antonio);

    }


    private static void habitosExample1(Usuario u) {
        List<Actividad> actividades = actividadService.build().findAll();
        for (Actividad act: actividades) {
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            act.setIdCategoria(cat);
        }
        // Habitos
        List<Habito> habitosLS = new ArrayList<>();
        // Habito 1
        Habito h1 = new Habito(u, actividades.get(1));
        h1.setFrecuencia(5);
        h1.setTipo("Semanal");
        h1.setUltimaFecha(LocalDate.now().minusDays(1));
        habitosLS.add(h1);
        // Habito 2
        Habito h2 = new Habito(u, actividades.get(4));
        h2.setFrecuencia(2);
        h2.setTipo("Diario");
        h2.setUltimaFecha(LocalDate.now().minusDays(1));
        habitosLS.add(h2);
        // Habito 3
        Habito h3 = new Habito(u, actividades.get(7));
        h3.setFrecuencia(1);
        h3.setTipo("Mensual");
        h3.setUltimaFecha(LocalDate.now().minusDays(7));
        habitosLS.add(h3);
        // Habito 4
        Habito h4 = new Habito(u, actividades.get(2));
        h4.setFrecuencia(2);
        h4.setTipo("Anual");
        h4.setUltimaFecha(LocalDate.now().minusDays(40));
        habitosLS.add(h4);

        for (Habito h: habitosLS) {
            habitoService.build().insert(h);
        }

    }

    private static void habitosExample2(Usuario u) {
        List<Actividad> actividades = actividadService.build().findAll();
        for (Actividad act: actividades) {
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            act.setIdCategoria(cat);
        }
        // Habitos
        List<Habito> habitosLS = new ArrayList<>();
        // Habito 1
        Habito h1 = new Habito(u, actividades.get(1));
        h1.setFrecuencia(2);
        h1.setTipo("Diario");
        h1.setUltimaFecha(LocalDate.now().minusDays(2));
        habitosLS.add(h1);
        // Habito 2
        Habito h2 = new Habito(u, actividades.get(5));
        h2.setFrecuencia(1);
        h2.setTipo("Semanal");
        h2.setUltimaFecha(LocalDate.now().minusDays(1));
        habitosLS.add(h2);

        for (Habito h: habitosLS) {
            habitoService.build().insert(h);
        }

    }

    private static Usuario createUser(String name) {
        Usuario u = new Usuario();
        u.setNombre(name);
        u.setEmail(name.toLowerCase().trim() + "@gmail.com");
        u.setContraseña(HashPass.hashPassword("12345678"));
        u.setFechaRegistro(java.time.LocalDate.now().minusYears(1));
        usuarioService.build().insert(u);
        return u;
    }

    private static void huellasExample1(Usuario u) {

        List<Actividad> actividades = actividadService.build().findAll();
        for (Actividad act: actividades) {
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            act.setIdCategoria(cat);
        }
        // Huellas
        List<Huella> huellasLS = new ArrayList<>();
        // Huella 1
        Huella h1 = new Huella();
        h1.setIdUsuario(u);
        h1.setIdActividad(actividades.get(0));
        h1.setValor(BigDecimal.valueOf(50.0));
        h1.setUnidad(actividades.get(0).getIdCategoria().getUnidad());
        h1.setFecha(LocalDate.now().minusDays(300));
        huellasLS.add(h1);
        // Huella 2
        Huella h2 = new Huella();
        h2.setIdUsuario(u);
        h2.setIdActividad(actividades.get(1));
        h2.setValor(BigDecimal.valueOf(15.0));
        h2.setUnidad(actividades.get(1).getIdCategoria().getUnidad());
        h2.setFecha(LocalDate.now().minusDays(298));
        huellasLS.add(h2);
        // Huella 3
        Huella h3 = new Huella();
        h3.setIdUsuario(u);
        h3.setIdActividad(actividades.get(2));
        h3.setValor(BigDecimal.valueOf(500.0));
        h3.setUnidad(actividades.get(2).getIdCategoria().getUnidad());
        h3.setFecha(LocalDate.now().minusDays(250));
        huellasLS.add(h3);
        // Huella 4
        Huella h4 = new Huella();
        h4.setIdUsuario(u);
        h4.setIdActividad(actividades.get(3));
        h4.setValor(BigDecimal.valueOf(100.0));
        h4.setUnidad(actividades.get(3).getIdCategoria().getUnidad());
        h4.setFecha(LocalDate.now().minusDays(200));
        huellasLS.add(h4);
        // Huella 5
        Huella h5 = new Huella();
        h5.setIdUsuario(u);
        h5.setIdActividad(actividades.get(4));
        h5.setValor(BigDecimal.valueOf(10.0));
        h5.setUnidad(actividades.get(4).getIdCategoria().getUnidad());
        h5.setFecha(LocalDate.now().minusDays(199));
        huellasLS.add(h5);
        // Huella 6
        Huella h6 = new Huella();
        h6.setIdUsuario(u);
        h6.setIdActividad(actividades.get(5));
        h6.setValor(BigDecimal.valueOf(2.0));
        h6.setUnidad(actividades.get(5).getIdCategoria().getUnidad());
        h6.setFecha(LocalDate.now().minusDays(180));
        huellasLS.add(h6);
        // Huella 7
        Huella h7 = new Huella();
        h7.setIdUsuario(u);
        h7.setIdActividad(actividades.get(6));
        h7.setValor(BigDecimal.valueOf(3.0));
        h7.setUnidad(actividades.get(6).getIdCategoria().getUnidad());
        h7.setFecha(LocalDate.now().minusDays(150));
        huellasLS.add(h7);
        // Huella 8
        Huella h8 = new Huella();
        h8.setIdUsuario(u);
        h8.setIdActividad(actividades.get(7));
        h8.setValor(BigDecimal.valueOf(1.0));
        h8.setUnidad(actividades.get(7).getIdCategoria().getUnidad());
        h8.setFecha(LocalDate.now().minusDays(100));
        huellasLS.add(h8);
        // Huella 9
        Huella h9 = new Huella();
        h9.setIdUsuario(u);
        h9.setIdActividad(actividades.get(8));
        h9.setValor(BigDecimal.valueOf(0.5));
        h9.setUnidad(actividades.get(8).getIdCategoria().getUnidad());
        h9.setFecha(LocalDate.now().minusDays(80));
        huellasLS.add(h9);
        // Huella 10
        Huella h10 = new Huella();
        h10.setIdUsuario(u);
        h10.setIdActividad(actividades.get(2));
        h10.setValor(BigDecimal.valueOf(700.0));
        h10.setUnidad(actividades.get(2).getIdCategoria().getUnidad());
        h10.setFecha(LocalDate.now().minusDays(40));
        huellasLS.add(h10);

        for (Huella h: huellasLS) {
            huellaService.build().insert(h);
        }
    }

    private static void huellasExample2(Usuario u) {
        List<Actividad> actividades = actividadService.build().findAll();
        for (Actividad act: actividades) {
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            act.setIdCategoria(cat);
        }
        List<Huella> huellasLS = new ArrayList<>();
        // Huella 1
        Huella h1 = new Huella();
        h1.setIdUsuario(u);
        h1.setIdActividad(actividades.get(5));
        h1.setValor(BigDecimal.valueOf(50.0));
        h1.setUnidad(actividades.get(5).getIdCategoria().getUnidad());
        h1.setFecha(LocalDate.now().minusDays(100));
        huellasLS.add(h1);
        // Huella 2
        Huella h2 = new Huella();
        h2.setIdUsuario(u);
        h2.setIdActividad(actividades.get(2));
        h2.setValor(BigDecimal.valueOf(50.0));
        h2.setUnidad(actividades.get(2).getIdCategoria().getUnidad());
        h2.setFecha(LocalDate.now().minusDays(99));
        huellasLS.add(h2);
        // Huella 3
        Huella h3 = new Huella();
        h3.setIdUsuario(u);
        h3.setIdActividad(actividades.get(7));
        h3.setValor(BigDecimal.valueOf(5.0));
        h3.setUnidad(actividades.get(7).getIdCategoria().getUnidad());
        h3.setFecha(LocalDate.now().minusDays(98));
        huellasLS.add(h3);
        // Huella 4
        Huella h4 = new Huella();
        h4.setIdUsuario(u);
        h4.setIdActividad(actividades.get(1));
        h4.setValor(BigDecimal.valueOf(100.0));
        h4.setUnidad(actividades.get(1).getIdCategoria().getUnidad());
        h4.setFecha(LocalDate.now().minusDays(5));
        huellasLS.add(h4);
        // Huella 5
        Huella h5 = new Huella();
        h5.setIdUsuario(u);
        h5.setIdActividad(actividades.get(1));
        h5.setValor(BigDecimal.valueOf(40.0));
        h5.setUnidad(actividades.get(1).getIdCategoria().getUnidad());
        h5.setFecha(LocalDate.now().minusDays(3));
        huellasLS.add(h5);

        for (Huella h: huellasLS) {
            huellaService.build().insert(h);
        }
    }
}
