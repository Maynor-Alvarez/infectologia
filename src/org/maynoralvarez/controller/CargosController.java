
package org.maynoralvarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.maynoralvarez.bean.Cargo;
import org.maynoralvarez.db.Conexion;
import org.maynoralvarez.sistema.Principal;


public class CargosController implements Initializable  {
    private enum operaciones{AGREGAR,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
            private Principal EscenarioPrincipal; 
            private operaciones tipoDeOperacion = operaciones.NINGUNO; 
            private ObservableList <Cargo> listaCargo; 
  
            
    @FXML private TextField txtNombreCargo;
    @FXML private TableView tblCargo;
    @FXML private TableColumn colNombreCargo;
    @FXML private TableColumn colCodigoCargo; 
    @FXML private ComboBox cmbCodigoCargo; 
    @FXML private Button btnReporte; 
    @FXML private Button btnEliminar; 
    @FXML private Button btnEditar; 
    @FXML private Button btnNuevo;
    
            
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCargo.setItems(getCargo());
    }
    
    public ObservableList <Cargo> getCargo(){
        ArrayList <Cargo> lista = new ArrayList <Cargo> ();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarCargos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargo(resultado.getInt("codigoCargo"),
                                                resultado.getString("nombreCargo")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaCargo = FXCollections.observableList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardad");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        Cargo registro = new Cargo(); 
        registro.setNombreCargo(txtNombreCargo.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarCargos(?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.execute();
            listaCargo.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void desactivarControles(){
        txtNombreCargo.setEditable(false);
    }
    public void activarControles(){
        txtNombreCargo.setEditable(true);
    }
    public void limpiarControles(){
        txtNombreCargo.setText("");
    }

    public Principal getEscenarioPrincipal() {
        return EscenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal EscenarioPrincipal) {
        this.EscenarioPrincipal = EscenarioPrincipal;
    }
    public void menuPrincipal(){
        this.EscenarioPrincipal.menuPrincipal();
    }
}
