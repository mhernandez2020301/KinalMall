/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marlonhernandez.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.marlonhernandez.system.Principal;

 
/**
 * FXML Controller class
 *
 * @author Marlon PC 
 */
public class AutorController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //To change body of generated methods, choose Tools | Templates.
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }    

    @FXML
    private void regresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }
}