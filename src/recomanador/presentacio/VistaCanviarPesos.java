package src.recomanador.presentacio;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class VistaCanviarPesos extends JFrame {
    ArrayList<String> pesosVells;
    VistaAllItems vi;
    ArrayList<String> pesosNous;

    JPanel panel;
    ChangeListener listener;

    JButton accept;
    JButton reset;

    public VistaCanviarPesos(ArrayList<String> pesos, ArrayList<String> nomAtributs, VistaAllItems inst) {
        pesosVells = pesos;
        vi = inst;
        pesosNous = pesosVells;

        setTitle("Modificar Pesos");

    }

    void retornarItancar() {
        vi.pesosFi(pesosNous);
        dispose();
    }
}
