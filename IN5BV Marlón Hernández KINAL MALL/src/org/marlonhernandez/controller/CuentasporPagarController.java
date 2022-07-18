/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
 * @time 13:12:57
 * Código Técnico: IN5BV
 *
 *
 */
public class CuentasporPagarController implements Initializable {

    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Cuentasporpagar> listaCuentasporPagar;
    private ObservableList<Administracion> listaAdministracion;
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
    private TableView tblCuentasporPagar;

    @FXML
    private TableColumn colID;

    @FXML
    private TableColumn colNumeroFactura;

    @FXML
    private TableColumn colFechaLimitePago;

    @FXML
    private TableColumn colEstadoPago;

    @FXML
    private TableColumn colValorNetoPago;

    @FXML
    private TableColumn colAdministracion;

    @FXML
    private TableColumn colProveedores;

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
    private Button egresarProveedores;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNumeroFac;

    @FXML
    private TextField txtEstadoPago;

    @FXML
    private TextField txtValornetoPago;

    @FXML
    private ComboBox cmbAdmin;

    @FXML
    private ComboBox cmbproveedor;

    @FXML
    private JFXDatePicker dtpickerFechalimite;

    @FXML
    void selecionarElemento(MouseEvent event) {
        seleccionarElemento();
    }

    @FXML
    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    public ObservableList<Cuentasporpagar> getCuentasporPagar() {
        ArrayList<Cuentasporpagar> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Cuentasporpagar(
                        r.getInt("id"),
                        r.getString("numeroFactura"),
                        r.getDate("fechaLimitePago"),
                        r.getString("estadoPago"),
                        r.getBigDecimal("valorNetoPago"),
                        r.getInt("idAdministracion"),
                        r.getInt("idProveedor"))
                );

            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaCuentasporPagar = FXCollections.observableArrayList(lista);
        return listaCuentasporPagar;
    }

    public ObservableList<Administracion> getAdministracion() {
        ArrayList<Administracion> listado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet resultado = null;
        try {

            stmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarAdministracion()}");
            resultado = stmt.executeQuery();

            while (resultado.next()) {
                listado.add(new Administracion(
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")
                )
                );
            }
            listaAdministracion = FXCollections.observableArrayList(listado);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la tabla Administración en la base de datos.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                resultado.close();
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaAdministracion;
    }

    public Administracion buscarAdministracion(int id) {
        Administracion administracion = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarAdministracion(?)}");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                administracion = new Administracion(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar una Administracion con el ID " + id);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return administracion;
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarProveedores()}");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Proveedores(
                        rs.getInt("id"),
                        rs.getString("nit"),
                        rs.getString("servicioPrestado"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getBigDecimal("saldoFavor"),
                        rs.getBigDecimal("saldoContra"))
                );
            }
            listaProveedores = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la tabla Proveedores en la base de datos.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listaProveedores;
    }

    private Proveedores buscarProveedores(int id) {
        Proveedores registro = null;

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                registro = new Proveedores(
                        rs.getInt("id"),
                        rs.getString("nit"),
                        rs.getString("servicioPrestado"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getBigDecimal("saldoFavor"),
                        rs.getBigDecimal("saldoContra"));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registro;
    }

    public void cargarDatos() {
        tblCuentasporPagar.setItems(getCuentasporPagar());
        colID.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, Integer>("id"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, Date>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, BigDecimal>("valorNetoPago"));
        colAdministracion.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, Integer>("idAdministracion"));
        colProveedores.setCellValueFactory(new PropertyValueFactory<Cuentasporpagar, Integer>("idProveedor"));

        cmbAdmin.setItems(getAdministracion());
        cmbproveedor.setItems(getProveedores());

    }

    public void seleccionarElemento() {
        txtId.setText(String.valueOf(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getId()));
        txtNumeroFac.setText(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        txtEstadoPago.setText(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
        txtValornetoPago.setText(String.valueOf(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));

        cmbAdmin.getSelectionModel().select(buscarAdministracion(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getIdAdministracion()));
        cmbproveedor.getSelectionModel().select(buscarProveedores(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getIdProveedor()));
        dtpickerFechalimite.setValue(((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago().toLocalDate());
    }

    private void agregarCuentasPorPagar() {
        Cuentasporpagar registro = new Cuentasporpagar();
        registro.setNumeroFactura(txtNumeroFac.getText());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setValorNetoPago(new BigDecimal(txtValornetoPago.getText()));
        registro.setFechaLimitePago(Date.valueOf(dtpickerFechalimite.getValue()));

        registro.setIdAdministracion(((Administracion) cmbAdmin.getSelectionModel().getSelectedItem()).getId());
        registro.setIdProveedor(((Proveedores) cmbproveedor.getSelectionModel().getSelectedItem()).getId());
        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorPagar(?,?,?,?,?,?)}");
            stmt.setString(1, registro.getNumeroFactura());
            stmt.setDate(2, registro.getFechaLimitePago());
            stmt.setString(3, registro.getEstadoPago());
            stmt.setBigDecimal(4, registro.getValorNetoPago());

            stmt.setInt(5, registro.getIdAdministracion());
            stmt.setInt(6, registro.getIdProveedor());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void EliminarCuentasPorPagar() {
        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentasPorPagar(?)}");
            stmt.setInt(1, ((Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem()).getId());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editarCuentasPorCobrar() {
        Cuentasporpagar registro = (Cuentasporpagar) tblCuentasporPagar.getSelectionModel().getSelectedItem();
        registro.setNumeroFactura(txtNumeroFac.getText());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setFechaLimitePago(Date.valueOf(dtpickerFechalimite.getValue()));
        registro.setValorNetoPago(new BigDecimal(txtValornetoPago.getText()));
        registro.setIdAdministracion(((Administracion) cmbAdmin.getSelectionModel().getSelectedItem()).getId());
        registro.setIdProveedor(((Proveedores) cmbproveedor.getSelectionModel().getSelectedItem()).getId());

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EditarCuentasPorPagar(?, ?, ?, ?, ?, ?,? )}");
            stmt.setInt(1, registro.getId());
            stmt.setString(2, registro.getNumeroFactura());
            stmt.setDate(3, registro.getFechaLimitePago());
            stmt.setString(4, registro.getEstadoPago());
            stmt.setBigDecimal(5, registro.getValorNetoPago());
            stmt.setInt(6, registro.getIdAdministracion());
            stmt.setInt(7, registro.getIdProveedor());

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void habilitarCampos() {
        txtId.setEditable(false);
        txtNumeroFac.setEditable(true);
        txtValornetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
        cmbAdmin.setDisable(false);
        cmbproveedor.setDisable(false);
        dtpickerFechalimite.setDisable(false);

    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNumeroFac.setEditable(false);
        txtValornetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);
        cmbAdmin.setDisable(true);
        cmbproveedor.setDisable(true);
        dtpickerFechalimite.setDisable(false);
    }

    public void limpiarCampos() {
        txtId.clear();
        txtNumeroFac.clear();
        txtValornetoPago.clear();
        txtEstadoPago.clear();
        cmbAdmin.valueProperty().set(null);
        cmbproveedor.valueProperty().set(null);
        dtpickerFechalimite.getEditor().clear();
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
                    imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Actualizar.png"));
                    btnReportar.setText("Cancelar");
                    imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    operacion = Operaciones.ACTUALIZAR;
                }
                break;
            case ACTUALIZAR:

                ArrayList<TextField> listaTextField = new ArrayList<>();
                listaTextField.add(txtId);
                listaTextField.add(txtValornetoPago);
                listaTextField.add(txtEstadoPago);
                listaTextField.add(txtNumeroFac);

                if (validar(listaTextField)) {
                    editarCuentasPorCobrar();
                    deshabilitarCampos();
                    cargarDatos();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                    btnReportar.setText("Reportar");
                    imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
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

    @FXML
    void eliminar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Nuevo.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Eliminar.png"));
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
                        EliminarCuentasPorPagar();
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
                limpiarCampos();
                habilitarCampos();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
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
                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtValornetoPago);
                    listaTextField.add(txtEstadoPago);
                    listaTextField.add(txtNumeroFac);

                    if (validar(listaTextField)) {

                        btnNuevo.setDisable(false);
                        agregarCuentasPorPagar();
                        limpiarCampos();
                        cargarDatos();
                        limpiarCampos();
                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Nuevo.png"));
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Eliminar.png"));
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
                imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                btnReportar.setText("Reportar");
                imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Reportar.png"));
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
    void mostrarVistaProveedores(ActionEvent event) {
        escenarioPrincipal.mostrarProveedores();
    }

}
