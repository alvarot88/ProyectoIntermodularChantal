package model;

public class Habitacion {

    private int idHabitacion;
    private int idHotel;
    private int numHabitacion;
    private String tipoHabitacion;
    private double precioNoche;
    private String estado;

    // CONSTRUCTOR
    public Habitacion(int idHabitacion, int idHotel, int numHabitacion,
                      String tipoHabitacion, double precioNoche, String estado) {

        this.idHabitacion = idHabitacion;
        this.idHotel = idHotel;
        this.numHabitacion = numHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.precioNoche = precioNoche;
        this.estado = estado;
    }

    // GETTERS
    public int getIdHabitacion() { return idHabitacion; }
    public int getIdHotel() { return idHotel; }
    public int getNumHabitacion() { return numHabitacion; }
    public String getTipoHabitacion() { return tipoHabitacion; }
    public double getPrecioNoche() { return precioNoche; }
    public String getEstado() { return estado; }

    // SETTERS
    public void setIdHabitacion(int idHabitacion) { this.idHabitacion = idHabitacion; }
    public void setIdHotel(int idHotel) { this.idHotel = idHotel; }
    public void setNumHabitacion(int numHabitacion) { this.numHabitacion = numHabitacion; }
    public void setTipoHabitacion(String tipoHabitacion) { this.tipoHabitacion = tipoHabitacion; }
    public void setPrecioNoche(double precioNoche) { this.precioNoche = precioNoche; }
    public void setEstado(String estado) { this.estado = estado; }

    // TO STRING
    @Override
    public String toString() {
        return "Hab " + numHabitacion + " (" + tipoHabitacion + ") - " + precioNoche + "€/noche";
    }
}