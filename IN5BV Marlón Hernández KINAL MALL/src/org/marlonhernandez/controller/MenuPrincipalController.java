         /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.marlonhernandez.system.Principal;
import org.marlonhernandez.system.Principal;

/**
 *
 * @author Marlon PC
 * @date 5/05/2021
 * @time 17:18:15
 * Código Técnico: IN5BV
 *
 *
 */

public class MenuPrincipalController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void mostrarVistaAutor(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarAutor();
    }

    @FXML
    private void mostrarAdministracion(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarAdministracion();
    }

    @FXML
    private void mostrarClientes(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarClientes();
    }

    @FXML
    private void mostrarDepartamentos(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarDepartamentos();
    }

    @FXML
    private void mostrarLocales(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarLocales();
    }

    @FXML
    private void mostrarTipoCLientes(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarTipoCLientes();
    }

    @FXML
    void mostrarProveedores(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarProveedores();
    }

    @FXML
    void mostrarCuentasPorCobrar(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarCuentasporCobrar();
    }

    @FXML
    void mostrarCuentasPorPagar(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarCuentasporPagar();
    }

    void mostrarHorarios(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarHorarios();
    }

    @FXML
    private void mostrarCargos(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarCargos();
    }

    @FXML
    private void mostrarEmpleados(javafx.event.ActionEvent event) {
        escenarioPrincipal.mostrarEmpleados();
    }
}