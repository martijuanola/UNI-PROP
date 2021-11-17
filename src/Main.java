package src;

import java.util.ArrayList;
import java.util.Arrays;

import src.recomanador.domini.*;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;

public class Main {
    public static void main(String[] args) {
        ControladorDomini cd = new ControladorDomini();
        try {
            cd.carregarCarpeta("data/movies.sample");
        } catch (FolderNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FolderNotValidException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cd.prova2();
    }
}