package view;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PanelReservas extends VBox {

    // ================= UI =================
    private TextField txtBuscarCliente;
    private ListView<Cliente> listClientes;

    private ComboBox<Hotel> cbHotel;
    private ComboBox<String> cbTipo;

    private DatePicker dpEntrada;
    private DatePicker dpSalida;

    private TableView<ReservaDetalle> tabla;

    private Button btnComprobar;
    private Button btnCrear;
    private Button btnEliminar;

    // ================= STATE =================
    private Cliente clienteSeleccionado;

    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final HabitacionDAO habitacionDAO = new HabitacionDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final HotelDAO hotelDAO = new HotelDAO();

    public PanelReservas() {

        setPadding(new Insets(15));
        setSpacing(10);

        // ================= CLIENTE SEARCH =================
        txtBuscarCliente = new TextField();
        txtBuscarCliente.setPromptText("Buscar cliente...");

        listClientes = new ListView<>();
        listClientes.setPrefHeight(120);

        txtBuscarCliente.textProperty().addListener((obs, o, n) -> filtrarClientes(n));

        listClientes.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            clienteSeleccionado = n;
        });

        VBox boxClientes = new VBox(5, txtBuscarCliente, listClientes);

        // ================= FORM =================
        cbHotel = new ComboBox<>();
        cbHotel.setItems(FXCollections.observableArrayList(hotelDAO.obtenerTodosLosHoteles()));

        cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("individual", "doble", "suite");

        dpEntrada = new DatePicker();
        dpSalida = new DatePicker();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Hotel"), 0, 0);
        form.add(cbHotel, 1, 0);
        form.add(new Label("Tipo"), 2, 0);
        form.add(cbTipo, 3, 0);

        form.add(new Label("Entrada"), 0, 1);
        form.add(dpEntrada, 1, 1);
        form.add(new Label("Salida"), 2, 1);
        form.add(dpSalida, 3, 1);

        // ================= BOTONES =================
        btnComprobar = new Button("Comprobar disponibilidad");
        btnCrear = new Button("Crear reserva");
        btnEliminar = new Button("Eliminar");

        btnCrear.setDisable(true);

        HBox botones = new HBox(10, btnComprobar, btnCrear, btnEliminar);

        // ================= TABLA =================
        tabla = new TableView<>();

        TableColumn<ReservaDetalle, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idReserva"));

        TableColumn<ReservaDetalle, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<ReservaDetalle, String> colHotel = new TableColumn<>("Hotel");
        colHotel.setCellValueFactory(new PropertyValueFactory<>("nombreHotel"));

        TableColumn<ReservaDetalle, Date> colIn = new TableColumn<>("Entrada");
        colIn.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));

        TableColumn<ReservaDetalle, Date> colOut = new TableColumn<>("Salida");
        colOut.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));

        TableColumn<ReservaDetalle, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        tabla.getColumns().addAll(colId, colCliente, colHotel, colIn, colOut, colTotal);

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ================= EVENTS =================
        btnComprobar.setOnAction(e -> comprobarDisponibilidad());
        btnCrear.setOnAction(e -> crearReserva());
        btnEliminar.setOnAction(e -> eliminarReserva());

        // ================= LOAD =================
        cargarClientes();
        cargarReservas();

        // ================= LAYOUT =================
        getChildren().addAll(
                new Label("CLIENTE"),
                boxClientes,

                new Separator(),

                new Label("NUEVA RESERVA"),
                form,
                botones,

                new Separator(),

                new Label("RESERVAS"),
                tabla
        );
    }

    // ================= CLIENTES =================
    private void cargarClientes() {
        listClientes.setItems(FXCollections.observableArrayList(clienteDAO.obtenerTodos()));
    }

    private void filtrarClientes(String txt) {
        List<Cliente> todos = clienteDAO.obtenerTodos();

        listClientes.setItems(FXCollections.observableArrayList(
                todos.stream()
                        .filter(c -> (c.getNombre() + " " + c.getApellidos())
                                .toLowerCase()
                                .contains(txt.toLowerCase()))
                        .toList()
        ));
    }

    // ================= RESERVAS =================
    private void cargarReservas() {
        tabla.setItems(FXCollections.observableArrayList(reservaDAO.listarReservas()));
    }

    // ================= DISPONIBILIDAD =================
    private void comprobarDisponibilidad() {

        if (clienteSeleccionado == null ||
                cbHotel.getValue() == null ||
                cbTipo.getValue() == null ||
                dpEntrada.getValue() == null ||
                dpSalida.getValue() == null) {

            alerta("Completa todos los campos");
            btnCrear.setDisable(true);
            return;
        }

        List<Habitacion> libres = habitacionDAO.buscarHabitacionesDisponibles(
                cbHotel.getValue().getIdHotel(),
                cbTipo.getValue(),
                Date.valueOf(dpEntrada.getValue()),
                Date.valueOf(dpSalida.getValue())
        );

        if (libres.isEmpty()) {
            alerta("No hay disponibilidad");
            btnCrear.setDisable(true);
        } else {
            alerta("Hay disponibilidad");
            btnCrear.setDisable(false);
        }
    }

    // ================= CREAR =================
    private void crearReserva() {

        long noches = ChronoUnit.DAYS.between(dpEntrada.getValue(), dpSalida.getValue());

        if (noches <= 0) {
            alerta("Fechas inválidas");
            return;
        }

        List<Habitacion> libres = habitacionDAO.buscarHabitacionesDisponibles(
                cbHotel.getValue().getIdHotel(),
                cbTipo.getValue(),
                Date.valueOf(dpEntrada.getValue()),
                Date.valueOf(dpSalida.getValue())
        );

        if (libres.isEmpty()) {
            alerta("Sin disponibilidad");
            return;
        }

        Habitacion hab = libres.get(0);
        double precio = noches * hab.getPrecioNoche();

        Reserva r = new Reserva(
                clienteSeleccionado.getIdCliente(),
                hab.getIdHabitacion(),
                Date.valueOf(LocalDate.now()),
                "Confirmada",
                "Pendiente"
        );

        int id = reservaDAO.crearReserva(
                r,
                hab.getIdHabitacion(),
                Date.valueOf(dpEntrada.getValue()),
                Date.valueOf(dpSalida.getValue()),
                1,
                precio
        );

        if (id != -1) {
            alerta("Reserva creada");
            cargarReservas();
        }
    }

    // ================= ELIMINAR =================
    private void eliminarReserva() {

        ReservaDetalle r = tabla.getSelectionModel().getSelectedItem();

        if (r == null) {
            alerta("Selecciona una reserva");
            return;
        }

        reservaDAO.eliminarReserva(r.getIdReserva());
        cargarReservas();
    }

    // ================= ALERT =================
    private void alerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.showAndWait();
    }
}