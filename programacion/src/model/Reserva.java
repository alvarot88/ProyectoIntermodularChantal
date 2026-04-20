package model;
import java.sql.Date;

public class Reserva {
    private int idReserva;
    private int idCliente;
    private int idHabitacion; // Asegúrate de que este campo exista
    private Date fechaReserva;
    private String estadoReserva;
    private String estadoPago;

    // --- CONSTRUCTOR CORREGIDO (Añadido idHabitacion como parámetro) ---
    public Reserva(int idCliente, int idHabitacion, Date fechaReserva, String estadoReserva, String estadoPago) {
        this.idCliente = idCliente;
        this.idHabitacion = idHabitacion; // Ahora sí funcionará
        this.fechaReserva = fechaReserva;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
    }

    // --- GETTERS CORREGIDOS ---
    public int getIdCliente() { return idCliente; }

    // Añadido el punto y coma (;) que faltaba
    public int getIdHabitacion() { return idHabitacion; }

    public Date getFechaReserva() { return fechaReserva; }
    public String getEstadoReserva() { return estadoReserva; }
    public String getEstadoPago() { return estadoPago; }
}