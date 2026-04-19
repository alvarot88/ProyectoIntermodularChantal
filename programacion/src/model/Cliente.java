package model;

import java.sql.Date;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String numDocumento;
    private String email;
    private String telefono;
    private String genero;
    private Date fechaNacimiento;
    private String paisResidencia;
    private String nacionalidad;

    
    public Cliente(String nombre, String apellidos, String tipoDocumento, String numDocumento,
                   String email, String telefono, String genero, Date fechaNacimiento,
                   String paisResidencia, String nacionalidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.paisResidencia = paisResidencia;
        this.nacionalidad = nacionalidad;
    }

   
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getNumDocumento() { return numDocumento; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getGenero() { return genero; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public String getPaisResidencia() { return paisResidencia; }
    public String getNacionalidad() { return nacionalidad; }

    @Override
    public String toString() { return apellidos + ", " + nombre; }
}