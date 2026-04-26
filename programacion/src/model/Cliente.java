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

    // CONSTRUCTOR COMPLETO
    public Cliente(int idCliente, String nombre, String apellidos, String tipoDocumento, String numDocumento,
                   String email, String telefono, String genero, Date fechaNacimiento,
                   String paisResidencia, String nacionalidad) {

        this.idCliente = idCliente;
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

    // CONSTRUCTOR SIN ID (ALTAS)
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

    // GETTERS
    public int getIdCliente() { return idCliente; }
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

    // SETTERS (NECESARIOS PARA EDICIÓN FUTURA)
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setPaisResidencia(String paisResidencia) { this.paisResidencia = paisResidencia; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    // TO STRING
    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }
}