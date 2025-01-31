package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.huellaDAO;
import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Huella;
import com.github.JuanManuel.model.entities.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class huellaService implements service<Huella>{
    private Integer id;
    private Usuario user;
    private Actividad act;
    private BigDecimal valor;
    private String unidad;
    private LocalDate fecha;

    @Override
    public boolean insert(Huella entity) {
        boolean result = false;
        try {
            if (validate(entity)) {
                Huella tempHu = huellaDAO.build().findByPK(entity);
                if (tempHu == null) {
                    if (huellaDAO.build().insert(entity)) {
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
    public boolean update(Huella entity) {
        boolean result = false;
        try {
            if (entity.getId() != null) {
                Huella tempHu = huellaDAO.build().findByPK(entity);
                if (tempHu != null) {
                    if (validate(entity)) {
                        if(huellaDAO.build().update(entity)) {
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

    @Override
    public boolean delete(Huella entity) {
        boolean result = true;
        try {
            if (entity.getId() != null) {
                Huella tempHu = huellaDAO.build().findByPK(entity);
                if (tempHu != null) {
                    entity = tempHu;
                    if (huellaDAO.build().delete(entity)) {
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
    public Huella findByPK(Huella pk) {
        Huella result = new Huella();
        try {
            if (pk != null && pk.getId() != null) {
                result = huellaDAO.build().findByPK(pk);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Huella> findAll() {
        List<Huella> ls = new ArrayList<>();
        try {
            ls = huellaDAO.build().findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }


    public List<Huella> findByUser(Usuario u) {
        List<Huella> ls = new ArrayList<>();
        try {
            if (u != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    u = tempUser;
                    ls = huellaDAO.build().findByUser(u);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    public List<Huella> findByAct(Actividad act) {
        List<Huella> ls = new ArrayList<>();
        try {
            if (act != null) {
                Actividad tempAct = actividadService.build().findByPK(act);
                if (tempAct != null) {
                    act = tempAct;
                    ls = huellaDAO.build().findByAct(act);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    public List<Huella> findByDateRange(LocalDate min, LocalDate max) {
        List<Huella> ls = new ArrayList<>();
        try {
            if (!min.isAfter(max)) {
                if (min.isAfter(LocalDate.MIN) && max.isBefore(LocalDate.MAX)) {
                    ls = huellaDAO.build().findByDateRange(min, max);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    @Override
    public boolean validate(Huella entity) {
        boolean result = false;

        user = usuarioService.build().findByPK(user);
        act = actividadService.build().findByPK(act);
        valor = entity.getValor();
        unidad = entity.getUnidad();
        fecha = entity.getFecha();

        String tempUni = "";
        Categoria tempCat = categoriaService.build().findByPK(act.getIdCategoria());
        tempUni = tempCat.getUnidad();

        if (user != null && act != null && unidad.equals(tempUni)) {
            if (valor.compareTo(BigDecimal.ZERO) > 0) {
                if (fecha.isBefore(LocalDate.now())) {
                    result = true;
                }
            }
        }
        return result;
    }

    public static huellaService build() {
        return new huellaService();
    }
}
