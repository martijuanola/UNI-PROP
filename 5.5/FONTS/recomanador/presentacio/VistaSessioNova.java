package src.recomanador.presentacio;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.recomanador.excepcions.AlreadyLogedInException;

public class VistaSessioNova extends JFrame {
    ArrayList<JTextField> nomAtributs;
    ArrayList<JComboBox<String>> tipusAtributs;
    ArrayList<JButton> eliminarAtributs;
    ArrayList<String> diferentsTipus;
    JPanel atributs;
    JPanel botons;

    JButton afegir;
    JButton accept;
    GridLayout myLayout;
    int numAtributsTot = 3;

    VistaSessioNova instancia;

    String nom;

    public VistaSessioNova (String nomProj) {
        nom = nomProj;
        instancia = this;
        diferentsTipus = new ArrayList<String>();
        ArrayList<String> auxiliar = ControladorPresentacio.getTipus();
        for (int i = 0; i < auxiliar.size(); ++i) {
            if (!auxiliar.get(i).equals("Identificador") && !auxiliar.get(i).equals("Nom")) {
                diferentsTipus.add(auxiliar.get(i));
            }
        }

        myLayout = new GridLayout(2, 3);
        atributs = new JPanel();
        atributs.setLayout(myLayout);

        botons = new JPanel();
        botons.setLayout(new FlowLayout());
        botons.setPreferredSize(new Dimension(150, 200));

        nomAtributs = new ArrayList<JTextField>();
        tipusAtributs = new ArrayList<JComboBox<String>>();
        eliminarAtributs = new ArrayList<JButton>();

        JTextField id = new JTextField("id");
        id.setEditable(false); 
        nomAtributs.add(id);
        JPanel flow1 = new JPanel();
        flow1.setLayout(new FlowLayout());
        flow1.add(id);
        atributs.add(flow1);

        JComboBox<String> auxid = new JComboBox<String>();
        auxid.addItem("Identificador");
        tipusAtributs.add(auxid);
        JPanel flow2 = new JPanel();
        flow2.setLayout(new FlowLayout());
        flow2.add(auxid);
        atributs.add(flow2);

        JButton elim1 = new JButton("Eliminar");
        elim1.setBackground(Color.RED);
        elim1.setContentAreaFilled(false);
        elim1.setOpaque(true);
        elim1.setVisible(false);
        eliminarAtributs.add(elim1);
        atributs.add(elim1);

        accept = new JButton("OK");
        botons.add(accept);
        JLabel espais = new JLabel("");
        espais.setPreferredSize(new Dimension(100, 15));
        botons.add(espais);

        JTextField nom = new JTextField("Nom");
        nom.getDocument().addDocumentListener(new DocumentListener() {
            
            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            public void removeUpdate(DocumentEvent e) {
              warn();
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }
            
            public void warn() {
                nom.getParent().revalidate();
            }
        });
        nomAtributs.add(nom);
        JPanel flow5 = new JPanel();
        flow5.setLayout(new FlowLayout());
        flow5.add(nom);
        atributs.add(flow5);

        JComboBox<String> auxnom = new JComboBox<String>();
        auxnom.addItem("Nom");
        tipusAtributs.add(auxnom);
        JPanel flow6 = new JPanel();
        flow6.setLayout(new FlowLayout());
        flow6.add(auxnom);
        atributs.add(flow6);

        JButton elim2 = new JButton("Eliminar");
        elim2.setBackground(Color.RED);
        elim2.setContentAreaFilled(false);
        elim2.setOpaque(true);
        elim2.setVisible(false);
        eliminarAtributs.add(elim2);
        JPanel flow7 = new JPanel();
        flow7.setLayout(new FlowLayout());
        flow7.add(elim2);
        atributs.add(flow7);

        afegir = new JButton("Afegir atribut");
        botons.add(afegir);
        
        //SET BORDER?
        JScrollPane scrollFrame = new JScrollPane(atributs);
        atributs.setAutoscrolls(true);
        add(scrollFrame, BorderLayout.CENTER);
        add(botons, BorderLayout.LINE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Afegeix els atributs i els tipus que tindran");
        pack();
        setMinimumSize(getBounds().getSize());
        setVisible(true);

        afegir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myLayout.setRows(myLayout.getRows() + 1);

                JTextField nom2 = new JTextField("atribut_" + numAtributsTot);
                numAtributsTot += 1;
                nom2.getDocument().addDocumentListener(new DocumentListener() {
            
                    public void changedUpdate(DocumentEvent e) {
                      warn();
                    }
                    public void removeUpdate(DocumentEvent e) {
                      warn();
                    }
                    public void insertUpdate(DocumentEvent e) {
                      warn();
                    }
                    
                    public void warn() {
                        nom2.getParent().revalidate();
                    }
                });
                nomAtributs.add(nom2);
                JPanel flow = new JPanel();
                flow.setLayout(new FlowLayout());
                flow.add(nom2);
                atributs.add(flow);
        
                JComboBox<String> auxcom = new JComboBox<String>();
                for (int i = 0; i < diferentsTipus.size(); ++i) {
                    auxcom.addItem(diferentsTipus.get(i));
                }
                tipusAtributs.add(auxcom);
                JPanel flow12 = new JPanel();
                flow12.setLayout(new FlowLayout());
                flow12.add(auxcom);
                atributs.add(flow12);
                JPanel flow13 = new JPanel();

                JButton elim19 = new JButton("Eliminar");
                //elim19.setForeground(Color.WHITE);
                elim19.setBackground(Color.RED);
                elim19.setContentAreaFilled(false);
                elim19.setOpaque(true);
                elim19.addActionListener(new ActionListener() { //Quina desgràcia de codi
                    public void actionPerformed(ActionEvent e) {
                        atributs.remove(flow);
                        nomAtributs.remove(nom2);

                        atributs.remove(flow12);
                        tipusAtributs.remove(auxcom);

                        atributs.remove(flow13);
                        eliminarAtributs.remove(elim19);

                        myLayout.setRows(myLayout.getRows() - 1);
                        atributs.revalidate();
                    }
                });

                eliminarAtributs.add(elim19);
                flow13.setLayout(new FlowLayout());
                flow13.add(elim19);
                atributs.add(flow13);

                atributs.revalidate();
            }
        });
    
        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> noms = new ArrayList<String>();
                for (int i = 0; i < nomAtributs.size(); ++i) {
                    String act = nomAtributs.get(i).getText();
                    if (act.replace(" ", "").equals("")) {
                        ControladorPresentacio.obreVistaError("Els atributs no poden estar buits. \nRevisa l'atribut " + (i+1));
                        return;
                    }
                    if (act.contains(",")) {
                        ControladorPresentacio.obreVistaError("Els atributs no poden tenir comes. \nRevisa l'atribut " + (i+1));
                        return;
                    }
                    noms.add(act);
                }

                ArrayList<String> tipuses = new ArrayList<String>();
                for (int i = 0; i < tipusAtributs.size(); ++i) {
                    tipuses.add(tipusAtributs.get(i).getItemAt(tipusAtributs.get(i).getSelectedIndex()));
                }

                try {
                    ControladorPresentacio.logInAdmin();
                }
                catch (AlreadyLogedInException e1) {
                    ControladorPresentacio.obreVistaError(e1.getMessage());
                    return;
                }
                if (ControladorPresentacio.crearProjecte(nomProj, tipuses, noms)) {
                    new VistaItems(noms, tipuses, instancia);
                    setVisible(false);
                }
            }
        });
    }    

    public void ItemsAcabats() {
        ControladorPresentacio.obreVistaPrincipal();
        dispose();
    }
}
