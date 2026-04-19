package dao;

import model.Habitacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    public List<Habitacion> buscarHabitacionesConfigurables(int idHotel, String tipo) {
        List<Habitacion> lista = new ArrayList<>();
        // Buscamos por Hotel, por Tipo y que estén disponibles
        String sql = "SELECT * FROM habitacion WHERE id_hotel = ? AND tipo_habitacion = ? AND estado = 'disponible'";

        try (Connection con = ConexionDB.conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHotel);
            ps.setString(2, tipo);

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al filtrar: " + e.getMessage());
        }
        return lista;
    }

    public List<Habitacion> listarDisponibles() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE estado = 'disponible'";

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
            System.out.println("❌ Error: " + e.getMessage());
        }
        return lista;
    }

}