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
                if (entity.getId() == null) {
                    if (huellaDAO.build().insert(entity)) {
                        result = true;
                    }
                } else {
                    Huella tempHu = huellaDAO.build().findByPK(entity);
                    if (tempHu == null) {
                        if (huellaDAO.build().insert(entity)) {
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
            if (pk.getId() != null) {
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
                    ls = huellaDAO.build().findByUser(tempUser);
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
                    ls = huellaDAO.build().findByAct(tempAct);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    public List<Huella> findByDateRange(LocalDate min, LocalDate max, Usuario u) {
        List<Huella> ls = new ArrayList<>();
        try {
            if (!min.isAfter(max) && min.isAfter(LocalDate.MIN) && max.isBefore(LocalDate.MAX)) {
                if ((u != null) && (u.getId() != null || u.getEmail() != null)) {
                    Usuario tempUser = usuarioService.build().findByPK(u);
                    if (tempUser != null) {
                        ls = huellaDAO.build().findByDateRange(min, max, tempUser);
                    }
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
        user = entity.getIdUsuario();
        act = entity.getIdActividad();
        user = usuarioService.build().findByPK(user);
        act = actividadService.build().findByPK(act);
        valor = entity.getValor();
        unidad = entity.getUnidad();
        fecha = entity.getFecha();

        if (user != null && act != null && unidad!= null && !unidad.isEmpty() && fecha != null) {
            if (valor.compareTo(BigDecimal.ZERO) > 0) {
                result = true;
            }
        }
        return result;
    }

    public static huellaService build() {
        return new huellaService();
    }
}
