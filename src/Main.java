package src;

import java.util.ArrayList;
import java.util.Arrays;

import src.recomanador.domini.*;

public class Main {
    public static void main(String[] args) {
        ControladorDomini cd = new ControladorDomini();
        cd.carregarCarpeta("data/Movies-2250");
        cd.prova2();
    }
}