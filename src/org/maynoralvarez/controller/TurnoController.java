
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
import org.maynoralvarez.bean.Turno;
import org.maynoralvarez.db.Conexion;
import org.maynoralvarez.sistema.Principal;


public class TurnoController implements Initializable {
    private enum operaciones {AGREGAR,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
        private Principal EscenarioPrincipal; 
        private operaciones tipoDeOperacion = operaciones.NINGUNO;
        private ObservableList <Turno> listaTurno; 
        
    @FXML private TextField txtValorCita; 
    @FXML private TextField txtFechaTurno;
    @FXML private TextField txtFechaCita;
    @FXML private TableView tblTurno; 
    @FXML private TableColumn colValorCita; 
    @FXML private TableColumn colFechaTurno; 
    @FXML private TableColumn colCodigoTurnoResp; 
    @FXML private TableColumn colCodigoTurno; 
    @FXML private TableColumn colCodigoPaciente; 
    @FXML private TableColumn colCodigoMedicoEsp; 
    @FXML private ComboBox cmbCodigoTurnoResponsable; 
    @FXML private ComboBox cmbCodigoTurno; 
    @FXML private ComboBox cmbCodigoPaciente; 
    @FXML private ComboBox cmbCodigoMedicoEspecialidad; 
    @FXML private Button btnReporte; 
    @FXML private Button btnNuevo; 
    @FXML private Button btnEliminar; 
    @FXML private Button btnEditar; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
    tblTurno.setItems(getTurnos());
    }
    
    public ObservableList <Turno> getTurnos(){
        ArrayList <Turno> lista = new ArrayList <Turno>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTurnos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Turno(resultado.getInt("codigoTurno"),
                                                resultado.getString("fechaTurno"),
                                                resultado.getString("fechaCita"),
                                                resultado.getString("valorCita"),
                                                resultado.getInt("codigoMedicoEspecialidad"),
                                                resultado.getInt("codigoResponsableTurno"),
                                                resultado.getInt("codigoPaciente")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaTurno = FXCollections.observableList(lista);
    }
    
    
    
    public void nuevo (){
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
        Turno registro = new Turno ();
        registro.setValorCita((txtFechaTurno.getText()));
        registro.setFechaCita(txtFechaCita.getText());
        registro.setFechaTurno(txtValorCita.getText());
        try{
                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarTurno(?,?,?)}");
                procedimiento.setString(1, registro.getFechaTurno());
                procedimiento.setString(2, registro.getFechaCita());
                procedimiento.setString(3, registro.getValorCita());
                procedimiento.execute();
                listaTurno.add(registro);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void desactivarControles(){
        txtFechaTurno.setEditable(false);
        txtFechaCita.setEditable(false);
        txtValorCita.setEditable(false);
    }
    
    public void activarControles(){
        txtFechaTurno.setEditable(true);
        txtFechaCita.setEditable(true);
        txtValorCita.setEditable(true);
    }
    
    public void limpiarControles(){
        txtFechaTurno.setText("");
        txtFechaCita.setText("");
        txtValorCita.setText("");
    }

    public Principal getEscenarioPrincipal() {
        return EscenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal EscenarioPrincipal) {
        this.EscenarioPrincipal = EscenarioPrincipal;
    }
    public void menuPrincipal(){
        EscenarioPrincipal.menuPrincipal();
    }
}
