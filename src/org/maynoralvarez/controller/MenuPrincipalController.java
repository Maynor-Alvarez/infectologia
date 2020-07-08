package org.maynoralvarez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.maynoralvarez.sistema.Principal;


public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void VentanaMedico (){
        escenarioPrincipal.VentanaMedicos();
    }
    public void VentanaPacientes (){
        escenarioPrincipal.VentanaPacientes();
    }
    public void VentanaTurno (){
        escenarioPrincipal.VentanaTurno();
    }
    public void VentanaContactoUrgencia(){
        escenarioPrincipal.VentanaContactoUrgencia();
    }
    public void VentanaAreas(){ 
        escenarioPrincipal.VentanaAreas();
    }
    public void VentanaCargos(){
        escenarioPrincipal.VentanaCargos();
    }
}
