package iessanalberto.dam1.tresenrayajavafx.screens;

import iessanalberto.dam1.tresenrayajavafx.models.Jugador;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MainScreen {
    //Variables de la clase
    //Variables que guardan las partidas ganadas por cada jugador y los empates.
    private int ganadasJugador1 = 0;
    private int ganadasJugador2 = 0;
    private int empates = 0;
    //Variable que lleva los turnos jugados (hasta el turno 5 mínimo no puede haber un ganador)
    private int turno = 1;
//Creamos esta ArrayList que guarda los 9 primeros números primos
    private ArrayList<Integer> primos = new ArrayList<>(Arrays.asList(2,3,5,7,11,13,17,19,23));
    //Creamos un índice que nos servirá luego para asignar a cada botón un valor del ArrayList.
    private int indice = 0;
    //Creamos los dos jugadores con su símbolo y su turno.
    private Jugador jugador1 = new Jugador("X",true);
    private Jugador jugador2 = new Jugador("0",false);
    //Componentes del layout
    private VBox root = new VBox();
    private GridPane gridPane = new GridPane();
    private HBox fila1 = new HBox();
    private HBox fila2 = new HBox();
    private Label lblVictoria1 = new Label("Victorias Jugador 1: \n" + ganadasJugador1);
    private Label lblVictoria2 = new Label("Victorias Jugador 2: \n" + ganadasJugador2);
    private Label lblEmpates = new Label("Partidas empatadas: \n" + empates);
    private Label lblJugador1 = new Label("Jugador 1");
    private Label lblJugador2 = new Label("Jugador 2");
    private Button[][] btnceldas = new Button [3][3];




    public MainScreen() {
        configurarLayout();
        //Colocamos los botones mediante un bucle
        for (int fila = 0; fila <3; fila++) {
            for (int columna = 0; columna < 3; columna++){
                Button btncelda = new Button();
                btnceldas[fila][columna] = btncelda;
                //Les añadimos un tamaño fijo
                btncelda.setPrefWidth(60);
                btncelda.setPrefHeight(60);
                //Agregamos al botón uno de los valores del ArrayList e incrementamos el índice
                btncelda.setUserData(primos.get(indice));
                indice++;
                //Añadimos el botón al GridPane.
                gridPane.add(btncelda,columna,fila);
                btncelda.setOnAction(event -> {
                    //Comprobamos que la celda no tiene ya un símbolo colocado
                    if (btncelda.getText().isEmpty()){
                        //Dependiendo del turno, colocamos el símbolo correspondiente y modificamos el valor
                        if (jugador1.isTurno()){
                            btncelda.setText(jugador1.getSimbolo());
                            jugador1.setValor(jugador1.getValor() * Integer.parseInt(btncelda.getUserData().toString()));
                        } else {
                            btncelda.setText(jugador2.getSimbolo());
                            jugador2.setValor(jugador2.getValor() * Integer.parseInt(btncelda.getUserData().toString()));

                        }
                        if (turno >=5 && comprobarVictoria()) {
                            //Hacemos un return para evitar conflictos si la partida acaba en victoria con el tablero lleno.
                            return;
                        }
                        if(!tableroLleno()) {
                            //Mientras el tablero no esté lleno, alternamos el turno y sumamos un turno
                            jugador1.setTurno(!jugador1.isTurno());
                            jugador2.setTurno(!jugador2.isTurno());
                            if (jugador1.isTurno()){
                                lblJugador1.setStyle("-fx-background-color:green;");
                                lblJugador2.setStyle("");
                            } else {
                                lblJugador1.setStyle("");
                                lblJugador2.setStyle("-fx-background-color:green;");
                            }
                            turno++;
                        } else {
                            mostrarAlerta("Empate");

                        }
                    }

                });
            }
        }
        //Añadimos los componentes al layout
        fila1.getChildren().addAll(lblJugador1,lblJugador2);
        fila2.getChildren().addAll(lblVictoria1,lblEmpates,lblVictoria2);
        root.getChildren().addAll(fila1,gridPane,fila2);
    }
//Función que configura los elementos del layout.
    private void configurarLayout() {
        lblJugador1.setStyle("-fx-background-color:green;");
        fila1.setSpacing(50);
        fila1.setAlignment(Pos.CENTER);
        fila2.setSpacing(50);
        fila2.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);

    }
//Función que comprueba si todas las celdas están llenas
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
//Función que comprueba si un jugador ha ganado
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
//Alerta que sale cuando gana un jugador o hay empate, para dar opción de jugar más partidas o salir
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fin de partida");
        alert.setHeaderText(mensaje);
        alert.setContentText("¿Quieres volver a jugar?");
        Optional<ButtonType> resultado = alert.showAndWait();
        //Si aceptamos restaura el tablero
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            reiniciarTablero(mensaje);
        //Si no aceptamos cierra la aplicación
        } else {
            Platform.exit();
        }

    }
//Si devuelve true es que hay Tres en Raya
    private boolean haGanado(int valor) {
        return valor % 30 == 0 || valor % 1001 == 0 || valor % 7429 == 0 ||
                valor % 627 == 0 || valor % 238 == 0 || valor % 506 == 0 ||
                valor % 1495 == 0 || valor % 935 == 0;
    }
    //Función que vuelve el tablero a su estado inicial si deseamos jugar otra partida
public void reiniciarTablero(String mensaje){
    for (int fila = 0; fila <3; fila++) {
        for (int columna = 0; columna < 3; columna++) {
            btnceldas[fila][columna].setText("");
        }
    }
    if (!mensaje.startsWith("Empate")) {
        if (jugador1.isTurno()) {
            ganadasJugador1++;
            lblVictoria1.setText("Victorias Jugador 1: \n" + ganadasJugador1);
        } else {
            ganadasJugador2++;
            lblVictoria2.setText("Victorias Jugador 2: \n" + ganadasJugador2);
        }
    } else {
        empates++;
        lblEmpates.setText("Partidas empatadas: \n"+empates);
    }
    jugador1.setValor(1);
    jugador1.setTurno(true);
    lblJugador1.setStyle("-fx-background-color:green;");
    jugador2.setValor(1);
    jugador2.setTurno(false);
    lblJugador2.setStyle("");
}
//Getter del VBox
    public VBox getRoot() {
        return root;
    }
}
