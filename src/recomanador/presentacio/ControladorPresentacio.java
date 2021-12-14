package src.recomanador.presentacio;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import src.recomanador.domini.ControladorDomini;

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
        //new VistaPrincipal();
    }

    public static void obreVistaInicial() {
        new VistaInicial();
    }

    public static void main(String[] args) {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        instancia = new ControladorPresentacio();
        obreVistaInicial();
    }
}
