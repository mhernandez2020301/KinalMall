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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.marlonhernandez.db.Conexion;
/**
 *
 * @author Marlon PC
 * @date 16/06/2021
 * @time 12:59:26
 * Código Técnico: IN5BV
 *
 *
 */
public class LocalesController implements Initializable{
    
    private Principal escenarioPrincipal;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReportar;
    @FXML
    private TextField txtSaldoLiquido;
    @FXML
    private TextField txtLocalesDisponibles;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    private ObservableList<Locales> listaLocal;

    @FXML
    private void regresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }
    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtSaldoafavor;
    @FXML
    private TextField txtSaldoencontra;
    @FXML
    private TableView tblLocales;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colSaldoafavor;
    @FXML
    private TableColumn colSaldoenContra;
    @FXML
    private TableColumn colMesesPendientes;
    @FXML
    private TableColumn colDisponibilidad;
    @FXML
    private TableColumn colValorlocal;
    @FXML
    private TableColumn colValorAdministracion;
    @FXML
    private TextField txtMesespendientes;
    @FXML
    private TextField txtValorlocal;
    @FXML
    private TextField txtDisponibilidad;
    @FXML
    private TextField txtValoradministración;
    private ImageView ImgNuevo;
    private ImageView ImgEliminar;


    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }
    
    public ObservableList<Locales> getLocal() {
        ArrayList<Locales> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Locales(
                        r.getInt("id"), 
                        r.getBigDecimal("saldoFavor"), 
                        r.getBigDecimal("saldoContra"), 
                        r.getInt("mesesPendientes"), 
                        r.getBoolean("disponibilidad"), 
                        r.getBigDecimal("valorLocal"), 
                        r.getBigDecimal("valorAdministracion")
                )
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaLocal = FXCollections.observableArrayList(lista);
        return listaLocal;
    }
    
    public void cargarDatos() {
        tblLocales.setItems(getLocal());
        colId.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("id"));
        colSaldoafavor.setCellValueFactory(new PropertyValueFactory<Locales, BigDecimal>("saldoFavor"));
        colSaldoenContra.setCellValueFactory(new PropertyValueFactory<Locales, BigDecimal>("saldoContra"));
        colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Locales, Boolean>("disponibilidad"));
        colValorlocal.setCellValueFactory(new PropertyValueFactory<Locales, BigDecimal>("valorLocal"));
        colValorAdministracion.setCellValueFactory(new PropertyValueFactory<Locales, BigDecimal>("valorAdministracion"));
    }
    @FXML
    public void selecionarElemento() {
        txtId.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getId()));
        txtSaldoafavor.setText(String.valueOf(  (((Locales) tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor())));
        txtSaldoencontra.setText(String.valueOf(  (((Locales) tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra())));
        txtMesespendientes.setText(String.valueOf(((Locales) tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
        txtDisponibilidad.setText(String.valueOf( ((Locales) tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad()));
        txtValoradministración.setText(String.valueOf(  (((Locales) tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion())));
        txtValorlocal.setText(String.valueOf(  (((Locales) tblLocales.getSelectionModel().getSelectedItem()).getValorLocal())));
        txtSaldoLiquido.setText(String.valueOf(calcularSaldoLiquido()));
    }
    public void agregarLocales() {
        
        Locales registro = new Locales();
        registro.setSaldoFavor(BigDecimal.ZERO);
        registro.setSaldoContra(BigDecimal.ONE);
        registro.setMesesPendientes(0);
        registro.setDisponibilidad(true);
        registro.setValorLocal(BigDecimal.ZERO);
        registro.setValorAdministracion(BigDecimal.ZERO);

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarLocales(?,?,?,?,?,?)}");
            pstmt.setBigDecimal(1, registro.getSaldoFavor());
            pstmt.setBigDecimal(2, registro.getSaldoContra());
            pstmt.setInt(3, registro.getMesesPendientes());
            pstmt.setBoolean(4, registro.isDisponibilidad());
            pstmt.setBigDecimal(5, registro.getValorAdministracion());
            pstmt.setBigDecimal(6, registro.getValorLocal());
                    
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }
        
    }
    
    public void eliminarLocales() {
        try {
           PreparedStatement pstmt;
           pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarLocales(?)}");
           pstmt.setInt(1, Integer.parseInt(txtId.getText()));
           pstmt.execute();
           System.out.println(pstmt.toString());
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void EditarLocales() {
        Locales registro = new Locales();
        registro.setId(Integer.getInteger(txtId.getId()));
        registro.setSaldoFavor(BigDecimal.ZERO);
        registro.setSaldoContra(BigDecimal.ONE);
        registro.setMesesPendientes(Integer.getInteger(txtMesespendientes.getText()));
        registro.setDisponibilidad(true);
        registro.setValorLocal(BigDecimal.ZERO);
        registro.setValorAdministracion(BigDecimal.ZERO);

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarLocales(?,?,?,?,?,?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setBigDecimal(2, registro.getSaldoFavor());
            pstmt.setBigDecimal(3, registro.getSaldoContra());
            pstmt.setInt(4, registro.getMesesPendientes());
            pstmt.setBoolean(5, registro.isDisponibilidad());
            pstmt.setBigDecimal(6, registro.getValorAdministracion());
            pstmt.setBigDecimal(7, registro.getValorLocal());
                    
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    public void limpiarCampos() {
        txtId.clear();
        txtSaldoafavor.clear();
        txtSaldoencontra.clear();
        txtMesespendientes.clear();
        txtDisponibilidad.clear();
        txtValorlocal.clear();
        txtValoradministración.clear();
    }
    
    public void habilitarCampos() {
        txtId.setEditable(true);
        txtSaldoafavor.setEditable(true);
        txtSaldoencontra.setEditable(true);
        txtMesespendientes.setEditable(true);
        txtDisponibilidad.setEditable(true);
        txtValorlocal.setEditable(true);
        txtValoradministración.setEditable(true);
        
    }

    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtSaldoafavor.setEditable(false);
        txtSaldoencontra.setEditable(false);
        txtMesespendientes.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtValorlocal.setEditable(false);
        txtValoradministración.setEditable(false);
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
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                operacion = Operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un elemento para Editarlo");
                } else {
                EditarLocales();
                limpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReportar.setText("Reporte");
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
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
                limpiarCampos();
                deshabilitarCampos();
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Eliminar
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se pude realizar esta accion");
                } else {

                    int a = JOptionPane.showConfirmDialog(null, "Esta borrando un Registro, desea continuar: ");

                    if (JOptionPane.OK_OPTION == a) {
                        eliminarLocales();
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
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                break;
            case GUARDAR:
                if (txtSaldoafavor.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes Ingresar Datos");
                } else {
                    btnNuevo.setDisable(false);
                    agregarLocales();
                    cargarDatos();
                    limpiarCampos();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReportar.setDisable(false);
                    ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                    ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                    operacion = Operaciones.NINGUNO;
                }

                break;
        }
        System.out.println("Operación: " + operacion);
    }


    void reporte(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReportar.setText("Reprotar");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                deshabilitarCampos();
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                btnReportar.setText("Reportar");
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                limpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    
    void regresarClientes(ActionEvent event) {
        escenarioPrincipal.mostrarClientes();
    }
    
    public void contarLocalesDisponibles() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_CalcularLocalesDisponibles()}");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                txtLocalesDisponibles.setText(String.valueOf(rs.getInt("N°")));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {

        }
    }    

    public BigDecimal calcularSaldoLiquido() {
        BigDecimal saldoFavor = new BigDecimal(txtSaldoafavor.getText());
        BigDecimal saldoContra = new BigDecimal(txtSaldoencontra.getText());
        BigDecimal retornar = saldoFavor.subtract(saldoContra);
        return retornar;
    }
    

    
}
