package src.recomanador.presentacio;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;

public class VistaInformacioItem extends JFrame {
    public VistaInformacioItem(ArrayList<ArrayList<String>> item, VistaAllItems inst) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Escull el tipus dels atributs");
        pack();
        setMinimumSize(new Dimension(getBounds().getSize().width, 200));
        setVisible(true);
        inst.infoFi();
    }
}
