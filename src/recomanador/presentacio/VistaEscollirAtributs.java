package src.recomanador.presentacio;

import java.util.ArrayList;

import java.awt.*;
import javax.swing.*;

public class VistaEscollirAtributs extends JFrame {
    JPanel panel;
    ArrayList<String> nomAtributs;
    ArrayList<String> tipusAtributs;
    ArrayList<String> diferentsTipus;

    ArrayList<JComboBox<String>> nousTipus;

    JButton reset;
    JButton accept;

    public VistaEscollirAtributs () {
        diferentsTipus = ControladorPresentacio.getTipus();
        nomAtributs = ControladorPresentacio.getHeaderItems();

        panel = new JPanel();
        panel.setLayout(new GridLayout(nomAtributs.size(), 2));

        nousTipus = new ArrayList<JComboBox<String>>(nomAtributs.size());

        reset = new JButton("Reset tipus");
        accept = new JButton("OK");

        for (int i = 0; i < nomAtributs.size(); ++i) {
            JComboBox<String> aux = new JComboBox<String>();
            for (int j = 0; j < diferentsTipus.size(); ++j) aux.addItem(diferentsTipus.get(j));
            aux.setSelectedItem(tipusAtributs.get(i));

            JPanel esq = new JPanel();
            esq.setLayout(new FlowLayout());
            JLabel nom = new JLabel(nomAtributs.get(i));
            esq.add(nom);
            esq.add(aux);
            nousTipus.add(aux);
            panel.add(esq);

            JPanel dre = new JPanel();
            dre.setLayout(new FlowLayout());
            if (i == 0) {
                JLabel ei = new JLabel("TEST");
                dre.add(ei);
            }
            else if (i == 1) {
                JLabel ei = new JLabel("TEST");
                dre.add(ei);
            }
            else {
                JLabel ei = new JLabel("TEST");
                dre.add(ei);
            }
            panel.add(dre);
            
        }

        JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Escull el tipus dels atributs");
        pack();
        setMinimumSize(getBounds().getSize());
        setVisible(true);
    }
}
