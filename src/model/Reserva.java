package model;
import java.sql.Date;

public class Reserva {
    private int idReserva;
    private int idCliente;
    private Date fechaReserva;
    private String estadoReserva; 
    private String estadoPago;

    public Reserva(int idCliente, Date fechaReserva, String estadoReserva, String estadoPago) {
        this.idCliente = idCliente;
        this.fechaReserva = fechaReserva;
        this.estadoReserva = estadoReserva;
        this.estadoPago = estadoPago;
    }

    
    public int getIdCliente() { return idCliente; }
    public Date getFechaReserva() { return fechaReserva; }
    public String getEstadoReserva() { return estadoReserva; }
    public String getEstadoPago() { return estadoPago; }
}