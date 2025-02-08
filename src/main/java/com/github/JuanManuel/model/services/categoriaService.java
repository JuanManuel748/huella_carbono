package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.categoriaDAO;
import com.github.JuanManuel.model.entities.Categoria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Categoria entities.
 * Provides methods to perform CRUD operations and validation.
 */
public class categoriaService implements service<Categoria> {
    private Integer id;
    private String nombre;
    private BigDecimal factor_emision;
    private String unidad;

    /**
     * Inserts a new Categoria entity into the database.
     *
     * @param entity the Categoria entity to be inserted.
     * @return true if the insertion was successful.
     */
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

    /**
     * Updates an existing Categoria entity in the database.
     *
     * @param entity the Categoria entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Categoria entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
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

    /**
     * Deletes an existing Categoria entity from the database.
     *
     * @param entity the Categoria entity to be deleted.
     * @return true if the deletion was successful.
     */
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
            } else {
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

    /**
     * Finds a Categoria entity by its primary key.
     *
     * @param pk the primary key of the Categoria entity to be found.
     * @return the found Categoria entity, or null if not found.
     */
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

    /**
     * Finds all Categoria entities in the database.
     *
     * @return a list of all Categoria entities.
     */
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

    /**
     * Validates a Categoria entity.
     *
     * @param entity the Categoria entity to be validated.
     * @return true if the entity is valid.
     */
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

    /**
     * Builds a new instance of categoriaService.
     *
     * @return a new instance of categoriaService.
     */
    public static categoriaService build() {
        return new categoriaService();
    }
}