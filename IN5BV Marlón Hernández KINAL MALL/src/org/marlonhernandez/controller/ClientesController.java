/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.controller;

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
 * @date 9/06/2021
 * @time 14:52:57
 * Código Técnico: IN5BV
 *
 *
 */
public class ClientesController implements Initializable {

    private Principal escenarioPrincipal;
    @FXML
    private TableView<?> tblEmpleados;
    @FXML
    private TableColumn<?, ?> colDepartamento;
    @FXML
    private TableColumn<?, ?> colCargo;
    @FXML
    private TableColumn<?, ?> colHorario;
    @FXML
    private TableColumn<?, ?> colAdministracion;
    @FXML
    private TableColumn<?, ?> colSueldo;
    @FXML
    private TextField txtSueldo;
    @FXML
    private ComboBox<?> cmbDepartamento;
    @FXML
    private ComboBox<?> cmbCargo;
    @FXML
    private ComboBox<?> cmbHorario;


    @FXML
    private void regresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }
    


    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Clientes> listaClientes;
    private ObservableList<TipoCliente> listatipoCliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    @FXML
    private TextField txtTelefono;
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    private ComboBox cmbTipoCliente;
    @FXML
    private ComboBox cmbAdministracion;
    private TableColumn colTipoCliente;
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
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    private TableView tblClientes;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNombres;

    @FXML
    private TableColumn colApellidos;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colEmail;

    private TableColumn colDireccion;

    
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

    public ObservableList<TipoCliente> getTipoClientes() {
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

        listatipoCliente = FXCollections.observableArrayList(lista);
        return listatipoCliente;
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Clientes(
                        r.getInt("id"),
                        r.getString("nombres"),
                        r.getString("apellidos"),
                        r.getString("telefono"),
                        r.getString("direccion"),
                        r.getString("email"),
                        r.getInt("idTipoCliente"))
                );
            }

            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaClientes = FXCollections.observableArrayList(lista);
        return listaClientes;
    }
    
    private TipoCliente buscarTipoCliente(int id) {
        TipoCliente registro = null;
        
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoCliente(?)}");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                    registro = new TipoCliente(rs.getInt("id"),rs.getString("descripcion"));
            }
            
            rs.close();
            pstmt.close();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return registro;
    }
    
    public void cargarDatos() {

        tblClientes.setItems(getClientes());
        colId.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Clientes, String>("email"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccion"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("idTipoCliente"));

        cmbTipoCliente.setItems(getTipoClientes());
    }

    public void agregarClientes() {
        Clientes registro = new Clientes();
        registro.setNombres(txtDireccion.getText());
        registro.setApellidos(txtTelefono.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setEmail(txtEmail.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setIdTipoCliente(0);

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?,?,?)}");
            pstmt.setString(1, registro.getNombres());
            pstmt.setString(2, registro.getApellidos());
            pstmt.setString(3, registro.getTelefono());
            pstmt.setString(4, registro.getEmail());
            pstmt.setString(5, registro.getDireccion());
            pstmt.setInt(6, registro.getIdTipoCliente());
            pstmt.setInt(6, 1);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarClientes() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
            //pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getId());
            pstmt.execute();
            System.out.println(pstmt.toString() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarClientes() {
        Clientes registro = new Clientes();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setNombres(txtDireccion.getText());
        registro.setApellidos(txtTelefono.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setEmail(txtEmail.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setIdTipoCliente(0);

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?,?,?,?,?,?,?)}");
            pstmt.setString(1, registro.getNombres());
            pstmt.setString(2, registro.getApellidos());
            pstmt.setString(3, registro.getTelefono());
            pstmt.setString(4, registro.getEmail());
            pstmt.setString(5, registro.getDireccion());
            pstmt.setInt(6, registro.getIdTipoCliente());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
}

public void desactivarControles() {
        txtId.setEditable(false);
        txtApellidos.setEditable(false);
        txtNombres.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        cmbTipoCliente.setDisable(true);
    }

    public void activarControles() {
        txtId.setEditable(false);
        txtApellidos.setEditable(true);
        txtNombres.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        cmbTipoCliente.setDisable(false);
    }

    public void limpiarControles() {
        txtId.clear();
        txtApellidos.clear();
        txtNombres.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        cmbTipoCliente.getSelectionModel().clearSelection();
    }

    @FXML
    public void selecionarElemento() {
        
        try { 
        txtId.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getId()));
        txtNombres.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombres());
        txtApellidos.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidos());
        txtTelefono.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefono());
        txtEmail.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getEmail());
        txtDireccion.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccion());
        cmbTipoCliente.getSelectionModel().select(buscarTipoCliente(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getIdTipoCliente()));
         
        } catch (NullPointerException e) {
            
        }  
    }

    void selecionarElemento(MouseEvent event) {
        selecionarElemento();
    }

    @FXML
    void editar(ActionEvent event) {
                System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                activarControles();
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
                    editarClientes();
                    limpiarControles();
                    desactivarControles();
                    cargarDatos();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    btnReportar.setText("Reportar");
                    operacion = Operaciones.NINGUNO;
                    ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                    ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
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
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                limpiarControles();
                desactivarControles();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Eliminar
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se pude realizar esta accion");
                } else {

                    int a = JOptionPane.showConfirmDialog(null, "Esta borrando un Registro, desea continuar: ", "Confirmacion", JOptionPane.YES_NO_OPTION);

                    if (a == JOptionPane.YES_OPTION) {
                        eliminarClientes();
                        limpiarControles();
                    ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                    ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
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
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Guardar.png"));
                btnEliminar.setText("Cancelar");
                ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Cancelar.png"));
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtNombres.getText().isEmpty() && txtApellidos.getText().isEmpty() && txtTelefono.getText().isEmpty()
                        && txtDireccion.getText().isEmpty() && txtDireccion.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debes Ingresar Datos");
                } else {
                    btnNuevo.setDisable(false);
                    agregarClientes();
                    cargarDatos();
                    limpiarControles();
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
    

    @FXML
    void reportar(ActionEvent event) {         
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                btnReportar.setText("Reprotar");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                    ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                    ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                    limpiarControles();
                desactivarControles();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                btnReportar.setText("Reportar");
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                    ImgNuevo.setImage(new Image ("/org/marlonhernandez/resource/images/Nuevo.png"));
                    ImgEliminar.setImage(new Image("/org/marlonhernandez/resource/images/Eliminar.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }
    
    void mostrarDepartamentos(ActionEvent event) {
    escenarioPrincipal.mostrarDepartamentos();
    }

    void mostrarLocales(ActionEvent event) {
        escenarioPrincipal.mostrarLocales();
    }

    void mostrarVistaTipoClientes(ActionEvent event) {
        escenarioPrincipal.mostrarTipoCLientes();
    }
    
    
}
