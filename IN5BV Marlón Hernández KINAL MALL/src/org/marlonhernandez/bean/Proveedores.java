/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.bean;

import java.math.BigDecimal;

/**
 *
 * @author Marlon PC
 * @date 9/07/2021
 * @time 12:46:08
 * Código Técnico: IN5BV
 *
 *
 */
public class Proveedores {

    private int id;
    private String nit;
    private String servicioPrestado;
    private String telefono;
    private String direccion;
    private BigDecimal saldoFavor;
    private BigDecimal saldoContra;

    public Proveedores() {
    }

    public Proveedores(int id, String nit, String servicioPrestado, String telefono, String direccion, BigDecimal saldoFavor, BigDecimal saldoContra) {
        this.id = id;
        this.nit = nit;
        this.servicioPrestado = servicioPrestado;
        this.telefono = telefono;
        this.direccion = direccion;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(BigDecimal saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public BigDecimal getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(BigDecimal saldoContra) {
        this.saldoContra = saldoContra;
    }

    @Override
    public String toString() {
        return  id + "|" + " " +servicioPrestado;
    }
    
}
