package dao;

import model.Reserva;
import model.ReservaDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // CREATE RESERVA
    public int crearReserva(Reserva reserva, int idHabitacion, Date entrada, Date salida, int huespedes, double precio) {

        String sqlReserva =
                "INSERT INTO reserva (id_cliente, fecha_reserva, estado_reserva, estado_pago) VALUES (?, ?, ?, ?)";

        String sqlDetalle =
                "INSERT INTO reserva_habitacion (id_reserva, id_habitacion, fecha_entrada, fecha_salida, num_huespedes, precio_total) VALUES (?, ?, ?, ?, ?, ?)";

        Connection con = null;

        try {
            con = ConexionDB.conectar();
            con.setAutoCommit(false);

            PreparedStatement psR = con.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);

            psR.setInt(1, reserva.getIdCliente());
            psR.setDate(2, reserva.getFechaReserva());
            psR.setString(3, reserva.getEstadoReserva());
            psR.setString(4, reserva.getEstadoPago());
            psR.executeUpdate();

            ResultSet rs = psR.getGeneratedKeys();

            int idReserva = 0;
            if (rs.next()) {
                idReserva = rs.getInt(1);
            }

            PreparedStatement psD = con.prepareStatement(sqlDetalle);

            psD.setInt(1, idReserva);
            psD.setInt(2, idHabitacion);
            psD.setDate(3, entrada);
            psD.setDate(4, salida);
            psD.setInt(5, huespedes);
            psD.setDouble(6, precio);
            psD.executeUpdate();

            con.commit();
            return idReserva;

        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) {}
            System.out.println("❌ Error crear reserva: " + e.getMessage());
            return -1;
        } finally {
            if (con != null) try { con.close(); } catch (SQLException e) {}
        }
    }

    // LISTAR TODAS (USO PRINCIPAL UI)
    public List<ReservaDetalle> listarReservas() {

        List<ReservaDetalle> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                        "r.id_reserva, " +
                        "CONCAT(c.nombre, ' ', c.apellidos) AS cliente, " +
                        "h.nombre AS hotel, " +
                        "rh.fecha_entrada, " +
                        "rh.fecha_salida, " +
                        "rh.precio_total, " +
                        "r.estado_reserva " +
                        "FROM reserva r " +
                        "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                        "JOIN reserva_habitacion rh ON r.id_reserva = rh.id_reserva " +
                        "JOIN habitacion hab ON rh.id_habitacion = hab.id_habitacion " +
                        "JOIN hotel h ON hab.id_hotel = h.id_hotel " +
                        "ORDER BY r.id_reserva DESC";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new ReservaDetalle(
                        rs.getInt("id_reserva"),
                        rs.getString("cliente"),
                        rs.getString("hotel"),
                        rs.getDate("fecha_entrada"),
                        rs.getDate("fecha_salida"),
                        rs.getDouble("precio_total"),
                        rs.getString("estado_reserva")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error listar reservas: " + e.getMessage());
        }

        return lista;
    }

    // FILTRAR POR CLIENTE (USO OPCIONAL UI)
    public List<ReservaDetalle> listarPorCliente(int idCliente) {

        List<ReservaDetalle> lista = new ArrayList<>();

        String sql =
                "SELECT " +
                        "r.id_reserva, " +
                        "CONCAT(c.nombre, ' ', c.apellidos) AS cliente, " +
                        "h.nombre AS hotel, " +
                        "rh.fecha_entrada, " +
                        "rh.fecha_salida, " +
                        "rh.precio_total, " +
                        "r.estado_reserva " +
                        "FROM reserva r " +
                        "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                        "JOIN reserva_habitacion rh ON r.id_reserva = rh.id_reserva " +
                        "JOIN habitacion hab ON rh.id_habitacion = hab.id_habitacion " +
                        "JOIN hotel h ON hab.id_hotel = h.id_hotel " +
                        "WHERE r.id_cliente = ? " +
                        "ORDER BY r.id_reserva DESC";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new ReservaDetalle(
                        rs.getInt("id_reserva"),
                        rs.getString("cliente"),
                        rs.getString("hotel"),
                        rs.getDate("fecha_entrada"),
                        rs.getDate("fecha_salida"),
                        rs.getDouble("precio_total"),
                        rs.getString("estado_reserva")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error listar por cliente: " + e.getMessage());
        }

        return lista;
    }

    // DELETE
    public boolean eliminarReserva(int idReserva) {

        String sqlDetalle = "DELETE FROM reserva_habitacion WHERE id_reserva = ?";
        String sqlReserva = "DELETE FROM reserva WHERE id_reserva = ?";

        try (Connection con = ConexionDB.conectar()) {

            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(sqlDetalle);
            ps1.setInt(1, idReserva);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(sqlReserva);
            ps2.setInt(1, idReserva);
            ps2.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error eliminar reserva: " + e.getMessage());
            return false;
        }
    }
}