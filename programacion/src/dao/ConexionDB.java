package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_hoteles";
    private static final String USER = "root";
    private static final String PASS = "";

    // GET CONNECTION
    public static Connection conectar() {

        try {
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la BD: " + e.getMessage());
            return null;
        }
    }

    // TEST CONNECTION
    public static boolean testConexion() {

        try (Connection con = conectar()) {
            return con != null && !con.isClosed();

        } catch (SQLException e) {
            System.out.println("❌ Error en test de conexión: " + e.getMessage());
            return false;
        }
    }
}