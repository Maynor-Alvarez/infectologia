
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.maynoralvarez.bean.Paciente;
import org.maynoralvarez.db.Conexion;
import org.maynoralvarez.sistema.Principal;


public class PacientesController implements Initializable{
    private enum operaciones{AGREGAR,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal   EscenarioPrincipal; 
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Paciente> listaPaciente;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte; 
    @FXML private TextField txtApellidos; 
    @FXML private TextField txtFechaNacimiento; 
    @FXML private TextField txtDireccion; 
    @FXML private TextField txtDPI; 
    @FXML private TextField txtEdad; 
    @FXML private TextField txtNombres; 
    @FXML private TextField txtOcupacion;
    @FXML private TextField txtSexo;
    @FXML private TableColumn colCodigoPaciente; 
    @FXML private TableColumn colDPI; 
    @FXML private TableColumn colDireccion; 
    @FXML private TableColumn colEdad; 
    @FXML private TableColumn colNacimiento; 
    @FXML private TableColumn colNombres;
    @FXML private TableColumn colOcupacion; 
    @FXML private TableColumn colSexo; 
    @FXML private TableColumn colApellidos; 
    @FXML private TableView tblPacientes;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblPacientes.setItems(getPacientes());
    }
    
    public ObservableList <Paciente> getPacientes(){
        ArrayList <Paciente> lista = new ArrayList <Paciente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{ call sp_ListarPacientes}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Paciente(resultado.getInt("codigoPaciente"),
                                                    resultado.getInt("DPI"),
                                                    resultado.getString("nombres"),
                                                    resultado.getString("apellidos"),
                                                    resultado.getString("nacimiento"),
                                                    resultado.getInt("edad"),
                                                    resultado.getString("direccion"),
                                                    resultado.getString("ocupacion"),
                                                    resultado.getString("sexo")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaPaciente = FXCollections.observableList(lista);
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
        Paciente registro = new Paciente(); 
        registro.setDPI(Integer.parseInt(txtDPI.getText()));
        registro.setApellidos(txtApellidos.getText());
        registro.setNombres(txtNombres.getText());
         registro.setFechaNacimiento(txtFechaNacimiento.getText());
        registro.setEdad(Integer.parseInt(txtEdad.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setOcupacion(txtOcupacion.getText());
        registro.setSexo(txtSexo.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarPaciente(?,?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getDPI());
            procedimiento.setString(2, registro.getApellidos());
            procedimiento.setString(3, registro.getNombres());
            procedimiento.setString(4, registro.getFechaNacimiento());
            procedimiento.setInt(5, registro.getEdad());
            procedimiento.setString(6, registro.getDireccion());
            procedimiento.setString(7, registro.getOcupacion());
            procedimiento.setString(8, registro.getSexo());
            procedimiento.execute();
            listaPaciente.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtSexo.setEditable(false);
        txtOcupacion.setEditable(false);
        txtNombres.setEditable(false);
        txtEdad.setEditable(false);
        txtDireccion.setEditable(false);
        txtDPI.setEditable(false);
        txtApellidos.setEditable(false);
       txtFechaNacimiento.setEditable(false);
    }
    
    public void activarControles(){
        txtSexo.setEditable(true);
        txtOcupacion.setEditable(true);
        txtNombres.setEditable(true);
        txtEdad.setEditable(true);
        txtDireccion.setEditable(true);
        txtDPI.setEditable(true);
        txtApellidos.setEditable(true);
        txtFechaNacimiento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtSexo.setText("");
        txtOcupacion.setText("");
        txtNombres.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtDPI.setText("");
        txtApellidos.setText(""); 
        txtFechaNacimiento.setText("");
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
