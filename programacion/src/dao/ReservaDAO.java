package dao;

import model.Reserva;
import model.ReservaDetalle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // 1. MÉTODO PARA CREAR LA RESERVA (Ya funcionaba joya)
    public int crearReserva(Reserva reserva, int idHabitacion, Date entrada, Date salida, int huespedes, double precio) {
        String sqlReserva = "INSERT INTO reserva (id_cliente, fecha_reserva, estado_reserva, estado_pago) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO reserva_habitacion (id_reserva, id_habitacion, fecha_entrada, fecha_salida, num_huespedes, precio_total) VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;
        try {
            con = ConexionDB.conectar();
            con.setAutoCommit(false);

            // Insertar en 'reserva'
            PreparedStatement psR = con.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);
            psR.setInt(1, reserva.getIdCliente());
            psR.setDate(2, reserva.getFechaReserva());
            psR.setString(3, reserva.getEstadoReserva());
            psR.setString(4, reserva.getEstadoPago());
            psR.executeUpdate();

            ResultSet rs = psR.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            // Insertar en 'reserva_habitacion'
            PreparedStatement psD = con.prepareStatement(sqlDetalle);
            psD.setInt(1, idGenerado);
            psD.setInt(2, idHabitacion);
            psD.setDate(3, entrada);
            psD.setDate(4, salida);
            psD.setInt(5, huespedes);
            psD.setDouble(6, precio);
            psD.executeUpdate();

            con.commit();
            return idGenerado;

        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) { }
            System.out.println("❌ Error: " + e.getMessage());
            return -1;
        } finally {
            if (con != null) try { con.close(); } catch (SQLException e) { }
        }
    } // <--- AQUÍ TERMINA crearReserva

    // 2. MÉTODO PARA EL HISTORIAL (El nuevo)
    public List<ReservaDetalle> obtenerHistorialCompleto() {
        List<ReservaDetalle> lista = new ArrayList<>();

        String sql = "SELECT r.id_reserva, CONCAT(c.nombre, ' ', c.apellidos) AS cliente, " +
                "h.nombre, rh.fecha_entrada, rh.fecha_salida, rh.precio_total, r.estado_reserva " +
                "FROM reserva r " +
                "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN reserva_habitacion rh ON r.id_reserva = rh.id_reserva " +
                "JOIN habitacion hab ON rh.id_habitacion = hab.id_habitacion " +
                "JOIN hotel h ON hab.id_hotel = h.id_hotel " +
                "ORDER BY rh.fecha_entrada DESC";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new ReservaDetalle(
                        rs.getInt("id_reserva"),
                        rs.getString("cliente"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_entrada"),
                        rs.getDate("fecha_salida"),
                        rs.getDouble("precio_total"),
                        rs.getString("estado_reserva")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener historial: " + e.getMessage());
        }
        return lista;
    }
}