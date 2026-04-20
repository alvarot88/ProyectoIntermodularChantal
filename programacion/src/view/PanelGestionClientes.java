package view;

import dao.ClienteDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Cliente;

public class PanelGestionClientes extends HBox {

    private TableView<Cliente> tabla;
    private TextField txtNombre, txtApellidos, txtEmail, txtTelefono;
    private Cliente clienteSeleccionado;
    private ClienteDAO clienteDAO = new ClienteDAO();

    public PanelGestionClientes() {
        this.setPadding(new Insets(20));
        this.setSpacing(20);

        // --- IZQUIERDA: TABLA ---
        VBox izquierda = new VBox(10);
        tabla = new TableView<>();
        configurarTabla();
        actualizarTabla();

        // Al tocar un cliente, cargar sus datos en los campos
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) cargarDatosCliente(newVal);
        });

        izquierda.getChildren().addAll(new Label("Listado de Clientes"), tabla);
        HBox.setHgrow(izquierda, Priority.ALWAYS);

        // --- DERECHA: FORMULARIO DE EDICIÓN ---
        VBox derecha = new VBox(10);
        derecha.setMinWidth(250);

        txtNombre = new TextField();
        txtApellidos = new TextField();
        txtEmail = new TextField();
        txtTelefono = new TextField();

        Button btnGuardar = new Button("Actualizar Datos");
        btnGuardar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

        Button btnEliminar = new Button("Eliminar Cliente");
        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        derecha.getChildren().addAll(
                new Label("Editar Cliente"),
                new Label("Nombre:"), txtNombre,
                new Label("Apellidos:"), txtApellidos,
                new Label("Email:"), txtEmail,
                new Label("Teléfono:"), txtTelefono,
                btnGuardar, btnEliminar
        );

        // --- LÓGICA DE BOTONES ---
        btnGuardar.setOnAction(e -> ejecutarActualizacion());
        btnEliminar.setOnAction(e -> ejecutarEliminacion());

        this.getChildren().addAll(izquierda, derecha);
    }

    private void configurarTabla() {
        TableColumn<Cliente, String> colNom = new TableColumn<>("Nombre");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TableColumn<Cliente, String> colApe = new TableColumn<>("Apellidos");
        colApe.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabla.getColumns().addAll(colNom, colApe, colEmail);
    }

    private void actualizarTabla() {
        tabla.setItems(FXCollections.observableArrayList(clienteDAO.obtenerTodos()));
    }

    private void cargarDatosCliente(Cliente c) {
        this.clienteSeleccionado = c;
        txtNombre.setText(c.getNombre());
        txtApellidos.setText(c.getApellidos());
        txtEmail.setText(c.getEmail());
        txtTelefono.setText(c.getTelefono());
    }

    private void ejecutarActualizacion() {
        if (clienteSeleccionado == null) return;

        // Creamos un objeto cliente con los datos nuevos pero el MISMO ID
        Cliente editado = new Cliente(
                clienteSeleccionado.getIdCliente(),
                txtNombre.getText(),
                txtApellidos.getText(),
                clienteSeleccionado.getTipoDocumento(),
                clienteSeleccionado.getNumDocumento(),
                txtEmail.getText(),
                txtTelefono.getText(),
                clienteSeleccionado.getGenero(),
                clienteSeleccionado.getFechaNacimiento(),
                clienteSeleccionado.getPaisResidencia(),
                clienteSeleccionado.getNacionalidad()
        );

        if (clienteDAO.actualizarCliente(editado)) {
            actualizarTabla();
            mostrarAlerta("Éxito", "Cliente actualizado correctamente.");
        }
    }

    private void ejecutarEliminacion() {
        if (clienteSeleccionado == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar a este cliente?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (clienteDAO.eliminarCliente(clienteSeleccionado.getIdCliente())) {
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se puede eliminar un cliente que tiene reservas activas.");
                }
            }
        });
    }

    private void limpiarCampos() {
        txtNombre.clear(); txtApellidos.clear(); txtEmail.clear(); txtTelefono.clear();
        clienteSeleccionado = null;
    }

    private void mostrarAlerta(String t, String m) {
        new Alert(Alert.AlertType.INFORMATION, m).show();
    }
}