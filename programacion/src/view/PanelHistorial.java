package view;

import dao.ReservaDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.ReservaDetalle;
import java.util.List;

public class PanelHistorial extends VBox {

    private TableView<ReservaDetalle> tabla;
    private Label lblTotalIngresos;

    public PanelHistorial() {
        this.setPadding(new Insets(20));
        this.setSpacing(15);

        Label titulo = new Label("HISTORIAL GENERAL DE RESERVAS");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        tabla = new TableView<>();
        configurarTabla();

        lblTotalIngresos = new Label("Total Ingresos: 0.00 €");
        lblTotalIngresos.setStyle("-fx-font-size: 16px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;");

        Button btnRefrescar = new Button("🔄 Actualizar Historial");
        btnRefrescar.setOnAction(e -> cargarDatos());

        this.getChildren().addAll(titulo, btnRefrescar, tabla, lblTotalIngresos);

        cargarDatos(); // Cargar al abrir
    }

    private void configurarTabla() {
        TableColumn<ReservaDetalle, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idReserva"));

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

        tabla.getColumns().addAll(colId, colCli, colHot, colEnt, colSal, colPre);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void cargarDatos() {
        ReservaDAO dao = new ReservaDAO();
        List<ReservaDetalle> lista = dao.obtenerHistorialCompleto();
        tabla.setItems(FXCollections.observableArrayList(lista));

        // Calcular total para tu análisis de cadena de hoteles
        double total = lista.stream().mapToDouble(ReservaDetalle::getPrecioTotal).sum();
        lblTotalIngresos.setText(String.format("Total Ingresos Acumulados: %.2f €", total));
    }
}