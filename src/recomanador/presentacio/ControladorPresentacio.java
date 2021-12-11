package src.recomanador.presentacio;

import javax.swing.*;
import java.awt.*;

import src.recomanador.domini.ControladorDomini;

public class ControladorPresentacio extends JFrame {
    private static ControladorDomini domini;
    private static ControladorPresentacio instancia = null;

    int count = 0;

    JPanel panel;
    JButton button;
    JLabel label;

    private ControladorPresentacio() {
        domini = new ControladorDomini();

        panel = new JPanel();
        button = new JButton("I am a button");
        label = new JLabel("Start by clicking the Button!");


        button.addActionListener(
            new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                count++;
                label.setText("Number of clicks: " + count);
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(2, 1));
        panel.add(button);
        panel.add(label);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema Recomanador");
        pack();
        setVisible(true);

    }

    public static ControladorPresentacio getInstance() {
        return instancia;
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        instancia = new ControladorPresentacio();
    }
}
