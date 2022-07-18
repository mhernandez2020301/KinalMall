/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.marlonhernandez.db.*;
import org.marlonhernandez.bean.*;
import org.marlonhernandez.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.marlonhernandez.controller.*;


/**
 *
 * @author Marlon PC
 * @date 9/07/2021
 * @time 22:14:44
 * Código Técnico: IN5BV
 *
 *
 */
public class CuentasPorCobrarController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        valueFactoryAnio = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2021);
        spnAnio.setValueFactory(valueFactoryAnio);

        valueFactoryMes = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 6);
        spnMes.setValueFactory(valueFactoryMes);

        cargarDatos();
    }

    private enum Operaciones {
        NUEVO, GUARDAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<CuentasporCobrar> listaCuetasPorCobrar;

    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Locales> listaLocales;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

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
    private Button btnRegresarClientes;

    @FXML
    private TableView tblCuentasporCobrar;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNumeroFac;

    @FXML
    private TableColumn colEstadoPago;

    @FXML
    private TableColumn colValorNeto;

    @FXML
    private TableColumn colAnio;

    @FXML
    private TableColumn colMes;

    @FXML
    private TableColumn colAdmini;

    @FXML
    private TableColumn colLocal;

    @FXML
    private TableColumn colCliente;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNumeroFac;

    @FXML
    private ComboBox cmbAdmi;

    @FXML
    private ComboBox cmbLocal;

    @FXML
    private ComboBox cmbCliente;

    @FXML
    private TextField txtEstadodePago;

    @FXML
    private TextField txtValrNeto;

    @FXML
    private Spinner<Integer> spnAnio;
    private SpinnerValueFactory<Integer> valueFactoryAnio;

    @FXML
    private Spinner<Integer> spnMes;
    private SpinnerValueFactory<Integer> valueFactoryMes;

    @FXML
    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    @FXML
    void seleccionarElemento(MouseEvent event) {
        seleccionarElemento();
    }

    public ObservableList<CuentasporCobrar> getCuentasPorCobrar() {
        ArrayList<CuentasporCobrar> listado = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarCuentasPorCobrar()}");
            System.out.println(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                listado.add(new CuentasporCobrar(
                        rs.getInt("id"),
                        rs.getString("numeroFactura"),
                        rs.getInt("anio"),
                        rs.getInt("mes"),
                        rs.getBigDecimal("valorNetoPago"),
                        rs.getString("estadoPago"),
                        rs.getInt("idAdministracion"),
                        rs.getInt("idCliente"),
                        rs.getInt("idLocal")
                )
                );
            }

            listaCuetasPorCobrar = FXCollections.observableArrayList(listado);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la tabla Cuentas por cobrar de la base de datos.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listaCuetasPorCobrar;
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

    public ObservableList<Locales> getLocales() {
        ArrayList<Locales> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarLocales()}");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Locales(
                        rs.getInt("id"),
                        rs.getBigDecimal("saldoFavor"),
                        rs.getBigDecimal("saldoContra"),
                        rs.getInt("mesesPendientes"),
                        rs.getBoolean("disponibilidad"),
                        rs.getBigDecimal("valorLocal"),
                        rs.getBigDecimal("valorAdministracion"))
                );
            }
            listaLocales = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la tabla Locales en la base de datos.");
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

        return listaLocales;
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarClientes()}");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Clientes(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getInt("idTipoCliente")
                )
                );
            }
            listaClientes = FXCollections.observableArrayList(lista);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la tabla Clientes en la base de datos.");
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

        return listaClientes;
    }

    public Clientes buscarCliente(int id) {
        Clientes cliente = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarClientes(?)}");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cliente = new Clientes(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getInt("idTipoCliente")
                );
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la tabla Clientes del registro con el ID: " + id);
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
        return cliente;
    }

    public Locales buscarLocales(int id) {
        Locales local = null;

        try {
            PreparedStatement pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarLocales(?)}");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                local = new Locales(
                        rs.getInt("id"),
                        rs.getBigDecimal("saldoFavor"),
                        rs.getBigDecimal("saldoContra"),
                        rs.getInt("mesesPendientes"),
                        rs.getBoolean("disponibilidad"),
                        rs.getBigDecimal("valorLocal"),
                        rs.getBigDecimal("valorAdministracion")
                );
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return local;
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

    public void cargarDatos() {
        tblCuentasporCobrar.setItems(getCuentasPorCobrar());
        colId.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, Integer>("id"));
        colNumeroFac.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, String>("numeroFactura"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, String>("estadoPago"));
        colValorNeto.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, BigDecimal>("valorNetoPago"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, Integer>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, Integer>("mes"));
        colAdmini.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, Integer>("idAdministracion"));
        colCliente.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, Integer>("idCliente"));
        colLocal.setCellValueFactory(new PropertyValueFactory<CuentasporCobrar, Integer>("idLocal"));

        cmbAdmi.setItems(getAdministracion());
        cmbCliente.setItems(getClientes());
        cmbLocal.setItems(getLocales());

    }

    public void seleccionarElemento() {
        try {
            txtId.setText(String.valueOf(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getId()));
            txtNumeroFac.setText(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtEstadodePago.setText(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
            txtValrNeto.setText(String.valueOf(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));

            cmbAdmi.getSelectionModel().select(buscarAdministracion(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getIdAdministracion()));
            cmbCliente.getSelectionModel().select(buscarCliente(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getIdCliente()));
            cmbLocal.getSelectionModel().select(buscarLocales(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getIdLocal()));

            spnAnio.getValueFactory().setValue(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getAnio());
            spnMes.getValueFactory().setValue(((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getMes());

        } catch (NullPointerException e) {

        }
    }

    public void habilitarCampos() {
        txtId.setEditable(false);
        txtNumeroFac.setEditable(true);
        spnAnio.setDisable(false);
        spnMes.setDisable(false);
        txtValrNeto.setEditable(true);
        txtEstadodePago.setEditable(true);
        cmbAdmi.setDisable(false);
        cmbCliente.setDisable(false);
        cmbLocal.setDisable(false);

    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNumeroFac.setEditable(false);
        spnAnio.setDisable(true);
        spnMes.setDisable(true);
        txtValrNeto.setEditable(false);
        txtEstadodePago.setEditable(false);
        cmbAdmi.setDisable(true);
        cmbCliente.setDisable(true);
        cmbLocal.setDisable(true);
    }

    public void limpiarCampos() {
        txtId.clear();
        txtNumeroFac.clear();
        txtValrNeto.clear();
        txtEstadodePago.clear();
        cmbAdmi.valueProperty().set(null);
        cmbCliente.valueProperty().set(null);
        cmbLocal.valueProperty().set(null);
        spnAnio.getValueFactory().setValue(2021);
        spnMes.getValueFactory().setValue(1);

    }

    private void agregarCuentasporCobrar() {
        CuentasporCobrar registro = new CuentasporCobrar();
        registro.setNumeroFactura(txtNumeroFac.getText());
        registro.setAnio(spnAnio.getValue());
        registro.setMes(spnMes.getValue());
        registro.setValorNetoPago(new BigDecimal(txtValrNeto.getText()));
        registro.setEstadoPago(txtEstadodePago.getText());
        registro.setIdAdministracion(((Administracion) cmbAdmi.getSelectionModel().getSelectedItem()).getId());
        registro.setIdCliente(((Clientes) cmbCliente.getSelectionModel().getSelectedItem()).getId());
        registro.setIdLocal(((Locales) cmbLocal.getSelectionModel().getSelectedItem()).getId());

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarCuentasPorCobrar( ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, registro.getNumeroFactura());
            stmt.setInt(2, registro.getAnio());
            stmt.setInt(3, registro.getMes());
            stmt.setBigDecimal(4, registro.getValorNetoPago());
            stmt.setString(5, registro.getEstadoPago());
            stmt.setInt(6, registro.getIdAdministracion());
            stmt.setInt(7, registro.getIdCliente());
            stmt.setInt(8, registro.getIdLocal());

            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void EliminarCuentasPorCobrar() {
        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarCuentasPorCobrar(?)}");
            stmt.setInt(1, ((CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem()).getId());
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editarCuentasPorCobrar() {
        CuentasporCobrar registro = (CuentasporCobrar) tblCuentasporCobrar.getSelectionModel().getSelectedItem();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setNumeroFactura(txtNumeroFac.getText());
        registro.setAnio(spnAnio.getValue());
        registro.setMes(spnMes.getValue());
        registro.setValorNetoPago(new BigDecimal(txtValrNeto.getText()));
        registro.setEstadoPago(txtEstadodePago.getText());
        registro.setIdAdministracion(((Administracion) cmbAdmi.getSelectionModel().getSelectedItem()).getId());
        registro.setIdCliente(((Clientes) cmbCliente.getSelectionModel().getSelectedItem()).getId());
        registro.setIdLocal(((Locales) cmbLocal.getSelectionModel().getSelectedItem()).getId());

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EditarCuentasPorCobrar( ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            stmt.setInt(1, registro.getId());
            stmt.setString(2, registro.getNumeroFactura());
            stmt.setInt(3, registro.getAnio());
            stmt.setInt(4, registro.getMes());
            stmt.setBigDecimal(5, registro.getValorNetoPago());
            stmt.setString(6, registro.getEstadoPago());
            stmt.setInt(7, registro.getIdAdministracion());
            stmt.setInt(8, registro.getIdCliente());
            stmt.setInt(9, registro.getIdLocal());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                listaTextField.add(txtValrNeto);
                listaTextField.add(txtEstadodePago);
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
                        EliminarCuentasPorCobrar();
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
                    listaTextField.add(txtValrNeto);
                    listaTextField.add(txtEstadodePago);
                    listaTextField.add(txtValrNeto);

                    if (validar(listaTextField)) {

                        btnNuevo.setDisable(false);
                        agregarCuentasporCobrar();
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
    void regresarClientes(ActionEvent event) {
        escenarioPrincipal.mostrarClientes();
    }
}
