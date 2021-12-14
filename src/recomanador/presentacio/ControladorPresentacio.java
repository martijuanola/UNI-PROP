package src.recomanador.presentacio;

import java.util.ArrayList;

import src.recomanador.domini.ControladorDomini;
import src.recomanador.excepcions.AlreadyLogedInException;
import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.FileNotFoundException;
import src.recomanador.excepcions.FileNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;

public class ControladorPresentacio {
    private static ControladorDomini domini;
    private static ControladorPresentacio instancia = null;

    private ControladorPresentacio() {
        domini = ControladorDomini.getInstance();
    }

    public static ControladorPresentacio getInstance() {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
        }
        return instancia;
    }

    public static void obreVistaPrincipal() {
        new VistaError("OBERTA VISTA PRINCIPAL!");
    }

    public static void obreVistaInicial() {
        new VistaInicial();
    }

    public static void carregarProjecte(String proj) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {
        domini.loadSession(proj);
    }

    public static void logInUser(int id) throws AlreadyLogedInException {
        domini.login(id);
    }

    public static void logInAdmin() throws AlreadyLogedInException {
        domini.loginAdmin();
    }

    public static void carregarProjecteNou(String nom, String itemsFile, String ratingsFile) throws FolderNotValidException, FileNotValidException, FileNotFoundException {
        domini.loadNewSession(nom, itemsFile, ratingsFile);
    }

    public static ArrayList<String> getProjectesDisponibles() {
        return new ArrayList<String>();
    }

    public static void main(String[] args) {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        instancia = new ControladorPresentacio();
        obreVistaInicial();
    }
}
