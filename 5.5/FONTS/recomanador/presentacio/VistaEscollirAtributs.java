package src.recomanador.presentacio;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.excepcions.PrivilegesException;

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
        tipusAtributs = ControladorPresentacio.getTipusItems();
        for (int j = 0; j < diferentsTipus.size(); ++j) {
            if (diferentsTipus.get(j).equals("Identificador")) diferentsTipus.remove(j);
        }

        panel = new JPanel();
        panel.setLayout(new GridLayout(nomAtributs.size(), 3));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        nousTipus = new ArrayList<JComboBox<String>>(nomAtributs.size());

        reset = new JButton("Reset tipus");
        accept = new JButton("OK");

        for (int i = 0; i < nomAtributs.size(); ++i) {
            JComboBox<String> aux = new JComboBox<String>();
            if (nomAtributs.get(i).equals("id")){
                aux.addItem("Identificador");
            }
            else for (int j = 0; j < diferentsTipus.size(); ++j) {
                aux.addItem(diferentsTipus.get(j));
            }
            aux.setSelectedItem(tipusAtributs.get(i));
            nousTipus.add(aux);

            JPanel esq = new JPanel();
            esq.setLayout(new FlowLayout());
            JLabel nom = new JLabel(nomAtributs.get(i));
            esq.add(nom);
            panel.add(esq);

            JPanel mig = new JPanel();
            mig.setLayout(new FlowLayout());
            mig.add(aux);
            panel.add(mig);

            JPanel dre = new JPanel();
            dre.setLayout(new FlowLayout());
            if (i == 0) {
                dre.add(accept);
            }
            else if (i == 1) {
                dre.add(reset);
            }
            else {
                dre.add(new JLabel());
            }
            panel.add(dre);
            
        }

        JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Escull el tipus dels atributs");
        pack();
        setMinimumSize(new Dimension(getBounds().getSize().width, 200));
        setVisible(true);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < nousTipus.size(); ++i) {
                    nousTipus.get(i).setSelectedItem(tipusAtributs.get(i));
                }
            }
        });

        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> tipuses = new ArrayList<String>();
                for (int i = 0; i < nousTipus.size(); ++i) {
                    tipuses.add(nousTipus.get(i).getItemAt(nousTipus.get(i).getSelectedIndex()));
                }
                boolean nom = false;
                for (int i = 0; i < tipuses.size(); ++i) {
                    if (tipuses.get(i).equals("Nom")) {
                        if (nom) {
                            ControladorPresentacio.obreVistaError("Hi ha més d'una casella amb l'atribut nom");
                            return;
                        }
                        else nom = true;
                    }
                }
                if (!nom) {
                    ControladorPresentacio.obreVistaError("Selecciona una casella amb l'atribut nom");
                    return;
                }
                try {
                    ControladorPresentacio.setTipusItems(tipuses);
                    ControladorPresentacio.guardar();
                    ControladorPresentacio.obreVistaPrincipal();
                    dispose();
                }
                catch (PrivilegesException | ItemTypeNotValidException | DataNotValidException e1) {
                    ControladorPresentacio.obreVistaError(e1.getMessage());
                    return;
                }
            }
        });
    }
}
