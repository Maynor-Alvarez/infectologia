
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
import org.maynoralvarez.bean.ContactoUrgencia;
import org.maynoralvarez.db.Conexion;
import org.maynoralvarez.sistema.Principal;


public class ContactoUrgenciaController implements Initializable{
    private enum operaciones {AGREGAR,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal EscenarioPrincipal ;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <ContactoUrgencia> listaContactoUrgencia; 
    
    @FXML private TextField txtNumeroContacto;
    @FXML private TextField txtNombres; 
    @FXML private TextField txtApellidos; 
    @FXML private TableView tblContactoUrgencia; 
    @FXML private TableColumn colNumeroContacto;
    @FXML private TableColumn colNombres; 
    @FXML private TableColumn colCodigoPaciente; 
    @FXML private TableColumn colCodigoContacto; 
    @FXML private TableColumn colApellidos; 
    @FXML private ComboBox cmbCodigoPaciente; 
    @FXML private ComboBox cmbCodigoContactoUrgencia; 
    @FXML private Button btnReporte; 
    @FXML private Button btnNuevo; 
    @FXML private Button btnEditar; 
    @FXML private Button btnEliminar; 
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
    tblContactoUrgencia.setItems(getContactoUrgencia());
    }
    
    public ObservableList <ContactoUrgencia> getContactoUrgencia(){
        ArrayList <ContactoUrgencia> lista = new ArrayList <ContactoUrgencia>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarContactoUrgencia}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new ContactoUrgencia(resultado.getInt("codigoContactoUrgencia"),
                                                                    resultado.getString("nombres"),
                                                                    resultado.getString("apellidos"),
                                                                    resultado.getString("numeroContacto"),
                                                                    resultado.getInt("codigoPaciente")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaContactoUrgencia = FXCollections.observableList(lista);
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
    
    public void guardar (){
        ContactoUrgencia registro =new ContactoUrgencia();
        registro.setNumeroContacto(txtNumeroContacto.getText());
        registro.setNombres(txtNombres.getText());
        registro.setApellidos(txtApellidos.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarContactoUrgencia(?,?,?)}");
            procedimiento.setString(1, registro.getNumeroContacto());
            procedimiento.setString(2, registro.getNombres());
            procedimiento.setString(3, registro.getApellidos());
            procedimiento.execute();
            listaContactoUrgencia.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void desactivarControles(){
        txtNombres.setEditable(false);
        txtNumeroContacto.setEditable(false);
        txtApellidos.setEditable(false);
    }
    public void activarControles(){
        txtNombres.setEditable(true);
        txtNumeroContacto.setEditable(true);
        txtApellidos.setEditable(true);
    }
    public void limpiarControles(){
        txtNombres.setText("");
        txtNumeroContacto.setText("");
        txtApellidos.setText("");
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
