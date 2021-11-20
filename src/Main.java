package src;

import src.recomanador.domini.*;
import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;

public class Main {
    public static void main(String[] args) {
        
        ControladorDomini cd = new ControladorDomini();
        try {
            cd.carregarCarpeta("movies-2250");
        } catch (FolderNotFoundException | FolderNotValidException | DataNotValidException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("x");
        cd.prova2();
    }
}
