package all.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/newUIlast.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/all/view/Stylesheet2.css").toExternalForm());
        primaryStage.setTitle("Sistema de aproximación de gastos");
        Controlador ctr = loader.getController();
        ctr.setStage(primaryStage);
        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            try {
                Controlador.alCerrar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

//    public void changeTitle(String ruta, Stage primaryStage){
//        primaryStage.setTitle("SBAG  " + "<" + ruta + ">");
//    }
    public static void main(String[] args) {
        launch(args);
    }
}
