package view;

import dao.ClienteDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Cliente;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PanelClientes extends VBox {

    private TableView<Cliente> tabla;
    private TextField txtBuscar;

    private TextField txtNombre, txtApellidos, txtDoc, txtEmail, txtTelefono;
    private DatePicker dpFechaNacimiento;

    private ComboBox<String> cbTipoDoc, cbPais, cbNacionalidad;
    private RadioButton rbM, rbF;
    private ToggleGroup grupoGenero;

    private Cliente clienteSeleccionado;
    private ClienteDAO dao = new ClienteDAO();

    public PanelClientes() {
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        Label titulo = new Label("GESTIÓN DE CLIENTES");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // 🔍 BUSCADOR
        txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por nombre, apellido, DNI o email...");
        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> filtrar(newVal));

        // 📋 TABLA
        tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        configurarTabla();
        actualizarTabla();

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) cargarDatos(n);
        });

        // 🧾 FORMULARIO
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        txtNombre = new TextField();
        txtApellidos = new TextField();
        txtDoc = new TextField();
        txtEmail = new TextField();
        txtTelefono = new TextField();
        dpFechaNacimiento = new DatePicker();

        cbTipoDoc = new ComboBox<>();
        cbTipoDoc.getItems().addAll("DNI", "PAS");
        cbTipoDoc.setValue("DNI");

        rbM = new RadioButton("Masculino");
        rbF = new RadioButton("Femenino");
        grupoGenero = new ToggleGroup();
        rbM.setToggleGroup(grupoGenero);
        rbF.setToggleGroup(grupoGenero);
        rbM.setSelected(true);

        cbPais = new ComboBox<>();
        cbPais.getItems().addAll("España", "Francia", "Portugal", "Italia", "Reino Unido");

        cbNacionalidad = new ComboBox<>();
        cbNacionalidad.getItems().addAll("España", "Francia", "Portugal", "Italia", "Reino Unido");

        cbPais.setOnAction(e -> cbNacionalidad.setValue(cbPais.getValue()));

        // 📌 FORM LAYOUT
        form.add(new Label("Nombre:"), 0, 0); form.add(txtNombre, 1, 0);
        form.add(new Label("Apellidos:"), 0, 1); form.add(txtApellidos, 1, 1);
        form.add(new Label("Tipo Doc:"), 0, 2); form.add(cbTipoDoc, 1, 2);
        form.add(new Label("Documento:"), 0, 3); form.add(txtDoc, 1, 3);
        form.add(new Label("Género:"), 0, 4); form.add(new HBox(10, rbM, rbF), 1, 4);
        form.add(new Label("Email:"), 0, 5); form.add(txtEmail, 1, 5);
        form.add(new Label("Teléfono:"), 0, 6); form.add(txtTelefono, 1, 6);
        form.add(new Label("Nacimiento:"), 0, 7); form.add(dpFechaNacimiento, 1, 7);
        form.add(new Label("País residencia:"), 0, 8); form.add(cbPais, 1, 8);
        form.add(new Label("Nacionalidad:"), 0, 9); form.add(cbNacionalidad, 1, 9);

        // 🔘 BOTONES
        Button btnNuevo = new Button("Nuevo");
        Button btnGuardar = new Button("Guardar");
        Button btnEliminar = new Button("Eliminar");

        btnNuevo.setOnAction(e -> limpiarFormulario());
        btnGuardar.setOnAction(e -> guardar());
        btnEliminar.setOnAction(e -> eliminar());

        HBox botones = new HBox(10, btnNuevo, btnGuardar, btnEliminar);

        this.getChildren().addAll(
                titulo,
                txtBuscar,
                tabla,
                new Separator(),
                form,
                botones
        );
    }

    // ================= TABLA =================

    private void configurarTabla() {

        tabla.getColumns().add(col("Nombre", "nombre"));
        tabla.getColumns().add(col("Apellidos", "apellidos"));
        tabla.getColumns().add(col("Tipo Doc", "tipoDocumento"));
        tabla.getColumns().add(col("Documento", "numDocumento"));
        tabla.getColumns().add(col("Género", "genero"));
        tabla.getColumns().add(col("Email", "email"));
        tabla.getColumns().add(col("Teléfono", "telefono"));
        tabla.getColumns().add(col("Nacimiento", "fechaNacimiento"));
        tabla.getColumns().add(col("País", "paisResidencia"));
        tabla.getColumns().add(col("Nacionalidad", "nacionalidad"));
    }

    private TableColumn<Cliente, ?> col(String title, String property) {
        TableColumn<Cliente, Object> c = new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(property));
        return c;
    }

    private void actualizarTabla() {
        tabla.setItems(FXCollections.observableArrayList(dao.obtenerTodos()));
    }

    // ================= BUSCADOR =================

    private void filtrar(String texto) {
        List<Cliente> lista = dao.obtenerTodos();

        List<Cliente> filtrados = lista.stream()
                .filter(c ->
                        c.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                                c.getApellidos().toLowerCase().contains(texto.toLowerCase()) ||
                                c.getNumDocumento().toLowerCase().contains(texto.toLowerCase()) ||
                                (c.getEmail() != null && c.getEmail().toLowerCase().contains(texto.toLowerCase()))
                )
                .collect(Collectors.toList());

        tabla.setItems(FXCollections.observableArrayList(filtrados));
    }

    // ================= FORM =================

    private void cargarDatos(Cliente c) {
        clienteSeleccionado = c;

        txtNombre.setText(c.getNombre());
        txtApellidos.setText(c.getApellidos());
        txtDoc.setText(c.getNumDocumento());
        txtEmail.setText(c.getEmail());
        txtTelefono.setText(c.getTelefono());
        dpFechaNacimiento.setValue(c.getFechaNacimiento().toLocalDate());

        cbTipoDoc.setValue(c.getTipoDocumento());

        if ("M".equals(c.getGenero())) rbM.setSelected(true);
        else rbF.setSelected(true);

        cbPais.setValue(c.getPaisResidencia());
        cbNacionalidad.setValue(c.getNacionalidad());
    }

    private void limpiarFormulario() {
        clienteSeleccionado = null;

        txtNombre.clear();
        txtApellidos.clear();
        txtDoc.clear();
        txtEmail.clear();
        txtTelefono.clear();
        dpFechaNacimiento.setValue(null);

        cbTipoDoc.setValue("DNI");
        rbM.setSelected(true);
        cbPais.setValue(null);
        cbNacionalidad.setValue(null);
    }

    // ================= CRUD =================

    private void guardar() {
        try {
            String genero = rbM.isSelected() ? "M" : "F";

            Cliente c = new Cliente(
                    clienteSeleccionado != null ? clienteSeleccionado.getIdCliente() : 0,
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    cbTipoDoc.getValue(),
                    txtDoc.getText(),
                    txtEmail.getText(),
                    txtTelefono.getText(),
                    genero,
                    Date.valueOf(dpFechaNacimiento.getValue()),
                    cbPais.getValue(),
                    cbNacionalidad.getValue()
            );

            if (clienteSeleccionado == null) {
                dao.insertarCliente(c);
            } else {
                dao.actualizarCliente(c);
            }

            actualizarTabla();
            limpiarFormulario();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void eliminar() {
        if (clienteSeleccionado == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar cliente?");
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                if (dao.eliminarCliente(clienteSeleccionado.getIdCliente())) {
                    actualizarTabla();
                    limpiarFormulario();
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se puede eliminar (tiene reservas)").show();
                }
            }
        });
    }
}