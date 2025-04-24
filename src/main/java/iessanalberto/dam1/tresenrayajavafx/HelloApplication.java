package iessanalberto.dam1.tresenrayajavafx;

import iessanalberto.dam1.tresenrayajavafx.navigation.Navigation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Navigation.navigate("MainScreen");
    }

    public static void main(String[] args) {
        launch();
    }
}