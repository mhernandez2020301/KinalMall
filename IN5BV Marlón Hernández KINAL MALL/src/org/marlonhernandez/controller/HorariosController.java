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
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.marlonhernandez.db.*;
import org.marlonhernandez.bean.*;
import org.marlonhernandez.db.Conexion;
import org.marlonhernandez.system.Principal;


/**
 *
 * @author Marlon PC
 * @date 9/07/2021
 * @time 19:48:29
 * Código Técnico: IN5BV
 *
 *
 */
public class HorariosController implements Initializable {

    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Horarios> listaHorarios;

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
    private TableView tblHorarios;

    @FXML
    private TableColumn colID;

    @FXML
    private TableColumn colHorarioEntrada;

    @FXML
    private TableColumn colHorarioSalida;

    @FXML
    private TableColumn colLunes;

    @FXML
    private TableColumn colMartes;

    @FXML
    private TableColumn colMiercoles;

    @FXML
    private TableColumn colJueves;

    @FXML
    private TableColumn colViernes;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportar;

    @FXML
    private ImageView imgReportar;

    @FXML
    private CheckBox cbxLunes;

    @FXML
    private CheckBox cbxmartes;

    @FXML
    private CheckBox cbxMiercoles;

    @FXML
    private CheckBox cbxJueves;

    @FXML
    private CheckBox cbxViernes;
    @FXML
    private JFXTimePicker tpHorarioEntrada;

    @FXML
    private JFXTimePicker tpHorarioSalida;

    @FXML
    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    @FXML
    void seleccionarElmento(MouseEvent event) {
        seleccionarElemento();
    }

