package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import src.recomanador.domini.*;
import src.recomanador.domini.Utils.Search;
import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;

public class Main {
    public static void main(String[] args) {
        
        ControladorDomini cd = new ControladorDomini();
        try {
            cd.carregarCarpeta("movies-2250");
        } catch (FolderNotFoundException | FolderNotValidException | DataNotValidException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cd.prova2();
    }
}
