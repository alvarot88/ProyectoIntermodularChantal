package view;

import dao.ClienteDAO;
import dao.HabitacionDAO;
import dao.HotelDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Cliente;
import model.Habitacion;
import model.Hotel;

import java.util.List;

public class PanelReservas extends VBox {

    // Componentes que necesitamos consultar luego
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

        Button btnRefrescarClientes = new Button("🔄"); // Por si añades uno nuevo en la otra pestaña
        seccionCliente.getChildren().addAll(new Label("Cliente:"), cbClientes, btnRefrescarClientes);

        // --- 2. SECCIÓN FILTROS (Hotel, Fechas, Tipo) ---
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(10);

        cbHoteles = new ComboBox<>();
        cbHoteles.setPromptText("Elegir Hotel...");

        cbTipos = new ComboBox<>();
        cbTipos.getItems().addAll("individual", "doble", "suite");
        cbTipos.setPromptText("Tipo...");

        dpInicio = new DatePicker();
        dpFin = new DatePicker();

        grid.add(new Label("Hotel:"), 0, 0);
        grid.add(cbHoteles, 1, 0);
        grid.add(new Label("Tipo:"), 2, 0);
        grid.add(cbTipos, 3, 0);
        grid.add(new Label("Desde:"), 0, 1);
        grid.add(dpInicio, 1, 1);
        grid.add(new Label("Hasta:"), 2, 1);
        grid.add(dpFin, 3, 1);

        // --- 3. TABLA DE HABITACIONES DISPONIBLES ---
        tablaHabitaciones = new TableView<>();
        configurarTabla();

        // --- 4. BOTONES DE ACCIÓN ---
        Button btnBuscar = new Button("Buscar Disponibilidad");
        btnBuscar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        Button btnReservar = new Button("Confirmar Reserva");
        btnReservar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // --- LÓGICA DE CARGA INICIAL ---
        cargarDatosIniciales();

        // --- EVENTOS ---
        btnBuscar.setOnAction(e -> ejecutarBusqueda());
        btnRefrescarClientes.setOnAction(e -> cargarClientes());

        // Añadir todo al panel
        this.getChildren().addAll(
                new Label("PASO 1: SELECCIONAR CLIENTE"), seccionCliente,
                new Separator(),
                new Label("PASO 2: FILTRAR BÚSQUEDA"), grid, btnBuscar,
                new Separator(),
                new Label("PASO 3: ELEGIR HABITACIÓN Y CONFIRMAR"), tablaHabitaciones, btnReservar
        );
    }

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
            // Usamos el método que creamos anteriormente
            List<Habitacion> disponibles = habDAO.buscarHabitacionesConfigurables(h.getIdHotel(), tipo);
            tablaHabitaciones.setItems(FXCollections.observableArrayList(disponibles));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona Hotel y Tipo de habitación");
            alert.show();
        }
    }
}