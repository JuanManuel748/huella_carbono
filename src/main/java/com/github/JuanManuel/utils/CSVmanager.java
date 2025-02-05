package com.github.JuanManuel.utils;

import com.github.JuanManuel.model.entities.*;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.recomendacionService;
import com.itextpdf.layout.element.Paragraph;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class CSVmanager {
    public static CSVmanager build() {
        return new CSVmanager();
    }

    public void generateCSV(List<Huella> huellas) {
        try {
            String filename = "huellas.csv";
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                filename = file.getAbsolutePath();
            }

            FileWriter writer = new FileWriter(filename);


            Usuario currentUser = Session.getInstance().getCurrentUser();
            writer.append("Información del usuario\n");
            writer.append("Nombre: ").append(currentUser.getNombre()).append("\n");
            writer.append("Correo electrónico: ").append(currentUser.getEmail()).append("\n");
            writer.append("Fecha de Registro: ").append(currentUser.getFechaRegistro().toString()).append("\n");

            writer.append("\nHuellas de Carbono\n");
            writer.append("Actividad,Valor,Unidad,Fecha,Impacto\n");

            for (Huella huella : huellas) {
                Actividad tempAct = huella.getIdActividad();
                tempAct = actividadService.build().findByPK(tempAct);
                writer.append(tempAct.getNombre()).append(",");
                writer.append(huella.getValor().toString()).append(",");
                writer.append(huella.getUnidad()).append(",");
                writer.append(huella.getFecha().toString()).append(",");
                writer.append(String.valueOf(calculateImpact(huella))).append("\n");
            }

            List<Recomendacion> recomendaciones = filtRecoms(huellas);
            writer.append("\nRecomendaciones\n");
            writer.append("Descripción,Impacto\n");
            for (Recomendacion recomendacion : recomendaciones) {
                writer.append(recomendacion.getDescripcion()).append(",");
                writer.append(recomendacion.getImpactoEstimado().toString()).append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateImpact(Huella huella) {
        Double result = 0.0;
        try {
            Actividad tempAct = huella.getIdActividad();
            tempAct = actividadService.build().findByPK(tempAct);
            Categoria tempCat = tempAct.getIdCategoria();
            tempCat = categoriaService.build().findByPK(tempCat);
            Double fact = tempCat.getFactorEmision().doubleValue();
            result = huella.getValor().doubleValue() * fact;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Recomendacion> filtRecoms(List<Huella> huellas) {
        List<Recomendacion> recomendaciones = new ArrayList<>();
        List<Categoria> cats = new ArrayList<>();
        for (Huella h : huellas) {
            Actividad act = h.getIdActividad();
            act = actividadService.build().findByPK(act);
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            if (!cats.contains(cat)) {
                cats.add(cat);
            }
        }
        recomendaciones = recomendacionService.build().findByCats(cats);
        return recomendaciones;
    }
}