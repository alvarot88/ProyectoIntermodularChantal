package dao;

import model.Habitacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO { // <-- La llave de la CLASE abre aquí

    public List<Habitacion> listarDisponibles() { // <-- La llave del MÉTODO abre aquí
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
    } // <-- Aquí cierra el método

} // <-- Aquí cierra la clase (MUY IMPORTANTE)