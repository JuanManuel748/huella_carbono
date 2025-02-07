package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.categoriaDAO;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.view.Alert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class categoriaService implements service<Categoria> {
    private Integer id;
    private String nombre;
    private BigDecimal factor_emision;
    private String unidad;

    @Override
    public boolean insert(Categoria entity) {
        boolean result = false;
        try {
            Categoria tempCat = categoriaDAO.build().findByName(entity);
            if (tempCat == null) {
                if (validate(entity)) {
                    if (categoriaDAO.build().insert(entity)) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Categoria entity) {
        boolean result = false;
        try {
            if (entity.getId()!= null) {
                Categoria tempCat = categoriaDAO.build().findByPK(entity);
                if (tempCat != null) {
                    if (validate(entity)) {
                        if (categoriaDAO.build().update(entity)) {
                            result = true;
                        }
                    }
                }
            } else {
                Categoria tempCat2 = categoriaDAO.build().findByName(entity);
                if (tempCat2 != null) {
                    entity.setId(tempCat2.getId());
                    if (validate(entity)) {
                        if (categoriaDAO.build().update(entity)) {
                            result = true;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean delete(Categoria entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Categoria tempCat = categoriaDAO.build().findByPK(entity);
                if (tempCat != null) {
                    entity = tempCat;
                    if (categoriaDAO.build().delete(entity)) {
                        result = true;
                    }
                }
            }else {
                Categoria tempCat2 = categoriaDAO.build().findByName(entity);
                if (tempCat2 != null) {
                    entity = tempCat2;
                    if (categoriaDAO.build().delete(entity)) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }



    @Override
    public Categoria findByPK(Categoria pk) {
        Categoria result = null;
        try {
            if (pk != null) {
                if (pk.getId() != null) {
                    result = categoriaDAO.build().findByPK(pk);
                } else if (pk.getNombre() != null && !pk.getNombre().isEmpty()) {
                    result = categoriaDAO.build().findByName(pk);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    @Override
    public List<Categoria> findAll() {
        List<Categoria> ls = new ArrayList<>();
        try {
            ls = categoriaDAO.build().findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public boolean validate(Categoria entity) {
        boolean result = false;
        nombre = entity.getNombre();
        factor_emision = entity.getFactorEmision();
        unidad = entity.getUnidad();

        if (nombre != null && !nombre.isEmpty() && factor_emision != null && unidad != null && !unidad.isEmpty()) {
            if (factor_emision.compareTo(BigDecimal.ZERO) >= 0) {
                result = true;
            }
        }
        return result;
    }

    public static categoriaService build() {
        return new categoriaService();
    }
}
