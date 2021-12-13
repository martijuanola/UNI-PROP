package src.recomanador.presentacio;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import src.recomanador.domini.ControladorDomini;

public class ControladorPresentacio extends JFrame {
    private static ControladorDomini domini;
    private static ControladorPresentacio instancia = null;

    int count = 0;

    JPanel panel;

    JRadioButton admin;
    JRadioButton user;
    ButtonGroup mode;
    JPanel escollirUA;

    JTextField id;
    JPanel textId;

    JPanel space;

    JButton SessioNova;
    JButton CarregaFitxers;
    JButton CarregaDades;
    JPanel inicis;

    JButton items;
    JComboBox<String> preprocessat;
    JPanel selectors;

    JButton ratings;
    JPanel PRatings;

    private ControladorPresentacio() {
        domini = ControladorDomini.getInstance();
        setMinimumSize(new Dimension(200, 150));

        panel = new JPanel();

        //LAYER 1
        user = new JRadioButton("User", false);
        admin = new JRadioButton("Administrador", false);
        mode = new ButtonGroup();
        mode.add(user);
        mode.add(admin);
        mode.clearSelection();
        escollirUA = new JPanel();
        escollirUA.setLayout(new BoxLayout(escollirUA, BoxLayout.LINE_AXIS));
        escollirUA.add(admin);
        escollirUA.add(Box.createRigidArea(new Dimension(100, 0)));
        escollirUA.add(user);

        //LAYER 2
        id = new JTextField("ID usuari");
        textId = new JPanel();
        textId.setLayout(new BoxLayout(textId, BoxLayout.LINE_AXIS));
        textId.add(id);

        //LAYER 3
        space = new JPanel();
        space.setLayout(new GridLayout());

        //LAYER 4
        SessioNova = new JButton("Sessió nova");
        CarregaFitxers = new JButton("Carregar fitxers");
        CarregaDades = new JButton("Carregar dades");
        inicis = new JPanel();
        inicis.setLayout(new BoxLayout(inicis, BoxLayout.LINE_AXIS));
        inicis.add(SessioNova);
        inicis.add(CarregaFitxers);
        inicis.add(CarregaDades);

        //LAYER 5
        items = new JButton("Fitxer d'items");
		preprocessat = new JComboBox<String>();
        preprocessat.addItem("carpeta 1");
        preprocessat.addItem("carpeta 2");
        preprocessat.addItem("carpeta 3");
        preprocessat.addItem("carpeta de porno furro");
        selectors = new JPanel();
        selectors.setLayout(new BoxLayout(selectors, BoxLayout.LINE_AXIS));
        selectors.add(items);
        selectors.add(preprocessat);

        //LAYER 6
        ratings = new JButton("Fitxer de valoracions");
        PRatings = new JPanel();
        PRatings.setLayout(new BoxLayout(PRatings, BoxLayout.LINE_AXIS));
        PRatings.add(ratings);


        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(6, 1)); //Canviar a boxlayout (probablement) per donar més espai al 5 i menys al 3
        panel.add(escollirUA);
        panel.add(textId);
        panel.add(space);
        panel.add(inicis);
        panel.add(selectors);
        panel.add(PRatings);


        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema Recomanador");
        pack();
        setVisible(true);
    
        id.addFocusListener(
            new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (id.getText().equals("ID usuari")) {
                    id.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (id.getText().equals("")) {
                    id.setText("ID usuari");
                }
            }
        });

        items.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 

                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setDialogTitle("Escull l'arxiu amb els items");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(" .csv", "csv");
                fc.setFileFilter(filter);

                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println("Opening: " + file.getName() + ".");
                }
                else System.out.println("Open command cancelled by user.");
            } 
        });

        ratings.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 

                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setDialogTitle("Escull l'arxiu amb les valoracions");
                FileNameExtensionFilter filter = new FileNameExtensionFilter(" .csv", "csv");
                fc.setFileFilter(filter);

                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println("Opening: " + file.getName() + ".");
                }
                else System.out.println("Open command cancelled by user.");
            } 
        });
    }


    public static ControladorPresentacio getInstance() {
        return instancia;
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        instancia = new ControladorPresentacio();
    }
}
