package iessanalberto.dam1.tresenrayajavafx.screens;

import iessanalberto.dam1.tresenrayajavafx.models.Jugador;
import iessanalberto.dam1.tresenrayajavafx.navigation.Navigation;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MainScreen {
    private VBox root = new VBox();
    private GridPane gridPane = new GridPane();
    private Button[][] btnceldas = new Button [3][3];
    private Label lblTurno = new Label("Turno de X");
    private ArrayList<Integer> primos = new ArrayList<>(Arrays.asList(2,3,5,7,11,13,17,19,23));
    private int indice = 0;
    private Jugador jugador1 = new Jugador("X",true);
    private Jugador jugador2 = new Jugador("0",false);

    private int turno = 1;

    public MainScreen() {
        configurarLayout();
        for (int fila = 0; fila <3; fila++) {
            for (int columna = 0; columna < 3; columna++){
                Button btncelda = new Button();
                btnceldas[fila][columna] = btncelda;
                btncelda.setPrefWidth(60);
                btncelda.setPrefHeight(60);
                btncelda.setUserData(primos.get(indice));
                indice++;
                gridPane.add(btncelda,columna,fila);
                btncelda.setOnAction(event -> {
                    if (btncelda.getText().isEmpty()){
                        if (jugador1.isTurno()){
                            btncelda.setText(jugador1.getSimbolo());
                            jugador1.setValor(jugador1.getValor() * Integer.parseInt(btncelda.getUserData().toString()));
                        } else {
                            btncelda.setText(jugador2.getSimbolo());
                            jugador2.setValor(jugador2.getValor() * Integer.parseInt(btncelda.getUserData().toString()));

                        }
                        if (turno >=5 && comprobarVictoria()) {
                            return;
                        }
                        if(!tableroLleno()) {
                            jugador1.setTurno(!jugador1.isTurno());
                            jugador2.setTurno(!jugador2.isTurno());
                            if (jugador1.isTurno()){
                                lblTurno.setText("Turno de X");
                            } else {
                                lblTurno.setText("Turno de O");
                            }
                            turno++;
                        } else {
                            mostrarAlerta("Empate");
                        }
                    }

                });
            }
        }
        root.getChildren().addAll(lblTurno,gridPane);
    }

    private void configurarLayout() {
        root.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

    }

    private boolean tableroLleno() {
        boolean ocupado = true;
        for (int fila = 0; fila < 3; fila++){
            for (int columna = 0; columna < 3; columna++){
                if (btnceldas[fila][columna].getText().isEmpty()) {
                    ocupado = false;
                }
            }
        }
        return ocupado;
    }

    private boolean comprobarVictoria() {
boolean victoria = false;
            if (jugador1.isTurno() && haGanado(jugador1.getValor())) {
                mostrarAlerta("Gana el jugador1");
                victoria = true;
            } else if (jugador2.isTurno() && haGanado(jugador2.getValor())) {
                mostrarAlerta("Gana el jugador2");
                victoria = true;
            }
return victoria;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fin de partida");
        alert.setHeaderText(mensaje);
        alert.setContentText("¿Quieres volver a jugar?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Navigation.navigate("MainScreen");

        } else {
            Platform.exit();
        }

    }

    private boolean haGanado(int valor) {
        return valor % 30 == 0 || valor % 1001 == 0 || valor % 7429 == 0 ||
                valor % 627 == 0 || valor % 238 == 0 || valor % 506 == 0 ||
                valor % 1495 == 0 || valor % 935 == 0;
    }


    public VBox getRoot() {
        return root;
    }
}
