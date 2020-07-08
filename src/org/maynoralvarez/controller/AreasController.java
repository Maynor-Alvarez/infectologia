
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
import org.maynoralvarez.bean.Area;
import org.maynoralvarez.db.Conexion;
import org.maynoralvarez.sistema.Principal;


public class AreasController implements Initializable {
    private enum operaciones{AGREGAR,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal EscenarioPrincipal; 
    private operaciones tipoDeOperacion = operaciones.NINGUNO;  
    private ObservableList <Area> listaArea; 
    
    
    @FXML private TextField txtNombreArea;
    @FXML private TableView tblAreas;
    @FXML private TableColumn colNombreArea;
    @FXML private TableColumn colCodigoArea;
    @FXML private ComboBox cmbCodigoArea; 
    @FXML private Button btnReporte; 
    @FXML private Button btnNuevo; 
    @FXML private Button btnEliminar; 
    @FXML private Button btnEditar;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblAreas.setItems(getAreas());
    }
    
    public ObservableList <Area> getAreas(){
        ArrayList <Area> lista = new ArrayList <Area>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarAreas}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Area(resultado.getInt("codigoArea"),
                                                resultado.getString("nombreArea")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaArea = FXCollections.observableList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                 activarControles();
                 btnNuevo.setText("Guardar");
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
        Area registro = new Area();
        registro.setNombreArea(txtNombreArea.getText());
        try{
                    PreparedStatement procedimiento  = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarAreas(?)}");
                    procedimiento.setString(1, registro.getNombreArea());
                    procedimiento.execute();
                    listaArea.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    public void desactivarControles(){
        txtNombreArea.setEditable(false);
    }
    public void activarControles(){
        txtNombreArea.setEditable(true);
    }
    public void limpiarControles(){
        txtNombreArea.setText("");
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
