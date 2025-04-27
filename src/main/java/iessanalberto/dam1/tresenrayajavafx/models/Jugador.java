package iessanalberto.dam1.tresenrayajavafx.models;

public class Jugador {
    //Variables de clase
    private String simbolo;
    private boolean turno;
    private int valor;
    //Constructor

    public Jugador(String simbolo, boolean turno) {
        this.simbolo = simbolo;
        this.turno = turno;
        this.valor = 1;
    }
//Getters y Setters
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }
}
