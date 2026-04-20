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

        // 1. Necesitamos una fecha para el objeto Reserva
        java.sql.Date fechaHoy = new java.sql.Date(System.currentTimeMillis());

        // 2. Creamos el objeto Reserva (usando 'r' como nombre)
        // Datos: idCliente=1, idHabitacion=1, fecha, estado, pago
        Reserva r = new Reserva(1, 1, fechaHoy, "Confirmada", "Pendiente");

        // 3. Llamamos al DAO usando 'r' (no miReserva)
        int id = reservaDAO.crearReserva(
                r,
                1, // idHabitacion
                Date.valueOf("2024-06-01"),
                Date.valueOf("2024-06-05"),
                2,
                500.00
        );

        if (id != -1) {
            System.out.println("Reserva guardada con ID: " + id);
        } else {
            System.out.println("No se pudo crear la reserva de prueba.");
        }

    }
}