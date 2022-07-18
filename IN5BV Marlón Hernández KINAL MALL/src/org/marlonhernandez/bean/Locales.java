/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.bean;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.marlonhernandez.system.Principal;
import org.marlonhernandez.bean.*;
import org.marlonhernandez.controller.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import org.marlonhernandez.db.Conexion;

/**
 *
 * @author Marlon PC
 * @date 9/06/2021
 * @time 13:56:59
 * Código Técnico: IN5BV
 *
 *
 */
public class Locales {
 private int id;
    private BigDecimal saldoFavor;
    private BigDecimal saldoContra;
    private int mesesPendientes;
    private boolean disponibilidad;
    private BigDecimal valorLocal;
    private BigDecimal ValorAdministracion;

    public Locales() {
    }
    

    public Locales(int id, BigDecimal saldoFavor, BigDecimal saldoContra, int mesesPendientes, 
                boolean disponibilidad, BigDecimal valorLocal, BigDecimal ValorAdministracion) {
        this.id = id;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.mesesPendientes = mesesPendientes;
        this.disponibilidad = disponibilidad;
        this.valorLocal = valorLocal;
        this.ValorAdministracion = ValorAdministracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMesesPendientes() {
        return mesesPendientes;
    }

    public void setMesesPendientes(int mesesPendientes) {
        this.mesesPendientes = mesesPendientes;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public BigDecimal getValorLocal() {
        return valorLocal;
    }

    public void setValorLocal(BigDecimal valorLocal) {
        this.valorLocal = valorLocal;
    }

    public BigDecimal getValorAdministracion() {
        return ValorAdministracion;
    }

    public void setValorAdministracion(BigDecimal ValorAdministracion) {
        this.ValorAdministracion = ValorAdministracion;
    }

    @Override
    public String toString() {
        return "Local{" + "id=" + id + ", saldoFavor=" + saldoFavor + ", saldoContra=" + saldoContra + ", mesesPendientes=" 
                    + mesesPendientes + ", disponibilidad=" + disponibilidad + ", valorLocal=" + valorLocal 
                    + ", ValorAdministracion=" + ValorAdministracion + '}';
    }
    
}


