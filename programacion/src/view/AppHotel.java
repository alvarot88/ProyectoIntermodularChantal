package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class AppHotel extends Application {

    @Override
    public void start(Stage primaryStage) {

        TabPane tabPane = new TabPane();

        // PESTAÑAS
        Tab tabClientes = crearTab("Gestión de Clientes", new PanelClientes());
        Tab tabReservas = crearTab("Gestión de Reservas", new PanelReservas());
        Tab tabHistorial = crearTab("Historial y Finanzas", new PanelHistorial());
        tabPane.getTabs().addAll(
                tabClientes,
                tabReservas,
                tabHistorial
        );


        // ESCENA PRINCIPAL
        Scene scene = new Scene(tabPane, 900, 750);

        primaryStage.setTitle("Sistema de Gestión Hotelera PROMETEO - Chantal v1.1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // MÉTODO REUTILIZABLE
    private Tab crearTab(String titulo, javafx.scene.layout.Region contenido) {
        Tab tab = new Tab(titulo);
        tab.setContent(contenido);
        tab.setClosable(false);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}