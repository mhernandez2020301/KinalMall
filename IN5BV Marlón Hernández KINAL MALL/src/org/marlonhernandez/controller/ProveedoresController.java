/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.marlonhernandez.db.*;
import org.marlonhernandez.bean.*;
import org.marlonhernandez.system.Principal;

/**
 *
 * @author Marlon PC
 * @date 9/07/2021
 * @time 17:32:40
 * Código Técnico: IN5BV
 *
 *
 */
public class ProveedoresController implements Initializable {

    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Proveedores> listaProveedores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReportar;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colNIT;
    @FXML
    private TableColumn colServicioPrestado;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colSaldoAFavor;
    @FXML
    private TableColumn colSaldoEnContra;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TextField txtServicioPrestado;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtSaldoEnContra;
    @FXML
    private TextField txtSaldoAFavor;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;  
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgReportar;
     

    @FXML
    private void selecionarElemento(MouseEvent event) {
    }

    @FXML
    private void selecionarElemento(KeyEvent event) {
        seleccionarElemento();
    }
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Proveedores(
                        r.getInt("id"),
                        r.getString("nit"),
                        r.getString("servicioPrestado"),
                        r.getString("telefono"),
                        r.getString("direccion"),
                        r.getBigDecimal("saldoFavor"),
                        r.getBigDecimal("saldoContra"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaProveedores = FXCollections.observableArrayList(lista);
        return listaProveedores;
    }

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colId.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("id"));
        colNIT.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nit"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("telefono"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("servicioPrestado"));
        colSaldoAFavor.setCellValueFactory(new PropertyValueFactory<Proveedores, BigDecimal>("saldoFavor"));
        colSaldoEnContra.setCellValueFactory(new PropertyValueFactory<Proveedores, BigDecimal>("saldoContra"));
    }

    public void seleccionarElemento() {
        try {
            txtId.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getId()));
            txtNit.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNit());
            txtDireccion.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getTelefono());
            txtServicioPrestado.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            colSaldoAFavor.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor())));
            txtSaldoEnContra.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra())));
        } catch (NullPointerException e) {

        }

    }

    public void agregarProveedores() {
        Proveedores registro = new Proveedores();
        registro.setNit(txtNit.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setSaldoContra(new BigDecimal(txtSaldoEnContra.getText()));
        registro.setSaldoFavor(new BigDecimal(txtSaldoAFavor.getText()));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarProveedores(?, ?, ?, ?, ?, ?)}");
            pstmt.setString(1, registro.getNit());
            pstmt.setString(2, registro.getServicioPrestado());
            pstmt.setString(3, registro.getTelefono());
            pstmt.setString(4, registro.getDireccion());
            pstmt.setBigDecimal(5, registro.getSaldoFavor());
            pstmt.setBigDecimal(6, registro.getSaldoContra());

            System.out.println(pstmt);

            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminarProveedores() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();
            System.out.println(pstmt.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EditarLocales() {
        Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
        registro.setId(Integer.valueOf(txtId.getText()));
        registro.setNit(txtNit.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setSaldoContra(new BigDecimal(txtSaldoEnContra.getText()));
        registro.setSaldoFavor(new BigDecimal(txtSaldoAFavor.getText()));

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedores( ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getNit());
            pstmt.setString(3, registro.getServicioPrestado());
            pstmt.setString(4, registro.getTelefono());
            pstmt.setString(5, registro.getDireccion());
            pstmt.setBigDecimal(6, registro.getSaldoFavor());
            pstmt.setBigDecimal(7, registro.getSaldoContra());

            pstmt.executeUpdate();
            System.out.println(pstmt.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void limpiarCampos() {
        txtId.clear();
        txtSaldoAFavor.clear();
        txtSaldoEnContra.clear();
        txtNit.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtServicioPrestado.clear();
    }

    public void habilitarCampos() {
        txtId.setEditable(true);
        txtSaldoAFavor.setEditable(true);
        txtSaldoEnContra.setEditable(true);
        txtNit.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtServicioPrestado.setEditable(true);
    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtSaldoAFavor.setEditable(false);
        txtSaldoEnContra.setEditable(false);
        txtNit.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtServicioPrestado.setEditable(false);
    }

    public boolean validar(ArrayList<TextField> listaTextField) {
        boolean respuesta = true;

        for (TextField textfield : listaTextField) {
            if (textfield.getText().trim().isEmpty()) {
                return false;
            }

        }
        return respuesta;
    }

    void nuevo (ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/marlonhernandez/resource/images/btn Guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/btn Cancelar.png"));
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtSaldoAFavor.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {
                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtSaldoAFavor);
                    listaTextField.add(txtSaldoEnContra);
                    listaTextField.add(txtNit);
                    listaTextField.add(txtTelefono);
                    listaTextField.add(txtDireccion);
                    listaTextField.add(txtServicioPrestado);

                    if (validar(listaTextField)) {

                        btnNuevo.setDisable(false);
                        agregarProveedores();
                        cargarDatos();
                        limpiarCampos();
                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image("/org/marlonhernandez/resource/images/btn Nuevo.png"));
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/btn Eliminar.png"));
                        btnEditar.setDisable(false);
                        btnReportar.setDisable(false);
                        operacion = Operaciones.NINGUNO;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("Por favor llene todos los Campos de Texto");
                        alert.show();
                    }

                    break;
                }
        }
        System.out.println("Operación: " + operacion);
    }

    void editar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                if ("".equals(txtId.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("No se pude realizar esta accion, " + " " + "Debe Seleccionar un Elemento");
                    alert.show();

                } else {
                    limpiarCampos();
                    habilitarCampos();
                    btnEditar.setText("Actualizar");
                    imgEditar.setImage(new Image("/org/marlonhernandez/resource/images/btn Actualizar.png"));
                    btnReportar.setText("Cancelar");
                    imgReportar.setImage(new Image("/org/marlonhernandez/resource/images/btn Cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    operacion = Operaciones.ACTUALIZAR;
                }
                break;
            case ACTUALIZAR:

                ArrayList<TextField> listaTextField = new ArrayList<>();
                listaTextField.add(txtId);
                listaTextField.add(txtSaldoAFavor);
                listaTextField.add(txtSaldoEnContra);
                listaTextField.add(txtNit);
                listaTextField.add(txtTelefono);
                listaTextField.add(txtDireccion);
                listaTextField.add(txtServicioPrestado);

                if (validar(listaTextField)) {
                    EditarLocales();
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    imgEditar.setImage(new Image("/org/marlonhernandez/resource/images/btn Editar.png"));
                    btnReportar.setText("Reportar");
                    imgReportar.setImage(new Image("/org/marlonhernandez/resource/images/btn Cancelar.png"));
                    operacion = Operaciones.NINGUNO;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor llene todos los Campos de Texto");
                    alert.show();
                }

                break;
        }

        System.out.println(
                "Operación: " + operacion);
    }

    void eliminar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/marlonhernandez/resource/images/btn Nuevo.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/btn Eliminar.png"));
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                limpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Eliminar
                if (txtId.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Seleccionar un registro para poder Eliminarlo.");
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("KINAL MALL");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que desea eliminar este registro?");

                    Optional<ButtonType> a = alert.showAndWait();
                    if (a.get() == ButtonType.OK) {
                        eliminarProveedores();
                        limpiarCampos();
                        cargarDatos();
                    } else {

                    }
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
                imgEditar.setImage(new Image("/org/marlonhernandez/resource/images/btn Editar.png"));
                btnReportar.setText("Reportar");
                imgReportar.setImage(new Image("/org/marlonhernandez/resource/images/btn Reportar.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                btnReportar.setText("Reportar");
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    void mostrarCuentasporPagar(ActionEvent event) {
        escenarioPrincipal.mostrarCuentasporPagar();
    }



    @FXML
    private void regresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }


}
