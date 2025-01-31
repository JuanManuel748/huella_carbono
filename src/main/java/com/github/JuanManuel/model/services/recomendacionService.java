package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.recomendacionDAO;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Recomendacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class recomendacionService implements  service<Recomendacion> {
    private Integer id;
    private Categoria cat;
    private String descripcion;
    private BigDecimal impacto_estimado;


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
            throw new RuntimeException();
        }

        return result;
    }

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

    @Override
    public Recomendacion findByPK(Recomendacion pk) {
        Recomendacion result = new Recomendacion();
        try {
            if(pk != null) {
                if(pk.getId() != null) {
                    result = recomendacionDAO.build().findByPK(pk);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Recomendacion> findByCat(Categoria cat) {
        List<Recomendacion> ls = new ArrayList<>();
        try {
            if (cat != null && (cat.getId()!= null || cat.getNombre() != null || cat.getNombre().isEmpty())) {
                Categoria tempCat = categoriaService.build().findByPK(cat);
                ls = recomendacionDAO.build().findByCat(tempCat);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ls;
    }

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

    public List<Recomendacion> findByRange(Double min, Double max) {
        List<Recomendacion> ls = new ArrayList<>();
        try {
            if (!(min > max || min < 0 )) {
                ls = recomendacionDAO.build().findByRange(min, max);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

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

    @Override
    public boolean validate(Recomendacion entity) {
        boolean result = false;
        id = entity.getId();
        descripcion = entity.getDescripcion();
        cat = entity.getIdCategoria();

        if (descripcion != null && descripcion.isEmpty()) {
            Categoria tempCat = categoriaService.build().findByPK(cat);
            if (tempCat != null) {
                entity.setIdCategoria(tempCat);
                result = true;
            }
        }
        return result;
    }

    public static recomendacionService build() {return new recomendacionService();}
}
