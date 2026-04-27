package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class AppHotel extends Application {

    private static final String APP_TITLE = "Sistema de Gestión Hotelera - Chantal v1.1";

    @Override
    public void start(Stage primaryStage) {

        TabPane tabPane = new TabPane();

        tabPane.getTabs().addAll(
                crearTab("Gestión de Clientes", new PanelClientes()),
                crearTab("Gestión de Reservas", new PanelReservas()),
                crearTab("Historial y Finanzas", new PanelHistorial())
        );

        Scene scene = new Scene(tabPane, 900, 750);

        primaryStage.setTitle(APP_TITLE);
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