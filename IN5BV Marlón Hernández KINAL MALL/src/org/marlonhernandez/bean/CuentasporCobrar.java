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
 * @time 12:45:59
 * Código Técnico: IN5BV
 *
 *
 */
public class CuentasporCobrar {

    private int id;
    private String numeroFactura;
    private int anio;
    private int mes;
    private BigDecimal valorNetoPago;
    private String estadoPago;
    private int idAdministracion;
    private int idCliente;
    private int idLocal;

    public CuentasporCobrar() {
    }

    public CuentasporCobrar(int id, String numeroFactura, int anio, int mes, BigDecimal valorNetoPago, String estadoPago, int idAdministracion, int idCliente, int idLocal) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.anio = anio;
        this.mes = mes;
        this.valorNetoPago = valorNetoPago;
        this.estadoPago = estadoPago;
        this.idAdministracion = idAdministracion;
        this.idCliente = idCliente;
        this.idLocal = idLocal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public BigDecimal getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(BigDecimal valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public int getIdAdministracion() {
        return idAdministracion;
    }

    public void setIdAdministracion(int idAdministracion) {
        this.idAdministracion = idAdministracion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    @Override
    public String toString() {
        return "CuentasporCobrar{" + "id=" + id + ", numeroFactura=" + numeroFactura + ", anio=" + anio + ", mes=" + mes + ", valorNetoPago=" + valorNetoPago + ", estadoPago=" + estadoPago + ", idAdministracion=" + idAdministracion + ", idCliente=" + idCliente + ", idLocal=" + idLocal + '}';
    }

    
}
