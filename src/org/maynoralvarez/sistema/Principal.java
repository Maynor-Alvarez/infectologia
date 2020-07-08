
package org.maynoralvarez.sistema;

import org.maynoralvarez.db.Conexion;



import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.maynoralvarez.controller.AreasController;
import org.maynoralvarez.controller.CargosController;
import org.maynoralvarez.controller.ContactoUrgenciaController;
import org.maynoralvarez.controller.MedicosController;
import org.maynoralvarez.controller.PacientesController;
import org.maynoralvarez.controller.MenuPrincipalController;
import org.maynoralvarez.controller.TurnoController;
import org.maynoralvarez.db.Conexion;
/**
 *
 * @author programacion
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/maynoralvarez/view/"; 
    private Stage escenarioPrincipal; 
    private Scene escena; 
    @Override
    public void start(Stage escenarioPrincipal) throws SQLException {
       
        this.escenarioPrincipal = escenarioPrincipal; 
        escenarioPrincipal.setTitle("Hospital de Infectologia");
        menuPrincipal(); 
        escenarioPrincipal.show();
        
       
    }
    
    public void menuPrincipal(){
        try {
           MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",600,400);
           menuPrincipal.setEscenarioPrincipal(this); 
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
        public void VentanaMedicos (){
        try{
            MedicosController medicosController = (MedicosController)cambiarEscena("MedicosView.fxml",770,570);
            medicosController.setEscenarioPrincipal(this); 
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        public void VentanaPacientes (){ 
        try{ 
            PacientesController pacientesController = (PacientesController)cambiarEscena("PacientesView.fxml",850,570);
            pacientesController.setEscenarioPrincipal(this);
        }catch (Exception e){ 
            e.printStackTrace();
            }
        }
        
        public void VentanaTurno(){
            try{ 
                TurnoController turnoController = (TurnoController)cambiarEscena("TurnoView.fxml",770,570);
                turnoController.setEscenarioPrincipal(this);
            }catch(Exception e){
               e.printStackTrace();
            }
        }
        
        public void VentanaContactoUrgencia (){
            try {
                ContactoUrgenciaController contactoUrgenciaController = (ContactoUrgenciaController) cambiarEscena("ContactoUrgenciaView.fxml",650,450);
                contactoUrgenciaController.setEscenarioPrincipal(this);
            }catch (Exception e){
                e.printStackTrace(); 
            }
        }
        public void VentanaAreas(){
            try{
                AreasController areasController = (AreasController) cambiarEscena("AreasView.fxml",290,370);
                areasController.setEscenarioPrincipal(this);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public void VentanaCargos (){ 
            try{ 
                CargosController cargosController = (CargosController) cambiarEscena("CargosView.fxml",290,370);
                cargosController.setEscenarioPrincipal(this);
            }catch(Exception e){ 
               e.printStackTrace();
            }
        }
    
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
            Initializable resultado = null; 
    FXMLLoader cargadorFXML = new FXMLLoader(); 
    InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
    cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
    cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
     escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
     escenarioPrincipal.setScene(escena); 
     escenarioPrincipal.sizeToScene(); 
     resultado =(Initializable)cargadorFXML.getController();
    
            return resultado;
   
    
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
