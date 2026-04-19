package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habitacion;

public class AppHotel extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Creamos la Tabla
        TableView<Habitacion> tabla = new TableView<>();

        // 2. Creamos las columnas (luego las conectaremos con el DAO)
        TableColumn<Habitacion, Integer> colId = new TableColumn<>("ID");
        TableColumn<Habitacion, String> colNum = new TableColumn<>("Número");
        TableColumn<Habitacion, String> colTipo = new TableColumn<>("Tipo");

        tabla.getColumns().addAll(colId, colNum, colTipo);

        // 3. Layout: Metemos la tabla en una caja vertical
        VBox root = new VBox(tabla);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Gestión de Hoteles - Chantal v1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}