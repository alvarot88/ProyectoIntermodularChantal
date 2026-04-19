package dao;

import model.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    public List<Hotel> obtenerTodosLosHoteles() {
        List<Hotel> hoteles = new ArrayList<>();
        String sql = "SELECT * FROM hotel";

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
                        rs.getString("estrellas"),
                        rs.getString("telefono")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar hoteles: " + e.getMessage());
        }
        return hoteles;
    }
}