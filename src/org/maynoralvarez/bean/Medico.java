
package org.maynoralvarez.bean;


public class Medico {
    private int codigoMedico; 
    private int licenciaMedica; 
    private String nombres; 
    private String Apellidos; 
    private String horaEntrada; 
    private String HoraSalida; 
    private int turnoMaximo; 
    private String sexo; 

    public Medico() {
    }

    public Medico(int codigoMedico, int licenciaMedica, String nombres, String Apellidos, String horaEntrada, String HoraSalida, int turnoMaximo, String sexo) {
        this.codigoMedico = codigoMedico;
        this.licenciaMedica = licenciaMedica;
        this.nombres = nombres;
        this.Apellidos = Apellidos;
        this.horaEntrada = horaEntrada;
        this.HoraSalida = HoraSalida;
        this.turnoMaximo = turnoMaximo;
        this.sexo = sexo;
    }

    public int getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(int codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public int getLicenciaMedica() {
        return licenciaMedica;
    }

    public void setLicenciaMedica(int licenciaMedica) {
        this.licenciaMedica = licenciaMedica;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return HoraSalida;
    }

    public void setHoraSalida(String HoraSalida) {
        this.HoraSalida = HoraSalida;
    }

    public int getTurnoMaximo() {
        return turnoMaximo;
    }

    public void setTurnoMaximo(int turnoMaximo) {
        this.turnoMaximo = turnoMaximo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    
}
