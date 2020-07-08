package org.maynoralvarez.db;
import java.sql.DriverManager; 
import java.sql.ResultSet; // respuestas
import java.sql.Connection; 
import java.sql.SQLException; //detecta error
import java.sql.Statement;



public class Conexion {

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private Connection conexion;  
      private static Conexion instancia;
     
      private String driver;
      private String url;
      private String usuario;
      private String password;
      private String dbname;
 
      public Conexion() { 
           String dbname = "HospitalInfectologia2018329";
 String url = "jdbc:mysql://localhost:3306/DBHospitalInfectologia2018329?useSSL=false&zeroDateTimeBehavior=convertToNull";
 String driver = "com.mysql.jdbc.Driver";
 String usuario = "root";
 String password = "admin";
 
    try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();  
        
        
        conexion=DriverManager.getConnection(url, usuario, password);        
       
    }catch(ClassNotFoundException e){ 
            e.printStackTrace();
           e.getMessage();
    }catch(InstantiationException e){ 
            e.printStackTrace();
            e.getMessage();
            
    }catch(IllegalAccessException e){ 
            e.printStackTrace();
            e.getMessage();
    }catch(SQLException e){ 
            e.printStackTrace();
            e.getMessage();
            } 
    }  
    
    public static Conexion getInstancia(){ 
        if(instancia == null){ 
            instancia = new Conexion();
        } 
        return instancia;
    } 


    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}