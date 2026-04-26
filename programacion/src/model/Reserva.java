package model;

import java.sql.Date;

public class Reserva {

    private int idReserva;
    private int idCliente;
    private Date fechaReserva;
    private String estadoReserva;
    private String estadoPago;

    // CONSTRUCTOR VACÍO
    public Reserva() {}

    // CONSTRUCTOR COMPLETO
    public Reserva(int idReserva, int idCliente, Date fechaReserva, String estadoReserva, String estadoPago) {

        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.fechaReserva = fechaReserva;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
    }

    // CONSTRUCTOR PARA CREAR
    public Reserva(int idCliente, Date fechaReserva, String estadoReserva, String estadoPago) {

        this.idCliente = idCliente;
        this.fechaReserva = fechaReserva;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
    }

    // GETTERS
    public int getIdReserva() { return idReserva; }
    public int getIdCliente() { return idCliente; }
    public Date getFechaReserva() { return fechaReserva; }
    public String getEstadoReserva() { return estadoReserva; }
    public String getEstadoPago() { return estadoPago; }

    // SETTERS
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public void setFechaReserva(Date fechaReserva) { this.fechaReserva = fechaReserva; }
    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
}