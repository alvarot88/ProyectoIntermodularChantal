package dao;

import model.Habitacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    // GET HABITACIONES POR HOTEL Y TIPO (SIN FECHAS)
    public List<Habitacion> buscarPorHotelYTipo(int idHotel, String tipo) {

        List<Habitacion> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM habitacion " +
                        "WHERE id_hotel = ? " +
                        "AND tipo_habitacion = ? " +
                        "AND estado = 'activo'";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHotel);
            ps.setString(2, tipo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Habitacion(
                        rs.getInt("id_habitacion"),
                        rs.getInt("id_hotel"),
                        rs.getInt("num_habitacion"),
                        rs.getString("tipo_habitacion"),
                        rs.getDouble("precio_noche"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error filtrando habitaciones: " + e.getMessage());
        }

        return lista;
    }

    // CHECK DISPONIBILIDAD REAL (CON FECHAS)  ✅ ESTE ES EL IMPORTANTE
    public List<Habitacion> buscarDisponibles(int idHotel, String tipo, Date entrada, Date salida) {

        List<Habitacion> lista = new ArrayList<>();

        String sql =
                "SELECT h.* " +
                        "FROM habitacion h " +
                        "WHERE h.id_hotel = ? " +
                        "AND h.tipo_habitacion = ? " +
                        "AND h.estado = 'activo' " +
                        "AND h.id_habitacion NOT IN ( " +
                        "   SELECT rh.id_habitacion " +
                        "   FROM reserva_habitacion rh " +
                        "   JOIN reserva r ON r.id_reserva = rh.id_reserva " +
                        "   WHERE NOT (rh.fecha_salida <= ? OR rh.fecha_entrada >= ?) " +
                        ")";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHotel);
            ps.setString(2, tipo);
            ps.setDate(3, entrada);
            ps.setDate(4, salida);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Habitacion(
                        rs.getInt("id_habitacion"),
                        rs.getInt("id_hotel"),
                        rs.getInt("num_habitacion"),
                        rs.getString("tipo_habitacion"),
                        rs.getDouble("precio_noche"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error comprobando disponibilidad: " + e.getMessage());
        }

        return lista;
    }

    // LISTAR TODAS ACTIVAS
    public List<Habitacion> listarTodas() {

        List<Habitacion> lista = new ArrayList<>();

        String sql = "SELECT * FROM habitacion WHERE estado = 'activo'";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Habitacion(
                        rs.getInt("id_habitacion"),
                        rs.getInt("id_hotel"),
                        rs.getInt("num_habitacion"),
                        rs.getString("tipo_habitacion"),
                        rs.getDouble("precio_noche"),
                        rs.getString("estado")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error listando habitaciones: " + e.getMessage());
        }

        return lista;
    }
}