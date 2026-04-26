package view;

import dao.HotelDAO;
import dao.ReservaDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Hotel;
import model.ReservaDetalle;

import java.util.List;

public class PanelHistorial extends VBox {

    private TableView<ReservaDetalle> tabla;
    private Label lblTotalIngresos;

    private ComboBox<Hotel> cbHotel;
    private ComboBox<String> cbAnio;

    public PanelHistorial() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);

        // ================= TÍTULO =================
        Label titulo = new Label("HISTORIAL GENERAL DE RESERVAS");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ================= FILTROS =================
        cbHotel = new ComboBox<>();
        cbHotel.setPromptText("Hotel");

        cbAnio = new ComboBox<>();
        cbAnio.setPromptText("Año");
        cbAnio.getItems().addAll("2023", "2024", "2025", "2026");

        // Cargar hoteles
        HotelDAO hotelDAO = new HotelDAO();
        cbHotel.setItems(FXCollections.observableArrayList(hotelDAO.obtenerTodosLosHoteles()));

        cbHotel.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Hotel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });

        cbHotel.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Hotel item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre());
            }
        });

        // ================= BOTONES =================
        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Button btnReset = new Button("Reset");
        btnReset.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");

        btnFiltrar.setOnAction(e -> cargarDatos());
        btnReset.setOnAction(e -> {
            cbHotel.setValue(null);
            cbAnio.setValue(null);
            cargarDatos();
        });

        HBox filtros = new HBox(10);
        filtros.setAlignment(Pos.CENTER_LEFT);

        filtros.getChildren().addAll(
                new Label("Hotel:"), cbHotel,
                new Label("Año:"), cbAnio,
                btnFiltrar,
                btnReset
        );

        // ================= TABLA =================
        tabla = new TableView<>();
        configurarTabla();

        lblTotalIngresos = new Label("Total Ingresos: 0.00 €");
        lblTotalIngresos.setStyle("-fx-font-size: 16px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;");

        this.getChildren().addAll(titulo, filtros, tabla, lblTotalIngresos);

        cargarDatos();
    }

    // ================= TABLA =================
    private void configurarTabla() {

        TableColumn<ReservaDetalle, String> colCli = new TableColumn<>("Cliente");
        colCli.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<ReservaDetalle, String> colHot = new TableColumn<>("Hotel");
        colHot.setCellValueFactory(new PropertyValueFactory<>("nombreHotel"));

        TableColumn<ReservaDetalle, String> colEnt = new TableColumn<>("Entrada");
        colEnt.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));

        TableColumn<ReservaDetalle, String> colSal = new TableColumn<>("Salida");
        colSal.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));

        TableColumn<ReservaDetalle, Double> colPre = new TableColumn<>("Precio Total");
        colPre.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));

        tabla.getColumns().addAll(colCli, colHot, colEnt, colSal, colPre);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // ================= CARGA DATOS =================
    private void cargarDatos() {

        ReservaDAO dao = new ReservaDAO();
        List<ReservaDetalle> lista = dao.listarReservas();

        // FILTRO HOTEL
        Hotel hotelSel = cbHotel.getValue();
        if (hotelSel != null) {
            lista = lista.stream()
                    .filter(r -> r.getNombreHotel().equalsIgnoreCase(hotelSel.getNombre()))
                    .toList();
        }

        // FILTRO AÑO
        String anioSel = cbAnio.getValue();
        if (anioSel != null && !anioSel.isEmpty()) {
            lista = lista.stream()
                    .filter(r -> r.getFechaEntrada().toString().startsWith(anioSel))
                    .toList();
        }

        tabla.setItems(FXCollections.observableArrayList(lista));

        double total = lista.stream()
                .mapToDouble(ReservaDetalle::getPrecioTotal)
                .sum();

        lblTotalIngresos.setText(String.format("Total Ingresos Acumulados: %.2f €", total));
    }
}