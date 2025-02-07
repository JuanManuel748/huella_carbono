package com.github.JuanManuel.utils;

import com.github.JuanManuel.model.entities.*;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.huellaService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.github.JuanManuel.model.services.recomendacionService;
import javafx.stage.FileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PDFmanager {
    public void generatePDF(List<Huella> huellas) {
        try {
            String filename = "huellas.pdf";
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                filename = file.getAbsolutePath();
            }

            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Usuario currentUser = Session.getInstance().getCurrentUser();

            document.add(new Paragraph("Información del usuario"));
            document.add(new Paragraph("Nombre: " + currentUser.getNombre()));
            document.add(new Paragraph("Correo electrónico: " + currentUser.getEmail()));
            document.add(new Paragraph("Fecha de registro: " + currentUser.getFechaRegistro()));

            document.add(new Paragraph("Huellas"));
            Table huellasTable = new Table(new float[]{30, 4, 4, 4, 4});
            huellasTable.setWidth(500);
            huellasTable.addHeaderCell("Actividad");
            huellasTable.addHeaderCell("Valor");
            huellasTable.addHeaderCell("Unidad");
            huellasTable.addHeaderCell("Fecha");
            huellasTable.addHeaderCell("Impacto");

            for (Huella huella : huellas) {
                Actividad tempAct = huella.getIdActividad();
                tempAct = actividadService.build().findByPK(tempAct);
                huellasTable.addCell(tempAct.getNombre());
                huellasTable.addCell(huella.getValor().toString());
                huellasTable.addCell(huella.getUnidad());
                huellasTable.addCell(huella.getFecha().toString());
                huellasTable.addCell(String.valueOf(calculateImpact(huella)));
            }
            document.add(huellasTable);

            document.add(new Paragraph("Recomendaciones"));
            List<Recomendacion> recomendaciones = filtRecoms(huellas);
            Table recomTable = new Table(new float[]{4, 4});
            recomTable.setWidth(500);
            recomTable.addHeaderCell("Descripción");
            recomTable.addHeaderCell("Impacto Estimado");

            for (Recomendacion recomendacion : recomendaciones) {
                recomTable.addCell(recomendacion.getDescripcion());
                recomTable.addCell(recomendacion.getImpactoEstimado().toString());
            }
            document.add(recomTable);

            List<Object[]> countResults = huellaService.build().countByCategorias(Session.getInstance().getCurrentUser());
            addPieChart(document, countResults);

            List<Object[]> stats = huellaService.build().statsByMonth(currentUser);
            addStatsChart(document, stats);

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPieChart(Document document, List<Object[]> countResults) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object[] o : countResults) {
            String cat = (String) o[0];
            Long count = (Long) o[1];
            dataset.setValue(cat, count);
        }

        JFreeChart chart = ChartFactory.createRingChart(
                "Categoría de Huellas",
                dataset,
                true,
                true,
                false
        );

        BufferedImage bufferedImage = chart.createBufferedImage(500, 500);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(byteArrayOutputStream, bufferedImage);
        ImageData imageData = ImageDataFactory.create(byteArrayOutputStream.toByteArray());
        Image image = new Image(imageData);
        document.add(image);
    }

    private void addStatsChart(Document document, List<Object[]> monthlyStats) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] stat : monthlyStats) {
            String month = (String) stat[0];
            double impact = (double) stat[1];
            dataset.addValue(impact, "Impact", month);
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Evolución del Impacto Mensual",
                "Mes",
                "Impacto",
                dataset,
                PlotOrientation.VERTICAL,
                false, // Desactivar la leyenda
                true,
                false
        );

        BufferedImage bufferedImage = lineChart.createBufferedImage(500, 300);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ChartUtils.writeBufferedImageAsPNG(byteArrayOutputStream, bufferedImage);
        ImageData imageData = ImageDataFactory.create(byteArrayOutputStream.toByteArray());
        Image image = new Image(imageData);
        document.add(image);
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

    public static PDFmanager build() {
        return new PDFmanager();
    }


}