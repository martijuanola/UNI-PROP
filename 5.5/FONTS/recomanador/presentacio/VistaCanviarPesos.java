package src.recomanador.presentacio;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import src.recomanador.excepcions.PrivilegesException;


public class VistaCanviarPesos extends JFrame {
    ArrayList<String> pesosVells;
    VistaItems vi;
    ArrayList<String> pesosNous;
    ArrayList<String> na; //Nom atributs

    boolean allItems; //Indica si es crida des de allItems o des de test

    JPanel panel;
    ChangeListener listener;

    JButton accept;
    JButton reset;

    ArrayList<JSlider> pesos;
    ArrayList<JTextField> textPesos;

    //Cridat des de VistaAllItems
    public VistaCanviarPesos(ArrayList<String> pesos2, ArrayList<String> nomAtributs, VistaItems inst) {
        pesosVells = pesos2;
        vi = inst;
        na = nomAtributs;

        allItems = true;

        crearVistaPesos();
        
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pesos.size(); ++i) {
                    pesosNous.set(i, ""+(pesos.get(i).getValue()/100.0));
                }
                retornarItancar();
            }
        });
    }

    //Cridat des de TestAlgorisme
    public VistaCanviarPesos(VistaTestAlgorisme vta) {
        try {
            pesosVells = ControladorPresentacio.getPesos();
        }
        catch (PrivilegesException e) {
            ControladorPresentacio.obreVistaError("Només un administrador pot modificar els pesos.");
            return;
        }

        na = ControladorPresentacio.getHeaderItems();

        allItems = false;

        crearVistaPesos();
        
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pesos.size(); ++i) {
                    pesosNous.set(i, ""+(pesos.get(i).getValue()/100.0));
                }
                ControladorPresentacio.setPesos(pesosNous);
                vta.mostra();
                dispose();
            }
        });
    }

    void crearVistaPesos() {
        pesosNous = pesosVells;
        setLayout(new BorderLayout());

        JPanel botons = new JPanel();
        botons.setLayout(new FlowLayout());

        accept = new JButton("Aplicar canvis");

        

        JLabel espai = new JLabel("");
        espai.setPreferredSize(new Dimension(60, 15));

        reset = new JButton("Reset pesos");

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pesos.size(); ++i) {
                    pesos.get(i).setValue((int) (Float.parseFloat(pesosVells.get(i))*100));
                    textPesos.get(i).setText((Float.parseFloat(pesosVells.get(i))*100)/100.0+"");
                }
            }
        });

        botons.add(reset);
        botons.add(espai);
        botons.add(accept);
        add(botons, BorderLayout.PAGE_START);


        JPanel llista = new JPanel();
        llista.setLayout(new GridLayout(pesosNous.size(), 1));

        pesos = new ArrayList<JSlider>(pesosNous.size());
        textPesos = new ArrayList<JTextField>(pesosNous.size());

        for (int i = 0; i < pesosNous.size(); ++i) {
            JPanel pes = new JPanel();
            pes.setLayout(new GridLayout(1, 3));

            JLabel n = new JLabel(na.get(i));
            JPanel nomi = new JPanel();
            nomi.setLayout(new FlowLayout());
            nomi.add(n);
            pes.add(nomi);

            int p = (int) (Float.parseFloat(pesosNous.get(i))*100);

            JSlider slider = new JSlider(0, 0, 10000, p); //100 vegades més del que toca per fer "decimals" amb ints
            
            JTextField text = new JTextField(""+p/100.0); //Aqui és on es veu el numero

            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    text.setText(String.valueOf(slider.getValue()/100.0));
                }    
            });

            pes.add(slider);
            pesos.add(slider);

            text.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String typed = text.getText();

                    int value;
                    try {
                        value = (int)(Float.parseFloat(typed)*100);
                    }
                    catch (Exception e1) {
                        ControladorPresentacio.obreVistaError("Només es poden afegir valors entre 0 i 100");
                        return;
                    }
                    slider.setValue(value);
                }
            });

            pes.add(text);
            textPesos.add(text);

            llista.add(pes);
        }

        JScrollPane scrollFrame = new JScrollPane(llista);
        llista.setAutoscrolls(true);
        add(scrollFrame, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Modificar Pesos");
        pack();
        setMinimumSize(new Dimension(Math.min(getBounds().getSize().width, 700), 200));
        setSize(new Dimension(Math.min(getBounds().getSize().width, 1500), 700));
        setVisible(true);
    }

    void retornarItancar() {
        ControladorPresentacio.setPesos(pesosNous);
        vi.pesosFi(pesosNous);
        dispose();
    }
}