    public ObservableList<Horarios> getHorarios() {
        ArrayList<Horarios> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Horarios(
                        r.getInt("id"),
                        r.getTime("horarioEntrada"),
                        r.getTime("horarioSalida"),
                        r.getBoolean("lunes"),
                        r.getBoolean("martes"),
                        r.getBoolean("miercoles"),
                        r.getBoolean("jueves"),
                        r.getBoolean("viernes"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaHorarios = FXCollections.observableArrayList(lista);
        return listaHorarios;
    }

    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colID.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("id"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horarios, Time>("horarioEntrada"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horarios, Time>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios, Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios, Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horarios, Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios, Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios, Boolean>("viernes"));
    }

    public void seleccionarElemento() {
        try {
            txtId.setText(String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId()));

            tpHorarioEntrada.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioEntrada().toLocalTime());
            tpHorarioSalida.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioSalida().toLocalTime());

            cbxLunes.setText(String.valueOf((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isLunes())));
            cbxmartes.setText(String.valueOf((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isMartes())));
            cbxMiercoles.setText(String.valueOf((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles())));
            cbxJueves.setText(String.valueOf(((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isJueves()))));
            cbxViernes.setText(String.valueOf((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isViernes())));

            cbxLunes.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isLunes()));
            cbxmartes.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isMartes()));
            cbxMiercoles.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles()));
            cbxJueves.setSelected((((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isJueves()));
            cbxViernes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).isViernes());

            cbxLunes.setText(cambiarNombreBox(cbxLunes));
            cbxmartes.setText(cambiarNombreBox(cbxmartes));
            cbxMiercoles.setText(cambiarNombreBox(cbxMiercoles));
            cbxJueves.setText(cambiarNombreBox(cbxJueves));
            cbxViernes.setText(cambiarNombreBox(cbxViernes));

        } catch (NullPointerException e) {

        }
    }

    public String cambiarNombreBox(CheckBox asdBox) {
        String estado = null;

        if (asdBox.isSelected()) {
            estado = "Llego a Trabajar";
        } else {
            estado = "No llego a Trabajar";
        }

        return estado;
    }

    /*public void valirdarhorarios() {
        Pattern pattern = Pattern.compile("([01][0-9]|[2][0123]):([0-5][0-9]):([0-5][0-9])");
    }*/
    public void agregarHorarios() {
        Horarios horario = new Horarios();
        horario.setHorarioEntrada(Time.valueOf(tpHorarioEntrada.getValue()));
        horario.setHorarioSalida(Time.valueOf(tpHorarioSalida.getValue()));
        horario.setLunes(cbxLunes.isSelected());
        horario.setMartes(cbxmartes.isSelected());
        horario.setMiercoles(cbxMiercoles.isSelected());
        horario.setJueves(cbxJueves.isSelected());
        horario.setViernes(cbxViernes.isSelected());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorarios(?,?,?,?,?,?,?)}");
            pstmt.setTime(1, horario.getHorarioEntrada());
            pstmt.setTime(2, horario.getHorarioSalida());
            pstmt.setBoolean(3, horario.isLunes());
            pstmt.setBoolean(4, horario.isMartes());
            pstmt.setBoolean(5, horario.isMiercoles());
            pstmt.setBoolean(6, horario.isJueves());
            pstmt.setBoolean(7, horario.isViernes());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminarHorarios() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarHorarios(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarHorarios() {
        Horarios horario = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();

        horario.setId(Integer.valueOf(txtId.getText()));
        horario.setHorarioEntrada(Time.valueOf(tpHorarioEntrada.getValue()));
        horario.setHorarioSalida(Time.valueOf(tpHorarioSalida.getValue()));
        horario.setLunes(cbxLunes.isSelected());
        horario.setMartes(cbxmartes.isSelected());
        horario.setMiercoles(cbxMiercoles.isSelected());
        horario.setJueves(cbxJueves.isSelected());
        horario.setViernes(cbxViernes.isSelected());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarHorarios(?,?,?,?,?,?,?,?)}");
            pstmt.setInt(1, horario.getId());
            pstmt.setTime(2, horario.getHorarioEntrada());
            pstmt.setTime(3, horario.getHorarioSalida());
            pstmt.setBoolean(4, horario.isLunes());
            pstmt.setBoolean(5, horario.isMartes());
            pstmt.setBoolean(6, horario.isMiercoles());
            pstmt.setBoolean(7, horario.isJueves());
            pstmt.setBoolean(8, horario.isViernes());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {
        txtId.clear();
        tpHorarioEntrada.getEditor().clear();
        tpHorarioSalida.getEditor().clear();
        cbxLunes.setSelected(false);
        cbxmartes.setSelected(false);
        cbxMiercoles.setSelected(false);
        cbxJueves.setSelected(false);
        cbxViernes.setSelected(false);
    }

    public void habilitarCampos() {
        txtId.setEditable(true);
        tpHorarioEntrada.setDisable(false);
        tpHorarioSalida.setDisable(false);
        cbxLunes.setSelected(false);
        cbxmartes.setSelected(false);
        cbxMiercoles.setSelected(false);
        cbxJueves.setSelected(false);
        cbxViernes.setSelected(false);
    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        tpHorarioEntrada.setDisable(true);
        tpHorarioSalida.setDisable(true);
        cbxLunes.setSelected(false);
        cbxmartes.setSelected(false);
        cbxMiercoles.setSelected(false);
        cbxJueves.setSelected(false);
        cbxViernes.setSelected(false);
    }

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
                listaTextField.add(txtId);

                if (tpHorarioEntrada != null) {

                    if (tpHorarioSalida != null) {
                        editarHorarios();
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
                        alert.setContentText("Por favor Ingrese el Horario Salida");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor Ingrese el Horario Entrada");
                    alert.show();
                }

                break;
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
                limpiarCampos();
                deshabilitarCampos();
                operacion = HorariosController.Operaciones.NINGUNO;
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
                        eliminarHorarios();
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
                imgNuevo.setImage(new Image("/org/marlonhernandez/resource/images/btn Guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/btn Cancelar.png"));
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (!"".equals(txtId.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {

                    if (tpHorarioEntrada != null) {

                        if (tpHorarioSalida != null) {

                            agregarHorarios();
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
                            alert.setContentText("Por favor Ingrese el Horario Salida");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("Por favor Ingrese el Horario Entrada");
                        alert.show();
                    }

                    break;
                }
                System.out.println("Operación: " + operacion);
        }
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

    @FXML
    void chechkViernes(ActionEvent event) {
        cbxViernes.setText(String.valueOf(cbxViernes.isSelected()));
    }

    @FXML
    void checkJueves(ActionEvent event) {
        cbxJueves.setText(String.valueOf(cbxJueves.isSelected()));
    }

    @FXML
    void checkLunes(ActionEvent event) {
        cbxLunes.setText(String.valueOf(cbxLunes.isSelected()));
    }

    @FXML
    void checkMartes(ActionEvent event) {
        cbxmartes.setText(String.valueOf(cbxmartes.isSelected()));
    }

    @FXML
    void checkMiercoles(ActionEvent event) {
        cbxMiercoles.setText(String.valueOf(cbxMiercoles.isSelected()));
    }

}
