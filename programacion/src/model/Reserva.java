package model;

import java.sql.Date;

public class Reserva {

    private int idReserva;
    private int idCliente;
    private int idHabitacion;

    private Date fechaReserva;
    private Date fechaEntrada;
    private Date fechaSalida;

    private String estadoReserva;
    private String estadoPago;

    private double precioTotal;

    // ================= CONSTRUCTOR VACÍO =================
    public Reserva() {}

    // ================= CONSTRUCTOR COMPLETO =================
    public Reserva(int idReserva, int idCliente, int idHabitacion,
                   Date fechaReserva, Date fechaEntrada, Date fechaSalida,
                   String estadoReserva, String estadoPago, double precioTotal) {

        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idHabitacion = idHabitacion;
        this.fechaReserva = fechaReserva;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
        this.precioTotal = precioTotal;
    }

    // ================= CONSTRUCTOR SIMPLE (CREATE) =================
    public Reserva(int idCliente, int idHabitacion,
                   Date fechaReserva,
                   String estadoReserva,
                   String estadoPago) {

        this.idCliente = idCliente;
        this.idHabitacion = idHabitacion;
        this.fechaReserva = fechaReserva;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
    }

    // ================= GETTERS =================

    public int getIdReserva() { return idReserva; }

    public int getIdCliente() { return idCliente; }

    public int getIdHabitacion() { return idHabitacion; }

    public Date getFechaReserva() { return fechaReserva; }

    public Date getFechaEntrada() { return fechaEntrada; }

    public Date getFechaSalida() { return fechaSalida; }

    public String getEstadoReserva() { return estadoReserva; }

    public String getEstadoPago() { return estadoPago; }

    public double getPrecioTotal() { return precioTotal; }

    // ================= SETTERS =================

    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }

    public void setFechaReserva(Date fechaReserva) { this.fechaReserva = fechaReserva; }

    public void setFechaEntrada(Date fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }

    public void setEstadoReserva(String estadoReserva) { this.estadoReserva = estadoReserva; }

    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
}