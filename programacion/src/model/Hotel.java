package model;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private String categoria;

    public Hotel(int idHotel, String nombre, String direccion, String ciudad, String pais, String categoria) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.categoria = categoria;
    }

    // --- GETTERS (Esto es lo que te pide PanelReservas) ---

    public int getIdHotel() {
        return idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    // --- El método mágico para el ComboBox ---
    @Override
    public String toString() {
        return nombre + " (" + ciudad + ")";
    }

    // Si necesitas más getters para la dirección o ciudad, puedes añadirlos igual que el de nombre
}