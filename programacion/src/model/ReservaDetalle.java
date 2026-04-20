package model;

import java.sql.Date;

public class ReservaDetalle {
    private int idReserva;
    private String nombreCliente;
    private String nombreHotel;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double precioTotal;
    private String estado;

    // CONSTRUCTOR: Para que el DAO pueda crear estos objetos
    public ReservaDetalle(int idReserva, String nombreCliente, String nombreHotel,
                          Date fechaEntrada, Date fechaSalida, double precioTotal, String estado) {
        this.idReserva = idReserva;
        this.nombreCliente = nombreCliente;
        this.nombreHotel = nombreHotel;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precioTotal = precioTotal;
        this.estado = estado;
    }

    // GETTERS: Imprescindibles para que la TableView y el cálculo del total funcionen
    public int getIdReserva() {
        return idReserva;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public String getEstado() {
        return estado;
    }
}