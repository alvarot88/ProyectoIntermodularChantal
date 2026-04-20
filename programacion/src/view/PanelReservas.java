package view;

import dao.ClienteDAO;
import dao.HabitacionDAO;
import dao.HotelDAO;
import dao.ReservaDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Cliente;
import model.Habitacion;
import model.Hotel;
import model.Reserva;

import java.util.List;

public class PanelReservas extends VBox {

    private ComboBox<Cliente> cbClientes;
    private ComboBox<Hotel> cbHoteles;
    private ComboBox<String> cbTipos;
    private DatePicker dpInicio, dpFin;
    private TableView<Habitacion> tablaHabitaciones;

    public PanelReservas() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);

        // --- 1. SECCIÓN CLIENTE ---
        HBox seccionCliente = new HBox(10);
        cbClientes = new ComboBox<>();
        cbClientes.setPromptText("Seleccionar Cliente...");
        cbClientes.setPrefWidth(300);
        Button btnRefrescarClientes = new Button("🔄");
        seccionCliente.getChildren().addAll(new Label("Cliente:"), cbClientes, btnRefrescarClientes);

        // --- 2. SECCIÓN FILTROS ---
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(10);
        cbHoteles = new ComboBox<>();
        cbHoteles.setPromptText("Elegir Hotel...");
        cbTipos = new ComboBox<>();
        cbTipos.getItems().addAll("individual", "doble", "suite");
        cbTipos.setPromptText("Tipo...");
        dpInicio = new DatePicker();
        dpFin = new DatePicker();

        grid.add(new Label("Hotel:"), 0, 0); grid.add(cbHoteles, 1, 0);
        grid.add(new Label("Tipo:"), 2, 0); grid.add(cbTipos, 3, 0);
        grid.add(new Label("Desde:"), 0, 1); grid.add(dpInicio, 1, 1);
        grid.add(new Label("Hasta:"), 2, 1); grid.add(dpFin, 3, 1);

        // --- 3. TABLA ---
        tablaHabitaciones = new TableView<>();
        configurarTabla();

        // --- 4. BOTONES ---
        Button btnBuscar = new Button("Buscar Disponibilidad");
        btnBuscar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnReservar = new Button("Confirmar Reserva");
        btnReservar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        cargarDatosIniciales();

        // --- EVENTOS ---
        btnBuscar.setOnAction(e -> ejecutarBusqueda());
        btnRefrescarClientes.setOnAction(e -> cargarClientes());

        // LÓGICA DEL BOTÓN RESERVAR (CORREGIDA)
        btnReservar.setOnAction(e -> {
            Cliente cliente = cbClientes.getValue();
            Habitacion hab = tablaHabitaciones.getSelectionModel().getSelectedItem();
            java.time.LocalDate fechaEntrada = dpInicio.getValue();
            java.time.LocalDate fechaSalida = dpFin.getValue();

            if (cliente == null || hab == null || fechaEntrada == null || fechaSalida == null) {
                mostrarAlerta("Error", "Por favor, completa todos los campos y selecciona una habitación.");
                return;
            }

            // Convertir fechas y crear reserva
            java.sql.Date sqlFechaHoy = java.sql.Date.valueOf(java.time.LocalDate.now());
            java.sql.Date sqlEntrada = java.sql.Date.valueOf(fechaEntrada);
            java.sql.Date sqlSalida = java.sql.Date.valueOf(fechaSalida);

            Reserva nuevaReserva = new Reserva(
                    cliente.getIdCliente(),
                    hab.getIdHabitacion(),
                    sqlFechaHoy,
                    "Confirmada",
                    "Pendiente"
            );

            // Calcular precio y enviar al DAO
            long noches = java.time.temporal.ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
            if (noches <= 0) {
                mostrarAlerta("Error de fechas", "La fecha de salida debe ser posterior a la de entrada.");
                return;
            }
            double precioTotal = noches * hab.getPrecioNoche();

            System.out.println("DEBUG - Cliente seleccionado: " + cliente.getNombre() + " " + cliente.getApellidos());
            System.out.println("DEBUG - ID que Java está enviando: " + cliente.getIdCliente());

            ReservaDAO resDAO = new ReservaDAO();
            int idRes = resDAO.crearReserva(nuevaReserva, hab.getIdHabitacion(), sqlEntrada, sqlSalida, 1, precioTotal);

            if (idRes != -1) {
                mostrarAlerta("¡Éxito!", "Reserva nº " + idRes + " guardada correctamente.");
                ejecutarBusqueda(); // Refrescar tabla
            } else {
                mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
            }
        });

        this.getChildren().addAll(
                new Label("PASO 1: SELECCIONAR CLIENTE"), seccionCliente,
                new Separator(),
                new Label("PASO 2: FILTRAR BÚSQUEDA"), grid, btnBuscar,
                new Separator(),
                new Label("PASO 3: ELEGIR HABITACIÓN Y CONFIRMAR"), tablaHabitaciones, btnReservar
        );
    }

    // Métodos auxiliares (configurarTabla, cargarClientes, mostrarAlerta, etc.) igual que antes
    private void configurarTabla() {
        TableColumn<Habitacion, Integer> colNum = new TableColumn<>("Nº Hab");
        colNum.setCellValueFactory(new PropertyValueFactory<>("numHabitacion"));
        TableColumn<Habitacion, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoHabitacion"));
        TableColumn<Habitacion, Double> colPrecio = new TableColumn<>("Precio/Noche");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioNoche"));
        tablaHabitaciones.getColumns().addAll(colNum, colTipo, colPrecio);
        tablaHabitaciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void cargarDatosIniciales() {
        cargarClientes();
        HotelDAO hDAO = new HotelDAO();
        cbHoteles.setItems(FXCollections.observableArrayList(hDAO.obtenerTodosLosHoteles()));
    }

    private void cargarClientes() {
        ClienteDAO cDAO = new ClienteDAO();
        cbClientes.setItems(FXCollections.observableArrayList(cDAO.obtenerTodos()));
    }

    private void ejecutarBusqueda() {
        Hotel h = cbHoteles.getValue();
        String tipo = cbTipos.getValue();
        if (h != null && tipo != null) {
            HabitacionDAO habDAO = new HabitacionDAO();
            List<Habitacion> disponibles = habDAO.buscarHabitacionesConfigurables(h.getIdHotel(), tipo);
            tablaHabitaciones.setItems(FXCollections.observableArrayList(disponibles));
        } else {
            mostrarAlerta("Atención", "Selecciona Hotel y Tipo");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}