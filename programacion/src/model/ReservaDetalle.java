package model;

import java.sql.Date;

public class ReservaDetalle {

    private int idReserva;
    private String nombreCliente;
    private String nombreHotel;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double precioTotal;
    private String estadoReserva;

    // CONSTRUCTOR
    public ReservaDetalle(int idReserva, String nombreCliente, String nombreHotel,
                          Date fechaEntrada, Date fechaSalida,
                          double precioTotal, String estadoReserva) {

        this.idReserva = idReserva;
        this.nombreCliente = nombreCliente;
        this.nombreHotel = nombreHotel;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precioTotal = precioTotal;
        this.estadoReserva = estadoReserva;
    }

    // GETTERS
    public int getIdReserva() { return idReserva; }
    public String getNombreCliente() { return nombreCliente; }
    public String getNombreHotel() { return nombreHotel; }
    public Date getFechaEntrada() { return fechaEntrada; }
    public Date getFechaSalida() { return fechaSalida; }
    public double getPrecioTotal() { return precioTotal; }
    public String getEstadoReserva() { return estadoReserva; }

    // SETTERS
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public void setNombreHotel(String nombreHotel) { this.nombreHotel = nombreHotel; }
    public void setFechaEntrada(Date fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }

    // TO STRING
    @Override
    public String toString() {
        return "Reserva #" + idReserva +
                " | Cliente: " + nombreCliente +
                " | Hotel: " + nombreHotel +
                " | " + fechaEntrada + " -> " + fechaSalida +
                " | " + precioTotal + "€";
    }
}