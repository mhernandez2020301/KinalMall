/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.bean;
import java.sql.Time;
/**
 *
 * @author Marlon PC
 * @date 9/07/2021
 * @time 12:45:45
 * Código Técnico: IN5BV
 *
 *
 */
public class Horarios {

    private int id;
    private Time horarioEntrada;
    private Time horarioSalida;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;

    public Horarios() {
    }

    public Horarios(int id, Time horarioEntrada, Time horarioSalida, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes) {
        this.id = id;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Time horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Time getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(Time horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    @Override
    public String toString() {
        return "Horarios{" + "id=" + id + ", horarioEntrada=" + horarioEntrada + ", horarioSalida=" + horarioSalida + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves + ", viernes=" + viernes + '}';
    }

    

}

