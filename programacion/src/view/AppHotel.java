package view;

import dao.HabitacionDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habitacion;

import java.util.List;

public class AppHotel extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Contenedor principal de pestañas
        TabPane tabPane = new TabPane();

        // --- PESTAÑA 1: CLIENTES ---
        Tab tabClientes = new Tab("Gestión de Clientes");
        tabClientes.setClosable(false); // Evita que se cierre la pestaña

        // Usamos la clase que creaste ayer
        FormularioCliente formCliente = new FormularioCliente();
        tabClientes.setContent(formCliente);

        // --- PESTAÑA 2: HABITACIONES ---
        Tab tabHabitaciones = new Tab("Disponibilidad");
        tabHabitaciones.setClosable(false);

        // Llamamos a un método que nos devuelva la tabla montada
        VBox layoutHabitaciones = crearPanelHabitaciones();
        tabHabitaciones.setContent(layoutHabitaciones);

        // --- PESTAÑA 3: RESERVAS  ---
        Tab tabReservas = new Tab("Nueva Reserva");
        tabReservas.setClosable(false);

        PanelReservas panelReservas = new PanelReservas();
        tabReservas.setContent(new PanelReservas());

        // 2. Añadimos todas las pestañas al TabPane
        tabPane.getTabs().addAll(tabClientes, tabHabitaciones, tabReservas);

        // 3. Configuración de la Escena
        Scene scene = new Scene(tabPane, 900, 750);
        primaryStage.setTitle("Sistema de Gestión Hotelera PROMETEO - Chantal v1.1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para mantener el código limpio: Crea la tabla y la rellena
    private VBox crearPanelHabitaciones() {
        TableView<Habitacion> tabla = new TableView<>();

        // Ajuste Pro: Las columnas se reparten el ancho automáticamente
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Habitacion, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));

        TableColumn<Habitacion, Integer> colNum = new TableColumn<>("Nº Hab");
        colNum.setCellValueFactory(new PropertyValueFactory<>("numHabitacion"));

        TableColumn<Habitacion, String> colTipo = new TableColumn<>("Categoría");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoHabitacion"));

        TableColumn<Habitacion, Double> colPrecio = new TableColumn<>("Precio/Noche");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioNoche"));

        TableColumn<Habitacion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tabla.getColumns().addAll(colId, colNum, colTipo, colPrecio, colEstado);

        // Cargar datos
        HabitacionDAO habitacionDAO = new HabitacionDAO();
        List<Habitacion> lista = habitacionDAO.listarDisponibles();
        ObservableList<Habitacion> datos = FXCollections.observableArrayList(lista);
        tabla.setItems(datos);

        // Layout de esta pestaña
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(new Label("HABITACIONES DISPONIBLES EN TIEMPO REAL"), tabla);

        return layout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}