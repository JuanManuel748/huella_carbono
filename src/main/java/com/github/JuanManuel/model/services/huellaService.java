package com.github.JuanManuel.model.services;

import com.github.JuanManuel.model.DAOs.huellaDAO;
import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Huella;
import com.github.JuanManuel.model.entities.Usuario;

import java.math.BigDecimal;
import java.nio.DoubleBuffer;
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

    public List<Huella> findByUserFiltByCat(Usuario u, Categoria c) {
        List<Huella> ls = new ArrayList<>();
        try {
            if (u.getId() != null || u.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    if (c.getId() != null) {
                        Categoria tempCat = categoriaService.build().findByPK(c);
                        if (tempCat != null) {
                            ls = huellaDAO.build().findByUserFiltByCat(tempUser, tempCat);
                        }
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

    public List<Object[]> countByCategorias(Usuario u) {
        List<Object[]> ls = new ArrayList<>();
        try {
            if (u.getId() != null || u.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    ls = huellaDAO.build().countByCategorias(tempUser);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

    public List<Object[]> countByCat(Usuario u, Categoria c) {
        List<Object[]> ls = new ArrayList<>();
        try {
            if (u.getId() != null || u.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    if (c.getId() != null) {
                        Categoria tempCat = categoriaService.build().findByPK(c);
                        if (tempCat != null) {
                            ls = huellaDAO.build().countByCat(tempUser, tempCat);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ls;
    }



    public List<Object[]> groupByWeek(Usuario u) {
        List<Object[]> weeklyImpacts = new ArrayList<>();
        try {
            if (u.getId() != null || u.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    weeklyImpacts = huellaDAO.build().findWeeksByUsr(tempUser);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return weeklyImpacts;
    }

    public List<Object[]> groupByMonth(Usuario u) {
        List<Object[]> monthlyImpacts = new ArrayList<>();
        try {
            if (u.getId() != null || u.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    monthlyImpacts = huellaDAO.build().findMonthsByUsr(tempUser);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return monthlyImpacts;
    }

    public List<Object[]> groupByYear(Usuario u) {
        List<Object[]> yearlyImpacts = new ArrayList<>();
        try {
            if (u.getId() != null || u.getEmail() != null) {
                Usuario tempUser = usuarioService.build().findByPK(u);
                if (tempUser != null) {
                    yearlyImpacts = huellaDAO.build().findYearsByUsr(tempUser);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return yearlyImpacts;
    }


    public List<Object[]> statsByWeek(Usuario u) {
        List<Object[]> weeklyStats = new ArrayList<>();
        try {
            List<Object[]> weeks = groupByWeek(u);

            for (Object[] o : weeks) {
                String startDate = o[0].toString();
                double impact = Double.parseDouble(o[1].toString());
                LocalDate date = LocalDate.parse(startDate);
                int x = date.getDayOfWeek().getValue();
                LocalDate weekStart = date.minusDays(x - 1);
                LocalDate weekEnd = weekStart.plusDays(6);
                String week = weekStart.getDayOfMonth() + "/" + weekStart.getMonthValue() + " - " + weekEnd.getDayOfMonth() + "/" + weekEnd.getMonthValue() + " de " + weekEnd.getYear();
                weeklyStats.add(new Object[]{week, impact});
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return weeklyStats;
    }

    public List<Object[]> statsByMonth(Usuario u) {
        List<Object[]> monthlyStats = new ArrayList<>();
        try {
            List<Object[]> months = groupByMonth(u);

            for (Object[] o : months) {
                int year = Integer.parseInt(o[0].toString());
                LocalDate monthV = LocalDate.of(year, Integer.parseInt(o[1].toString()), 1);
                String month = monthV.getMonth().toString() + " de " + year;
                double impact = Double.parseDouble(o[2].toString());
                monthlyStats.add(new Object[]{month, impact});
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return monthlyStats;
    }

    public List<Object[]> statsByYear(Usuario u) {
        List<Object[]> yearlyStats = new ArrayList<>();
        try {
            List<Object[]> years = groupByYear(u);

            for (Object[] o : years) {
                int year = Integer.parseInt(o[0].toString());
                double impact = Double.parseDouble(o[1].toString());
                yearlyStats.add(new Object[]{year, impact});
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return yearlyStats;
    }

    public List<Object[]> statsByDay(Usuario u) {
        List<Object[]> dailyStats = new ArrayList<>();
        try {
            List<Huella> huellas = findByUser(u);
            for (Huella h : huellas) {
                LocalDate date = h.getFecha();
                double impact = h.getValor().doubleValue();
                dailyStats.add(new Object[]{date, impact});
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dailyStats;
    }



    public static huellaService build() {
        return new huellaService();
    }
}
