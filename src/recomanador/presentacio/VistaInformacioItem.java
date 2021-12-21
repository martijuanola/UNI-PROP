package src.recomanador.presentacio;
import java.util.ArrayList;

import java.awt.*;
import javax.swing.*;

public class VistaInformacioItem extends JFrame {

    VistaAllItems vi;
    ArrayList<ArrayList<String>> it;

    boolean allI; //Indica si ve de all items (true) o de vistausuari (false)

    public VistaInformacioItem(ArrayList<ArrayList<String>> item, VistaAllItems inst) {
        vi = inst;
        it = item;
        allI = true;
        crearVistaInfoItem();
    }

    //Vista informacio item cridada per VistaUsuari
    public VistaInformacioItem(ArrayList<ArrayList<String>> item) {
        it = item;
        allI = false;
        crearVistaInfoItem();
    }

    private void crearVistaInfoItem() {
        setLayout(new BorderLayout());

        JPanel boto = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Escull el tipus dels atributs");
        pack();
        setMinimumSize(new Dimension(getBounds().getSize().width, 200));
        setVisible(true);
    }
}
