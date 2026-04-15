package dao;

import model.Reserva;
import java.sql.*;

public class ReservaDAO {

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
        }
    }
}