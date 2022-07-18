/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.marlonhernandez.bean.TipoCliente;
import org.marlonhernandez.db.Conexion;
import org.marlonhernandez.system.Principal;
import org.marlonhernandez.controller.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Marlon PC
 * @date 16/06/2021
 * @time 12:59:39
 * Código Técnico: IN5BV
 *
 *
 */
public class TipoClientesController implements Initializable {

    private Principal escenarioPrincipal;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtDescripcion;



    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<TipoCliente> listaTipoCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnRegresarClientes;

    @FXML
    private TableView tblTipoClientes;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TextField txtId;


    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    public ObservableList<TipoCliente> getTipoCLiente() {

        ArrayList<TipoCliente> lista = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoCliente()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new TipoCliente(
                        r.getInt("id"),
                        r.getString("descripcion"))
                );

            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaTipoCliente = FXCollections.observableArrayList(lista);

        return listaTipoCliente;
    }

    public void cargarDatos() {
        tblTipoClientes.setItems(getTipoCLiente());
        colId.setCellValueFactory(new PropertyValueFactory<TipoCliente, Integer>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoCliente, String>("descripcion"));
    }

    public void selccionarElemento() {
        txtId.setText(String.valueOf(((TipoCliente) tblTipoClientes.getSelectionModel().getSelectedItem()).getId()));
        txtDescripcion.setText(((TipoCliente) tblTipoClientes.getSelectionModel().getSelectedItem()).getDescripcion());
    }

    public void agregarTipoCliente() {
        TipoCliente registro = new TipoCliente();
        registro.setDescripcion(txtDescripcion.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoCliente(?)}");
            pstmt.setString(1, registro.getDescripcion());
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarTipoCliente() {

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoCliente(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarTipoCliente() {

        TipoCliente registro = new TipoCliente();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setDescripcion(txtDescripcion.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoCliente(?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getDescripcion());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void habilitarCampos() {
        txtId.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtDescripcion.setEditable(false);
    }

    public void LimpiarCampos() {
        txtId.clear();
        txtDescripcion.clear();
    }

    @FXML
    void selecionarElemento(MouseEvent event) {
        selccionarElemento();
    }

    @FXML
    void eliminar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Eliminar
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se pude realizar esta accion");
                } else {

                    int a = JOptionPane.showConfirmDialog(null, "Esta borrando un Registro, desea continuar: ");

                    if (JOptionPane.OK_OPTION == a) {
                        eliminarTipoCliente();
                        LimpiarCampos();
                        cargarDatos();
                    } else {

                    }
                }
                break;
        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void nuevo(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtDescripcion.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes Ingresar Datos");
                } else {
                    btnNuevo.setDisable(false);
                    agregarTipoCliente();
                    cargarDatos();
                    LimpiarCampos();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    operacion = Operaciones.NINGUNO;
                }

                break;
        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void editar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                operacion = Operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                editarTipoCliente();
                LimpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reportar");
                operacion = Operaciones.NINGUNO;
                break;
        }
    }
    @FXML
    private void reporte(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReporte.setText("Reprotar");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                btnReporte.setText("Reportar");
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    private void regresarclientes(ActionEvent event) {
        escenarioPrincipal.mostrarClientes();
    }

    @FXML
    private void selecionarElemento(KeyEvent event) {
        selccionarElemento();
    }
    
    @FXML
    private void regresarmenu(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }
}
