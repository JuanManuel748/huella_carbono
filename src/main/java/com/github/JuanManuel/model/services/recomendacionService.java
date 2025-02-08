package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.recomendacionDAO;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Recomendacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Recomendacion entities.
 * Provides methods to perform CRUD operations and validation.
 */
public class recomendacionService implements service<Recomendacion> {
    private Integer id;
    private Categoria cat;
    private String descripcion;
    private BigDecimal impacto_estimado;

    /**
     * Inserts a new Recomendacion entity into the database.
     *
     * @param entity the Recomendacion entity to be inserted.
     * @return true if the insertion was successful.
     */
    @Override
    public boolean insert(Recomendacion entity) {
        boolean result = false;
        try {
            Recomendacion tempRec = recomendacionDAO.build().findByPK(entity);
            if (tempRec == null) {
                if (validate(entity)) {
                    if (recomendacionDAO.build().insert(entity)) {
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
     * Updates an existing Recomendacion entity in the database.
     *
     * @param entity the Recomendacion entity to be updated.
     * @return true if the update was successful.
     */
    @Override
    public boolean update(Recomendacion entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Recomendacion tempRec = recomendacionDAO.build().findByPK(entity);
                if (tempRec != null) {
                    if (validate(entity)) {
                        if (recomendacionDAO.build().update(entity)) {
                            result = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Deletes an existing Recomendacion entity from the database.
     *
     * @param entity the Recomendacion entity to be deleted.
     * @return true if the deletion was successful.
     */
    @Override
    public boolean delete(Recomendacion entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Recomendacion tempRec = recomendacionDAO.build().findByPK(entity);
                if (tempRec != null) {
                    entity = tempRec;
                    if (recomendacionDAO.build().delete(entity)) {
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
     * Finds a Recomendacion entity by its primary key.
     *
     * @param pk the primary key of the Recomendacion entity to be found.
     * @return the found Recomendacion entity, or null if not found.
     */
    @Override
    public Recomendacion findByPK(Recomendacion pk) {
        Recomendacion result = new Recomendacion();
        try {
            if (pk != null && pk.getId() != null) {
                result = recomendacionDAO.build().findByPK(pk);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Finds Recomendacion entities by the associated Categoria.
     *
     * @param cat the Categoria entity to search by.
     * @return a list of Recomendacion entities associated with the given Categoria.
     */
    public List<Recomendacion> findByCat(Categoria cat) {
        List<Recomendacion> ls = new ArrayList<>();
        try {
            if (cat != null && (cat.getId() != null || (cat.getNombre() != null && !cat.getNombre().isEmpty()))) {
                Categoria tempCat = categoriaService.build().findByPK(cat);
                ls = recomendacionDAO.build().findByCat(tempCat);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    /**
     * Finds Recomendacion entities by a list of associated Categorias.
     *
     * @param cats the list of Categoria entities to search by.
     * @return a list of Recomendacion entities associated with the given Categorias.
     */
    public List<Recomendacion> findByCats(List<Categoria> cats) {
        List<Recomendacion> ls = new ArrayList<>();
        try {
            if (cats != null && !cats.isEmpty()) {
                for (Categoria cat : cats) {
                    Categoria tempCat = categoriaService.build().findByPK(cat);
                    if (tempCat != null) {
                        List<Recomendacion> tempLs = recomendacionDAO.build().findByCat(tempCat);
                        if (tempLs != null && !tempLs.isEmpty()) {
                            ls.addAll(tempLs);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    /**
     * Finds Recomendacion entities by a range of estimated impact values.
     *
     * @param min the minimum estimated impact value.
     * @param max the maximum estimated impact value.
     * @return a list of Recomendacion entities within the given range.
     */
    public List<Recomendacion> findByRange(Double min, Double max) {
        List<Recomendacion> ls = new ArrayList<>();
        try {
            if (!(min > max || min < 0)) {
                ls = recomendacionDAO.build().findByRange(min, max);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    /**
     * Finds all Recomendacion entities in the database.
     *
     * @return a list of all Recomendacion entities.
     */
    @Override
    public List<Recomendacion> findAll() {
        List<Recomendacion> ls = new ArrayList<>();
        try {
            ls = recomendacionDAO.build().findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    /**
     * Validates a Recomendacion entity.
     *
     * @param entity the Recomendacion entity to be validated.
     * @return true if the entity is valid.
     */
    @Override
    public boolean validate(Recomendacion entity) {
        boolean result = false;
        descripcion = entity.getDescripcion();
        cat = entity.getIdCategoria();

        if (descripcion != null && !descripcion.isEmpty()) {
            Categoria tempCat = categoriaService.build().findByPK(cat);
            if (tempCat != null) {
                entity.setIdCategoria(tempCat);
                result = true;
            }
        }
        return result;
    }

    /**
     * Builds a new instance of recomendacionService.
     *
     * @return a new instance of recomendacionService.
     */
    public static recomendacionService build() {
        return new recomendacionService();
    }
}