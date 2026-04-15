package main;

import dao.ClienteDAO;
import dao.HabitacionDAO;
import model.Cliente;
import model.Habitacion;
import dao.ReservaDAO;
import model.Reserva;

import java.sql.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1. Instanciar los DAOs
        ClienteDAO clienteDAO = new ClienteDAO();
        HabitacionDAO HabitacionDAO = new HabitacionDAO();
        ReservaDAO reservaDAO = new ReservaDAO();

        
        Cliente nuevoCliente = new Cliente(
                "Micaela", "García", "DNI", "12345678X",
                "mica@correo.com", "600112233", "F",
                Date.valueOf("1995-05-15"),
                "España", "España"
        );

        
        System.out.println("Intentando insertar cliente...");
        if (clienteDAO.insertarCliente(nuevoCliente)) {
            System.out.println("Cliente insertado con éxito.");
        } else {
            System.out.println("No se pudo insertar el cliente (quizás el email ya existe).");
        }

        
        System.out.println("\n--- LISTA ACTUALIZADA DE CLIENTES ---");
        for (Cliente c : clienteDAO.obtenerTodos()) {
            System.out.println(c.getApellidos() + " | " + c.getEmail());
        }

        
        List<Habitacion> disponibles = HabitacionDAO.listarDisponibles();

        System.out.println("\nHABITACIONES LIBRES:");
        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles ahora mismo.");
        } else {
            for (Habitacion h : disponibles) {
                System.out.println(h);
            }
        }

        Reserva miReserva = new Reserva(1, Date.valueOf("2024-05-20"), "Confirmada", "Pendiente");

        int id = reservaDAO.crearReserva(miReserva, 1,
                Date.valueOf("2024-06-01"), Date.valueOf("2024-06-05"),
                2, 500.00);

        if (id != -1) {
            System.out.println("Reserva guardada con ID: " + id);
        }

    }
}