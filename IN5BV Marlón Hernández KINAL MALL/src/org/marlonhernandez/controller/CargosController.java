/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
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
 * @date 15/07/2021
 * @time 18:37:08
 * Código Técnico: IN5BV
 *
 *
 */
public class CargosController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;
    @FXML
    private TextField txtNombres;
    @FXML
    private TableColumn colNombres;
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
    private TableView tblCargos;
    @FXML
    private TableColumn colId;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReportar;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView btnRegresar;

    @FXML
    private void selecionarElemento(MouseEvent event) {
        seleccionarElemento();
    }

    @FXML
    private void regresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }



    private enum Operaciones {
        NUEVO, GUARDAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Cargos> listaCargos;

    //Metodo Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    //Declaracion de las Variables FXML

     
    

    //ObservablesList para Listar Cargos
    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Cargos(
                        r.getInt("id"),
                        r.getString("nombre"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaCargos = FXCollections.observableArrayList(lista);
        return listaCargos;
    }

    //Agregar Datos en Cargos
    public void agregarCargos() {

        Departamento registro = new Departamento();
        registro.setNombre(txtNombres.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargos(?)}");
            pstmt.setString(1, registro.getNombre());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Editar Datos en Cargos
    public void editarCargos() {
        Cargos registro = (Cargos) tblCargos.getSelectionModel().getSelectedItem();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setNombre(txtNombres.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargos(?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getNombre());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Eliminar Datos en Cargos
    public void eliminarCargos() {

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargos(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {
        tblCargos.setItems(getCargos());
        colId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombre"));
    }
    
    //Metodo para Validar los Text Field
    public boolean validar(ArrayList<TextField> listaTextField) {
        boolean respuesta = true;

        for (TextField textfield : listaTextField) {
            if (textfield.getText().trim().isEmpty()) {
                return false;
            }

        }
        return respuesta;
    }
    
    //Habilitar Campos de los Text Field
    public void habilitarCampos() {
        txtId.setEditable(true);
        txtNombres.setEditable(true);
    }
    
    //Deshablitar Campos de los Text Field
    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombres.setEditable(false);
    }
    
    //Limpiar Campos de los Text Field
    public void LimpiarCampos() {
        txtId.clear();
        txtNombres.clear();
    }

    //Botones para Guardar, Eliminar, Editar y Reportar
    @FXML
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
                listaTextField.add(txtNombres);

                if (validar(listaTextField)) {
                    editarCargos();
                    LimpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    imgEditar.setImage(new Image("/org/marlonhernandez/resource/images/btn Editar.png"));
                    btnReportar.setText("Reportar");
                    imgReportar.setImage(new Image("/org/marlonhernandez/resource/images/btn Reportar.png"));
                    operacion = Operaciones.NINGUNO;
                    break;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor llene todos los Campos de Texto");
                    alert.show();
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
                imgNuevo.setImage(new Image("/org/marlonhernandez/resource/images/btn Nuevo.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/btn Eliminar.png"));
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                LimpiarCampos();
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
                        eliminarCargos();
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
                imgNuevo.setImage(new Image("/org/marlonhernandez/resource/images/btn Guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/btn Cancelar.png"));
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtNombres.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {

                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtNombres);

                    if (validar(listaTextField)) {
                        btnNuevo.setDisable(false);
                        agregarCargos();
                        cargarDatos();
                        LimpiarCampos();
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

                }

                break;
        }

        System.out.println("Operación: " + operacion);
    }

    @FXML
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
   

    public void seleccionarElemento() {
        try {
            txtId.setText(String.valueOf(((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getId()));
            txtNombres.setText(((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getNombre());
        } catch (NullPointerException e) {

        }
    }


    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarAdministracion();
    }


    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}

