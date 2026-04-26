package view;

import dao.HotelDAO;
import dao.ReservaDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.scene.layout.*;
import model.Hotel;
import model.ReservaDetalle;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PanelHistorial extends VBox {

    private TableView<ReservaDetalle> tabla;
    private Label lblTotalIngresos;

    private ComboBox<Hotel> cbHotel;
    private ComboBox<String> cbAnio;

    private List<ReservaDetalle> cache;

    public PanelHistorial() {

        setPadding(new Insets(20));
        setSpacing(15);

        Label titulo = new Label("HISTORIAL GENERAL DE RESERVAS");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // FILTROS
        cbHotel = new ComboBox<>();
        cbHotel.setPromptText("Hotel");

        cbAnio = new ComboBox<>();
        cbAnio.setPromptText("Año");
        cbAnio.getItems().addAll("2023", "2024", "2025", "2026");

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

        Button btnFiltrar = new Button("Filtrar");
        Button btnReset = new Button("Reset");
        Button btnExportar = new Button("Exportar XML");

        btnFiltrar.setOnAction(e -> aplicarFiltros());
        btnReset.setOnAction(e -> {
            cbHotel.setValue(null);
            cbAnio.setValue(null);
            cargarDatos();
        });

        btnExportar.setOnAction(e -> exportarXML());

        HBox filtros = new HBox(10,
                new Label("Hotel:"), cbHotel,
                new Label("Año:"), cbAnio,
                btnFiltrar,
                btnReset,
                btnExportar
        );

        filtros.setAlignment(Pos.CENTER_LEFT);

        // TABLA
        tabla = new TableView<>();
        configurarTabla();

        lblTotalIngresos = new Label();
        lblTotalIngresos.setStyle("-fx-font-size: 16px; -fx-text-fill: #2e7d32; -fx-font-weight: bold;");

        getChildren().addAll(titulo, filtros, tabla, lblTotalIngresos);

        cargarDatos();
    }

    // ================= EXPORTAR XML (MEJORADO PRO) =================
    private void exportarXML() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar XML");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );

        File file = fileChooser.showSaveDialog(this.getScene().getWindow());

        if (file == null) return;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // ROOT
            Element root = doc.createElement("exportacion_reservas");
            root.setAttribute("sistema", "GESTION_HOTELES");
            root.setAttribute("version", "1.0");
            doc.appendChild(root);

            // METADATOS
            Element metadatos = doc.createElement("metadatos");

            Element fecha = doc.createElement("fecha_exportacion");
            fecha.appendChild(doc.createTextNode(LocalDate.now().toString()));

            Element totalRegs = doc.createElement("total_reservas");
            totalRegs.appendChild(doc.createTextNode(String.valueOf(tabla.getItems().size())));

            metadatos.appendChild(fecha);
            metadatos.appendChild(totalRegs);
            root.appendChild(metadatos);

            // RESERVAS
            Element reservas = doc.createElement("reservas");
            root.appendChild(reservas);

            List<ReservaDetalle> lista = tabla.getItems();

            int id = 1;

            for (ReservaDetalle r : lista) {

                Element reserva = doc.createElement("reserva");
                reserva.setAttribute("id", String.valueOf(id++));

                // CLIENTE
                Element cliente = doc.createElement("cliente");
                cliente.appendChild(doc.createTextNode(r.getNombreCliente()));

                // HOTEL
                Element hotel = doc.createElement("hotel");
                hotel.appendChild(doc.createTextNode(r.getNombreHotel()));

                // FECHAS
                Element fechas = doc.createElement("fechas");

                Element entrada = doc.createElement("entrada");
                entrada.appendChild(doc.createTextNode(r.getFechaEntrada().toString()));

                Element salida = doc.createElement("salida");
                salida.appendChild(doc.createTextNode(r.getFechaSalida().toString()));

                fechas.appendChild(entrada);
                fechas.appendChild(salida);

                // PRECIO
                Element precio = doc.createElement("precio");
                precio.setAttribute("moneda", "EUR");

                Element total = doc.createElement("total");
                total.appendChild(doc.createTextNode(String.valueOf(r.getPrecioTotal())));

                precio.appendChild(total);

                // ENSAMBLAR
                reserva.appendChild(cliente);
                reserva.appendChild(hotel);
                reserva.appendChild(fechas);
                reserva.appendChild(precio);

                reservas.appendChild(reserva);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(file));

            transformer.transform(source, result);

            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText(null);
            ok.setContentText("XML exportado correctamente");
            ok.showAndWait();

        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText(null);
            error.setContentText("Error exportando XML: " + e.getMessage());
            error.showAndWait();
        }
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

    // ================= CARGA =================

    private void cargarDatos() {

        ReservaDAO dao = new ReservaDAO();
        cache = dao.listarReservas();

        aplicarFiltros();
    }

    // ================= FILTROS =================

    private void aplicarFiltros() {

        if (cache == null) return;

        List<ReservaDetalle> lista = cache;

        Hotel hotel = cbHotel.getValue();
        if (hotel != null) {
            lista = lista.stream()
                    .filter(r -> r.getNombreHotel().equalsIgnoreCase(hotel.getNombre()))
                    .toList();
        }

        String anio = cbAnio.getValue();
        if (anio != null) {
            lista = lista.stream()
                    .filter(r -> {
                        LocalDate fecha = r.getFechaEntrada().toLocalDate();
                        return String.valueOf(fecha.getYear()).equals(anio);
                    })
                    .toList();
        }

        tabla.setItems(FXCollections.observableArrayList(lista));

        double total = lista.stream()
                .mapToDouble(ReservaDetalle::getPrecioTotal)
                .sum();

        lblTotalIngresos.setText(String.format("Total Ingresos: %.2f €", total));
    }
}