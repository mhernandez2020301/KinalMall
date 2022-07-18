/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.marlonhernandez.system;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.marlonhernandez.controller.*;
import static javafx.application.Application.launch;
import org.marlonhernandez.bean.TipoCliente;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Marlon PC
 * @date 5/05/2021
 * @time 17:27:56
 * Código Técnico: IN5BV
 *
 *
 */

public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        this.escenarioPrincipal = stage;
        escenarioPrincipal.getIcons().add(new Image("/org/marlonhernandez/resource/images/Logo.png"));
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1024, 576);
            menuController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un erroren la vista Menu Principal");
            e.printStackTrace();
        }
    }

    public void mostrarAutor() {
        try {
            AutorController AutorController = (AutorController) cambiarEscena("AutorView.fxml", 395, 216);
            AutorController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Autor");
            e.printStackTrace();
        }
    }
    
        public void mostrarClientes() {
        try {
            ClientesController clientesController = (ClientesController) cambiarEscena("ClientesView.fxml", 1024, 650);
            clientesController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Cliente");
            e.printStackTrace();
        }
    }
    
        public void mostrarDepartamentos() {
        try {
            DepartamentoController depaController = (DepartamentoController) cambiarEscena("DepartamentoView.fxml", 890, 500);
            depaController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println("Se Produjo un error en la vista Locales");
            e.printStackTrace();
        }
    }
    
    public void mostrarLocales() {
        try {
            LocalesController localesController = (LocalesController) cambiarEscena("LocalesView.fxml", 1024, 650);
            localesController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println("Se Produjo un error en la vista Locales");
            e.printStackTrace();
        }
    }           
                
    public void mostrarTipoCLientes() {
        try {
            TipoClientesController tpClientesController = (TipoClientesController) cambiarEscena("TipoClientesView.fxml", 890, 500);
            tpClientesController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista TipoClientes");
            e.printStackTrace();
        }
    }
                
    public void mostrarAdministracion(){
        try {
            AdministracionController AdministracionController = (AdministracionController) cambiarEscena ("AdministracionView.fxml", 890, 500);
            AdministracionController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Error vista Administracion");
            e.printStackTrace();
       
        }
    }
    public void mostrarProveedores() {

        try {
            ProveedoresController proveController = (ProveedoresController) cambiarEscena("ProveedoresView.fxml", 1024, 650);
            proveController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Proveedores");
            e.printStackTrace();
        }
    }

    public void mostrarCuentasporCobrar() {

        try {
            CuentasPorCobrarController cuentaspcobrarController = (CuentasPorCobrarController) cambiarEscena("CuentasporCobrarView.fxml", 1024, 650);
            cuentaspcobrarController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Cuentas por Cobrar");
            e.printStackTrace();
        }
    }

    public void mostrarCuentasporPagar() {

        try {
            CuentasporPagarController cuentasporpagarController = (CuentasporPagarController) cambiarEscena("CuentasporPagarView.fxml", 1024, 650);
            cuentasporpagarController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Cuentas por Pagar");
            e.printStackTrace();
        }
    }
    
        public void mostrarHorarios() {
        try {
            HorariosController horariosController = (HorariosController) cambiarEscena("HorariosView.fxml", 1024, 650);
            horariosController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println("Se produjo un error en la vista Horarios");
            e.printStackTrace();
        }
    }
    
    public void mostrarCargos() {
        try {
            CargosController cargosController = (CargosController) cambiarEscena("CargosView.fxml", 1024, 650);
            cargosController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println("Se produjo un error en la vista Cargos");
            e.printStackTrace();
        }
    }
    
    public void mostrarEmpleados() {
        try {
            EmpleadosController empleadosController = (EmpleadosController) cambiarEscena("EmpleadosView.fxml", 1024, 650);
            empleadosController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println("Se produjo un error en la vista Empleados");
            e.printStackTrace();
        }
    }
    
    
    public Initializable cambiarEscena(String viewFxml, int ancho, int alto) throws IOException {
        Initializable resultado = null;
        FXMLLoader cargarFXML = new FXMLLoader();
        cargarFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargarFXML.setLocation(Principal.class.getResource("/org/marlonhernandez/view/" + viewFxml));
        InputStream archivo = Principal.class.getResourceAsStream("/org/marlonhernandez/view/" + viewFxml);
        escena = new Scene((AnchorPane) cargarFXML.load(archivo), ancho, alto);
        this.escenarioPrincipal.setScene(escena);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();

        resultado = (Initializable) cargarFXML.getController();

        return resultado;
    }
       
}