package dao;

import model.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    // GET ALL HOTELS
    public List<Hotel> obtenerTodosLosHoteles() {

        List<Hotel> hoteles = new ArrayList<>();

        String sql = "SELECT * FROM hotel ORDER BY nombre";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                hoteles.add(new Hotel(
                        rs.getInt("id_hotel"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("pais"),
                        rs.getString("categoria")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar hoteles: " + e.getMessage());
        }

        return hoteles;
    }

    // GET BY ID
    public Hotel obtenerPorId(int idHotel) {

        String sql = "SELECT * FROM hotel WHERE id_hotel = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idHotel);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Hotel(
                        rs.getInt("id_hotel"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("pais"),
                        rs.getString("categoria")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error buscando hotel: " + e.getMessage());
        }

        return null;
    }
}