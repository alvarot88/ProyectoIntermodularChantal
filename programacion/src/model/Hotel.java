package model;

public class Hotel {

    private int idHotel;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String pais;
    private String categoria;

    // CONSTRUCTOR
    public Hotel(int idHotel, String nombre, String direccion,
                 String ciudad, String pais, String categoria) {

        this.idHotel = idHotel;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.categoria = categoria;
    }

    // GETTERS
    public int getIdHotel() { return idHotel; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public String getPais() { return pais; }
    public String getCategoria() { return categoria; }

    // SETTERS
    public void setIdHotel(int idHotel) { this.idHotel = idHotel; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setPais(String pais) { this.pais = pais; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    // TO STRING
    @Override
    public String toString() {
        return nombre + " (" + ciudad + ")";
    }
}