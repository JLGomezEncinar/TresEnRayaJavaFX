package iessanalberto.dam1.tresenrayajavafx;

import iessanalberto.dam1.tresenrayajavafx.navigation.Navigation;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
//Name: Tres en Raya
//Author: José Luis Gómez
//Date: 27/04/2025
/*Description: Programa que permite jugar al Tres en Raya, va alternando entre los
jugadores y realiza un seguimiento de los empates y partidas ganadas por cada jugador.*/
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Navigation.navigate("MainScreen");
    }

    public static void main(String[] args) {
        launch();
    }
}