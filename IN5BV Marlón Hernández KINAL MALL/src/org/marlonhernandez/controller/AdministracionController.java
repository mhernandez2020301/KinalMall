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
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.marlonhernandez.db.*;
import org.marlonhernandez.system.*;
import java.sql.PreparedStatement;
import org.marlonhernandez.bean.*;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Marlon PC
 * @date 4/06/2021
 * @time 13:58:27
 * Código Técnico: IN5BV
 *
 *
 */
public class AdministracionController implements Initializable{

    
    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Administracion> listaAdministracion;
    
    private Principal escenarioPrincipal;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TableView tblAdministracion;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTelefono;
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }



    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }    

    @FXML
    private void regresarmenu (MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }



    public ObservableList <Administracion> getAdministracion() {

        ArrayList<Administracion> listado = new ArrayList <Administracion>();
        try {
            //CallableStatement stmt;
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                listado.add(new Administracion(
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")
                )
                );

            }

            resultado.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listaAdministracion = FXCollections.observableArrayList(listado);

        return listaAdministracion;

    }

    public void cargarDatos() {
        tblAdministracion.setItems(getAdministracion());
        colId.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("id"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Administracion, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Administracion, String>("telefono"));

    }

    public void agregarAdministracion() {

        Administracion registro = new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());

        try {
            //CallableStatement stmt;
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarAdministracion(?,?)}");
            stmt.setString(1, registro.getDireccion());
            stmt.setString(2, registro.getTelefono());
            //stmt.executeUpdate();
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void habilitarCampos() {
        txtId.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        btnNuevo.setDisable(false);

    }

    public void desabilitarControles() {
        txtId.setEditable(true);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        btnNuevo.setDisable(false);
    }

    public void limpiarCampos() {
        txtId.clear();
        txtDireccion.clear();
        txtTelefono.clear();
    }

    private void eliminarAdministracion() {

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarAdministracion(?)}");
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            stmt.execute();
            System.out.println(stmt.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editarAdministracion() {

        Administracion registro = new Administracion();

        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarAdministracion(?, ?, ?)}");
            stmt.setInt(1, registro.getId());
            stmt.setString(2, registro.getDireccion());
            stmt.setString(3, registro.getTelefono());
            stmt.execute();
            System.out.println(stmt.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selecionarElemento() {
        txtId.setText(String.valueOf(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getId()));
        txtDireccion.setText(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion());
        txtTelefono.setText(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono());
    }

    public void habilitarGuardar() {
        if (!txtDireccion.getText().chars().equals(btnNuevo) && !txtTelefono.getText().chars().equals(btnNuevo)) {
            btnNuevo.setDisable(true);
            desabilitarControles();
                JOptionPane.showMessageDialog(null, "Debes Ingresar Datos");
            } else {
                btnNuevo.setDisable(false);
                habilitarCampos();
                agregarAdministracion();
                JOptionPane.showMessageDialog(null, "Tus Datos fueron Ingresados");
                
            }
    }

    @FXML
    private void selecionarElemento(MouseEvent event) {
        selecionarElemento();
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
                editarAdministracion();
                limpiarCampos();
                desabilitarControles();
                cargarDatos();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                operacion = Operaciones.NINGUNO;
                break;
        }
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
                limpiarCampos();
                desabilitarControles();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: 
                if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se pude realizar esta accion");
                } else {

                int a = JOptionPane.showConfirmDialog(null, "Esta borrando un Registro, desea continuar: ");

                if (JOptionPane.OK_OPTION == a) {
                eliminarAdministracion();
                limpiarCampos();
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
                if (txtDireccion.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes Ingresar Datos");
                } else {
                    btnNuevo.setDisable(false);
                    agregarAdministracion();
                    cargarDatos();
                    limpiarCampos();
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
    void reporte(ActionEvent event) {
        
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReporte.setText("Reprotar");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                desabilitarControles();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: 
                btnReporte.setText("Reportar");
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                desabilitarControles();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;
    
        }
        System.out.println("Operación: " + operacion);
    }
}
