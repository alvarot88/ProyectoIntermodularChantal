package view;

import dao.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Cliente;
import java.sql.Date;

public class FormularioCliente extends VBox {

    // Declaramos los componentes como atributos para acceder a ellos fácilmente
    private TextField txtNombre, txtApellidos, txtDoc, txtEmail, txtTelefono;
    private DatePicker dpFechaNacimiento;
    private Button btnGuardar;

    public FormularioCliente() {
        // Configuración del contenedor principal (VBox)
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        Label titulo = new Label("REGISTRO DE NUEVO CLIENTE");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // Usamos un GridPane para que las etiquetas y cuadros queden alineados
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Inicializamos los campos
        txtNombre = new TextField();
        txtApellidos = new TextField();
        txtDoc = new TextField();
        txtEmail = new TextField();
        txtTelefono = new TextField();
        dpFechaNacimiento = new DatePicker();

        // Los colocamos en el grid (Columna, Fila)
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Apellidos:"), 0, 1);
        grid.add(txtApellidos, 1, 1);
        grid.add(new Label("Documento (DNI):"), 0, 2);
        grid.add(txtDoc, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(txtEmail, 1, 3);
        grid.add(new Label("Teléfono:"), 0, 4);
        grid.add(txtTelefono, 1, 4);
        grid.add(new Label("F. Nacimiento:"), 0, 5);
        grid.add(dpFechaNacimiento, 1, 5);

        btnGuardar = new Button("Guardar Cliente");
        btnGuardar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Acción del botón
        btnGuardar.setOnAction(e -> guardarCliente());

        // Añadimos todo al VBox (esta misma clase)
        this.getChildren().addAll(titulo, grid, btnGuardar);
    }

    private void guardarCliente() {
        try {
            // Creamos el objeto cliente con los datos de los campos
            Cliente nuevo = new Cliente(
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    "DNI", // Podrías añadir un ComboBox más adelante
                    txtDoc.getText(),
                    txtEmail.getText(),
                    txtTelefono.getText(),
                    "M", // Género por defecto o añadir radio buttons
                    Date.valueOf(dpFechaNacimiento.getValue()),
                    "España",
                    "Española"
            );

            ClienteDAO dao = new ClienteDAO();
            if (dao.insertarCliente(nuevo)) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION, "¡Cliente registrado con éxito!");
                alerta.show();
                limpiarCampos();
            }
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error en los datos: " + ex.getMessage());
            alerta.show();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidos.clear();
        txtDoc.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);
    }
}