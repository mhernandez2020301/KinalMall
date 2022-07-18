/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;
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
import javafx.scene.input.KeyEvent;
import org.marlonhernandez.controller.*;
import org.marlonhernandez.db.*;
import org.marlonhernandez.system.*;
import java.lang.Integer;
import javafx.scene.image.ImageView;

        
/**
 *
 * @author Marlon PC
 * @date 16/06/2021
 * @time 12:59:11
 * Código Técnico: IN5BV
 *
 *
 */
public class DepartamentoController implements Initializable {

    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtNombre;
    @FXML private ImageView ImgNuevo;
    @FXML private ImageView ImgEliminar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    private Principal escenarioPrincipal;

    @FXML
    private void regresarmenu(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Departamento> listaDepartamentos;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    private Button btnReportar;

    @FXML
    private Button btnRegresarClientes;

    @FXML
    private TableView tblDepartamentos;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNombre;

    @FXML
    private TextField txtId;

    private TextField txtnombre;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    public ObservableList<Departamento> getDepartamentos() {
        ArrayList<Departamento> lista = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Departamento(
                        r.getInt("id"),
                        r.getString("nombre"))
                );

            }

            r.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listaDepartamentos = FXCollections.observableArrayList(lista);

        return listaDepartamentos;
    }

    public void cargarDatos() {
        tblDepartamentos.setItems(getDepartamentos());
        colId.setCellValueFactory(new PropertyValueFactory<Departamento, Integer>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Departamento, String>("nombre"));
    }

    public void seleccionarElemento() {
        txtId.setText(java.lang.String.valueOf(((Departamento) tblDepartamentos.getSelectionModel().getSelectedItem()).getId()));
        txtnombre.setText(((Departamento) tblDepartamentos.getSelectionModel().getSelectedItem()).getNombre());
    }

    public void agregarDepartamentos() {

        Departamento registro = new Departamento();
        registro.setNombre(txtnombre.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDepartamentos(?)}");
            pstmt.setString(1, registro.getNombre());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarDepartamentos() {

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDepartamentos(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarDepartamentos() {
        Departamento registro = new Departamento();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setNombre(txtnombre.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDepartamentos(?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getNombre());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void habilitarCampos() {
        txtId.setEditable(true);
        txtnombre.setEditable(true);
    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtnombre.setEditable(false);
    }

    public void LimpiarCampos() {
        txtId.clear();
        txtnombre.clear();
    }

    @FXML
    void editar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                btnEditar.setText("Actualizar");
                btnReportar.setText("Cancelar");
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                operacion = Operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un elemento para Editarlo");
                } else {
                    editarDepartamentos();
                    LimpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    btnReportar.setText("Reportar");
                    operacion = Operaciones.NINGUNO;
                    break;
                }
        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void eliminar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
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
                        eliminarDepartamentos();
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
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtnombre.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes Ingresar Datos");
                } else {
                    btnNuevo.setDisable(false);
                    agregarDepartamentos();
                    cargarDatos();
                    LimpiarCampos();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReportar.setDisable(false);
                    operacion = Operaciones.NINGUNO;
                }

                break;
        }
        System.out.println("Operación: " + operacion);
    }

    void reportar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReportar.setText("Reprotar");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                btnReportar.setText("Reportar");
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
    void selecionarElemento(MouseEvent event) {
        seleccionarElemento();
    }

    @FXML
    void regresarClientes(ActionEvent event) {
        escenarioPrincipal.mostrarClientes();
    }

}
