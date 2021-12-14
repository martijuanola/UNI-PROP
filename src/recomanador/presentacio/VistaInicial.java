package src.recomanador.presentacio;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    Path itemsFile;
    Path ratingsFile;
    
    public VistaInicial() {
        //domini = new ControladorDomini();
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();

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
        id.setColumns(10);
        textId = new JPanel();
        textId.setLayout(new FlowLayout());
        JLabel a = new JLabel("");
        a.setPreferredSize(new Dimension(290, 30));
        a.setMinimumSize(new Dimension(290, 0));
        textId.add(a);
        textId.add(id);

        //LAYER 4
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
        preprocessat.addItem("carpeta 1");
        preprocessat.addItem("carpeta 2");
        preprocessat.addItem("carpeta 3");
        preprocessat.addItem("carpeta de porno furro");
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

        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(3, 1));
        panel.add(escollirUA);
        panel.add(textId);
        panel.add(inicis);

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
                fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(" .csv", "csv");
                fc.setFileFilter(filter);

                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    itemsFile = Paths.get(file.getAbsolutePath());
                    Path base = Paths.get(System.getProperty("user.dir"));
                    Path relative = base.relativize(Paths.get(file.getAbsolutePath()));
                    System.out.println("Opening: " + relative + ".");
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
                    ratingsFile = Paths.get(file.getAbsolutePath());
                    Path base = Paths.get(System.getProperty("user.dir"));
                    Path relative = base.relativize(Paths.get(file.getAbsolutePath()));
                    System.out.println("Opening: " + relative + ".");
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
    }

}
