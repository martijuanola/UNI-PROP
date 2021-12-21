package src.recomanador.presentacio;

import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class VistaAllItems extends JFrame {
    boolean SessioNova;
    VistaSessioNova vs;
    ArrayList<String> na;
    ArrayList<String> ta;
    
    JPanel panel;

    JButton exit;

    VistaAllItems instancia;

    //vs és la vistaSessioNova que ha creat aquesta vista
    public VistaAllItems(ArrayList<String> nomAtributs, ArrayList<String> tipusAtributs, VistaSessioNova vsn) {
        vs = vsn;
        SessioNova = true;
        na = nomAtributs;
        ta = tipusAtributs;
        crearVistaItems();
    }

    //Exactament igual que l'anterior però amb vistaPrincipal
    public VistaAllItems() {
        SessioNova = false;
        na = ControladorPresentacio.getHeaderItems();
        ta = ControladorPresentacio.getTipusItems();;
        crearVistaItems();
    }

    private void crearVistaItems() {
        instancia = this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { //Quan es tanca la finestra es crida aquesta funció
                vs.ItemsAcabats();
                dispose();
            }
        });
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));

        exit = new JButton("Guardar i tornar");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispatchEvent(new WindowEvent(instancia, WindowEvent.WINDOW_CLOSING));
            }
        });
        panel.add(exit);
        

        JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Escull el tipus dels atributs");
        pack();
        setMinimumSize(new Dimension(getBounds().getSize().width, 200));
        setVisible(true);
    }
}
