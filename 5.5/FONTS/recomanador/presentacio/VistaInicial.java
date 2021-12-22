package src.recomanador.presentacio;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.recomanador.Utils.StringOperations;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class VistaInicial extends JFrame {
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

    JLabel fitxerit;
    JButton items;
    JComboBox<String> preprocessat;
    JPanel selectors;

    JLabel fitxerra;
    JButton ratings;
    JPanel PRatings;

    String itemsFile;
    String ratingsFile;

    JTextField nomProjecte;

    ArrayList<String> projectesDisponibles;


    public VistaInicial() {
        itemsFile = "";
        ratingsFile = "";

        panel = new JPanel();

        projectesDisponibles = ControladorPresentacio.getProjectesDisponibles();

        //LAYER 1
        user = new JRadioButton("User", true);
        admin = new JRadioButton("Administrador", false);
        mode = new ButtonGroup();
        mode.add(user);
        mode.add(admin);
        escollirUA = new JPanel();
        escollirUA.setLayout(new FlowLayout());
        escollirUA.add(admin);
        escollirUA.add(Box.createRigidArea(new Dimension(100, 0)));
        escollirUA.add(user);

        //LAYER 2
        id = new JTextField("ID usuari");
        id.setColumns(11);
        nomProjecte = new JTextField("Nom nou projecte");
        nomProjecte.setColumns(11);
        textId = new JPanel();
        textId.setLayout(new FlowLayout());
        JLabel a = new JLabel("");
        a.setPreferredSize(new Dimension(150, 30));
        textId.add(nomProjecte);
        textId.add(a);
        textId.add(id);

        //LAYER 3
        SessioNova = new JButton("Sessió nova");
        SessioNova.setMaximumSize(new Dimension(1000, 50));
        SessioNova.setEnabled(false);
        CarregaFitxers = new JButton("Carregar fitxers");
        CarregaDades = new JButton("Carregar dades");
        inicis = new JPanel();
        inicis.setLayout(new GridLayout(3, 5));
        inicis.add(new JLabel());
        inicis.add(SessioNova);
        inicis.add(CarregaFitxers);
        inicis.add(CarregaDades);
        inicis.add(new JLabel());

        inicis.add(new JLabel());
        inicis.add(new JLabel());
        items = new JButton("Obrir");
        items.setPreferredSize(new Dimension(85, 40));
        JPanel peinel = new JPanel(new FlowLayout());
        fitxerit = new JLabel("Fitxer items");
        peinel.add(fitxerit);
        peinel.add(items);
		preprocessat = new JComboBox<String>();
        for (int i = 0; i < projectesDisponibles.size(); ++i) {
            preprocessat.addItem(projectesDisponibles.get(i));
        }

        inicis.add(peinel);
        inicis.add(preprocessat);
        inicis.add(new JLabel());

        inicis.add(new JLabel());
        inicis.add(new JLabel());
        ratings = new JButton("Obrir");
        ratings.setPreferredSize(new Dimension(85, 40));
        JPanel panelrat = new JPanel(new FlowLayout());
        fitxerra = new JLabel("Fitxer valoracions");
        panelrat.add(fitxerra);
        panelrat.add(ratings);
        inicis.add(panelrat);
        inicis.add(new JLabel());
        inicis.add(new JLabel());

        //ADD TO FRAME
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(3, 1));
        panel.add(escollirUA);
        panel.add(textId);
        panel.add(inicis);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema Recomanador");
        pack();
        setMinimumSize(getBounds().getSize());
        setVisible(true);
    
        //EVENT LISTENERS
        id.addFocusListener(new FocusListener() {
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

        nomProjecte.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (nomProjecte.getText().equals("Nom nou projecte")) {
                    nomProjecte.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (nomProjecte.getText().equals("")) {
                    nomProjecte.setText("Nom nou projecte");
                }
            }
        });

        items.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 

                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setDialogTitle("Escull l'arxiu amb els items");
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(" .csv", "csv");
                fc.setFileFilter(filter);

                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    itemsFile = file.getAbsolutePath();
                    System.out.println("Opening: " + file.getName() + ".");
                    fitxerit.setText(file.getName());
                }
                else System.out.println("Open items file cancelled by user.");
            } 
        });

        ratings.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setDialogTitle("Escull l'arxiu amb les valoracions");
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(" .csv", "csv");
                fc.setFileFilter(filter);

                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    ratingsFile = file.getAbsolutePath();
                    System.out.println("Opening: " + file.getName() + ".");
                    fitxerra.setText(file.getName());
                }
                else System.out.println("Open ratings file cancelled by user.");
            } 
        });

        user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                SessioNova.setEnabled(false);
                id.setEnabled(true);
            }
        });

        admin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                SessioNova.setEnabled(true);
                id.setEnabled(false);
            }
        });

        CarregaDades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                if (user.isSelected()) {
                    if (!StringOperations.esNombre(id.getText()) || id.getText().equals("")) {
                        ControladorPresentacio.obreVistaError("Identificador d'usuari en format incorrecte.");
                        return;
                    }
                }

                try {
                    ControladorPresentacio.carregarProjecte(preprocessat.getSelectedItem().toString());
                }
                catch (Exception e2) {
                    ControladorPresentacio.obreVistaError(e2.getMessage());
                    return;
                }

                if (user.isSelected()) {
                    try {
                        ControladorPresentacio.logInUser(Integer.parseInt(id.getText()));
                    }
                    catch (Exception e2) {
                        ControladorPresentacio.obreVistaError(e2.getMessage());
                        return;
                    }
                }
                else {
                    try {
                        ControladorPresentacio.logInAdmin();
                    }
                    catch (Exception e2) {
                        ControladorPresentacio.obreVistaError(e2.getMessage());
                        return;
                    }
                }
                
                ControladorPresentacio.obreVistaPrincipal();
                dispose();
            }
        });
        
        CarregaFitxers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.isSelected()) {
                    if (!StringOperations.esNombre(id.getText()) || id.getText().equals("")) {
                        ControladorPresentacio.obreVistaError("Identificador d'usuari en format incorrecte.");
                        return;
                    }
                }

                if (nomProjecte.getText().equals("") || nomProjecte.getText().equals("Nom nou projecte")) {
                    ControladorPresentacio.obreVistaError("Nom del Projecte buit.");
                    return;
                }
                if (nomProjecte.getText().indexOf(' ') != -1) {
                    ControladorPresentacio.obreVistaError("El nom del projecte no pot contenir espais");
                    return;
                }
                for (int i = 0; i < projectesDisponibles.size(); ++i) {
                    if (nomProjecte.getText().equals(projectesDisponibles.get(i))) {
                        ControladorPresentacio.obreVistaError("Projecte ja existeix.");
                        return;
                    }
                }

                try {
                    System.out.println(nomProjecte.getText() + " " + itemsFile + " " + ratingsFile);
                    ControladorPresentacio.carregarProjecteNou(nomProjecte.getText(), itemsFile, ratingsFile);
                }
                catch (Exception e2) {
                    ControladorPresentacio.obreVistaError(e2.getMessage());
                    return;
                }

                if (user.isSelected()) {
                    try {
                        ControladorPresentacio.logInUser(Integer.parseInt(id.getText()));
                    }
                    catch (Exception e1) {
                        ControladorPresentacio.obreVistaError(e1.getMessage());
                        return;
                    }
                }
                else {
                    try {
                        ControladorPresentacio.logInAdmin();
                    }
                    catch (Exception e1) {
                        ControladorPresentacio.obreVistaError(e1.getMessage());
                        return;
                    }
                }
                
                new VistaEscollirAtributs();
                dispose();
            }
        });
    
        SessioNova.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (user.isSelected()) {
                    ControladorPresentacio.obreVistaError("Només un administrador pot crear una sessió nova");
                    return;
                }

                if (nomProjecte.getText().equals("") || nomProjecte.getText().equals("Nom nou projecte")) {
                    ControladorPresentacio.obreVistaError("Nom del Projecte buit.");
                    return;
                }
                if (nomProjecte.getText().indexOf(' ') != -1) {
                    ControladorPresentacio.obreVistaError("El nom del projecte no pot contenir espais");
                    return;
                }
                for (int i = 0; i < projectesDisponibles.size(); ++i) {
                    if (nomProjecte.getText().equals(projectesDisponibles.get(i))) {
                        ControladorPresentacio.obreVistaError("Projecte ja existeix.");
                        return;
                    }
                }  

                new VistaSessioNova(nomProjecte.getText());
                dispose();
            }

        });
    }
}
