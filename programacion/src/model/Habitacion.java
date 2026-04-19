package model;

public class Habitacion {
    private int idHabitacion;
    private int idHotel;
    private int numHabitacion;
    private String tipoHabitacion;
    private double precioNoche;
    private String estado;

    
    public Habitacion(int idHabitacion, int idHotel, int numHabitacion, String tipoHabitacion, double precioNoche, String estado) {
        this.idHabitacion = idHabitacion;
        this.idHotel = idHotel;
        this.numHabitacion = numHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.precioNoche = precioNoche;
        this.estado = estado;
    }

    public int getIdHabitacion() { return idHabitacion; }
    public int getIdHotel() { return idHotel; }
    public int getNumHabitacion() { return numHabitacion; }
    public String getTipoHabitacion() { return tipoHabitacion; }
    public double getPrecioNoche() { return precioNoche; }
    public String getEstado() { return estado; }

    @Override
    public String toString() {
        return "Habitacion " + numHabitacion + " [" + tipoHabitacion + "] - " + precioNoche + "€/noche (" + estado + ")";
    }
    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }


}