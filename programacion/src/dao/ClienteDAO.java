package dao;

import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // 1. OBTENER TODOS LOS CLIENTES
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("tipo_documento"),
                        rs.getString("num_documento"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("genero"),
                        rs.getDate("fecha_nacimiento"),
                        rs.getString("pais_residencia"),
                        rs.getString("nacionalidad")
                );
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // 2. INSERTAR NUEVO CLIENTE
    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellidos, tipo_documento, num_documento, email, " +
                "telefono, genero, fecha_nacimiento, pais_residencia, nacionalidad) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getTipoDocumento());
            ps.setString(4, cliente.getNumDocumento());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getTelefono());
            ps.setString(7, cliente.getGenero());
            ps.setDate(8, cliente.getFechaNacimiento());
            ps.setString(9, cliente.getPaisResidencia());
            ps.setString(10, cliente.getNacionalidad());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // 3. ACTUALIZAR CLIENTE EXISTENTE
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre=?, apellidos=?, email=?, telefono=? WHERE id_cliente=?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getIdCliente());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR CLIENTE
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}