package model;

public class Hotel {
    private int idHotel;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String categoria; 
    private String cif;
    private String gerente;

    public Hotel(int idHotel, String nombre, String direccion, String ciudad, String categoria, String cif, String gerente) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.cif = cif;
        this.gerente = gerente;
    }

}