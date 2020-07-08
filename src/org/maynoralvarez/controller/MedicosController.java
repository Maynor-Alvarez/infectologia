
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.maynoralvarez.bean.Medico;
import org.maynoralvarez.db.Conexion;
import org.maynoralvarez.sistema.Principal;

public class MedicosController implements Initializable  {
    private enum operaciones{AGREGAR,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private Principal EscenarioPrincipal; 
    private operaciones tipoDeOperacion = operaciones.NINGUNO; 
    private ObservableList <Medico> listaMedico; 
    
    @FXML private ComboBox cmbCodigoMedico;
    @FXML private TextField txtHoraEntrada; 
    @FXML private TextField txtHoraSalida;
    @FXML private TextField txtApellidos; 
    @FXML private TextField txtNombres; 
    @FXML private TextField txtSexo; 
    @FXML private TextField txtLicenciaMedica; 
    @FXML private Button btnNuevo; 
    @FXML private Button btnEditar; 
    @FXML private Button btnEliminar; 
    @FXML private Button btnReporte; 
    @FXML private TableColumn colCodigoMedico; 
    @FXML private TableColumn colLicenciaMedica; 
    @FXML private TableColumn colNombres;
    @FXML private TableColumn colApellidos;
    @FXML private TableColumn colHoraEntrada;
    @FXML private TableColumn colHoraSalida;
    @FXML private TableColumn colSexo;
    @FXML private TableView  tblMedicos; 
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        
    }
    
    public void cargarDatos(){
        tblMedicos.setItems(getMedicos());
        colCodigoMedico.setCellValueFactory(new PropertyValueFactory<Medico, Integer>("codigoMedico"));
        colLicenciaMedica.setCellValueFactory(new PropertyValueFactory<Medico, Integer>("licenciaMedica"));
    }
    
    public ObservableList <Medico> getMedicos(){
         ArrayList <Medico> lista = new ArrayList <Medico>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{ call sp_listarMedicos }");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Medico(resultado.getInt("codigoMedico"),
                                        resultado.getInt("licenciMedica"),
                                        resultado.getString("nombres"),
                                        resultado.getString("apellidos"),
                                        resultado.getString("horaEntrada"),
                                        resultado.getString("horaSalida"),
                                        resultado.getInt("turnoMaximo"),
                                        resultado.getString("sexo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaMedico = FXCollections.observableList(lista);
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
                tipoDeOperacion=operaciones.NINGUNO;
            break;
        }
    }
    
     public void guardar (){
        Medico registro = new Medico();
        registro.setLicenciaMedica(Integer.parseInt(txtLicenciaMedica.getText()));
        registro.setNombres(txtNombres.getText());
        registro.setApellidos(txtApellidos.getText());
        registro.setHoraEntrada(txtHoraEntrada.getText());
        registro.setHoraSalida(txtHoraSalida.getText());
        registro.setSexo(txtSexo.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarMedicos(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getLicenciaMedica());
            procedimiento.setString(2, registro.getNombres());
            procedimiento.setString(3, registro.getApellidos());
            procedimiento.setString(4, registro.getHoraEntrada());
            procedimiento.setString(5, registro.getHoraSalida());
            procedimiento.setString(6, registro.getSexo());
            procedimiento.execute();
            listaMedico.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtLicenciaMedica.setEditable(false); 
        txtNombres.setEditable(false); 
        txtApellidos.setEditable(false); 
        txtHoraEntrada.setEditable(false); 
        txtHoraSalida.setEditable(false);
        txtSexo.setEditable(false);
    }
    
    public void activarControles(){
        txtLicenciaMedica.setEditable(true);
        txtNombres.setEditable(true);
        txtApellidos.setEditable(true);
        txtHoraEntrada.setEditable(true);
        txtHoraSalida.setEditable(true);
        txtSexo.setEditable(true);
    }
    
    public void limpiarControles(){
        txtLicenciaMedica.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtHoraEntrada.setText("");
        txtHoraSalida.setText("");
        txtSexo.setText("");
    }

    public Principal getEscenarioPrincipal() {
        return EscenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal EscenarioPrincipal) {
        this.EscenarioPrincipal = EscenarioPrincipal;
    }
    
    public void menuPrincipal (){
        this.EscenarioPrincipal.menuPrincipal();
    }
    
}
