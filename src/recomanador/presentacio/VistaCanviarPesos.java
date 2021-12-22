package src.recomanador.presentacio;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import src.recomanador.excepcions.ItemWeightNotCorrectException;
import src.recomanador.excepcions.PrivilegesException;


public class VistaCanviarPesos extends JFrame {
    ArrayList<String> pesosVells;
    VistaAllItems vi;
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
    public VistaCanviarPesos(ArrayList<String> pesos, ArrayList<String> nomAtributs, VistaAllItems inst) {
        pesosVells = pesos;
        vi = inst;
        na = nomAtributs;

        allItems = true;

        crearVistaPesos();
    }

    //Cridat des de TestAlgorisme
    public VistaCanviarPesos() {
        try {
            pesosVells = ControladorPresentacio.getPesos();
        }
        catch (PrivilegesException e) {
            ControladorPresentacio.obreVistaError("Només un administrador pot modificar els pesos.");
            return;
        }

        na = new ArrayList<String>();
        ArrayList<ArrayList<ArrayList<String>>> items = ControladorPresentacio.getAllItems();
        int nomPos = Integer.parseInt(ControladorPresentacio.getPosItemNom());
        for (int i = 0; i < items.size(); ++i) {
            na.add(items.get(i).get(nomPos).get(0));
        }

        allItems = false;

        crearVistaPesos();
    }

    void crearVistaPesos() {
        pesosNous = pesosVells;
        setLayout(new BorderLayout());

        JPanel botons = new JPanel();
        botons.setLayout(new FlowLayout());

        accept = new JButton("Aplicar canvis");

        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pesos.size(); ++i) {
                    pesosNous.set(i, ""+(pesos.get(i).getValue()/100.0));
                }
                retornarItancar();
            }
        });

        JLabel espai = new JLabel("");
        espai.setPreferredSize(new Dimension(60, 15));

        reset = new JButton("Reset pesos");

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pesos.size(); ++i) {
                    pesos.get(i).setValue((int) Float.parseFloat(pesosVells.get(i))*100);
                    textPesos.get(i).setText(((int)Float.parseFloat(pesosVells.get(i))*100)/100.0+"");
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
        setMinimumSize(new Dimension(getBounds().getSize().width, 200));
        setVisible(true);
    }

    void retornarItancar() {
        if (allItems) {
            vi.pesosFi(pesosNous);
        }
        else {
            try {
                ControladorPresentacio.setPesos(pesosNous);
            }
            catch (PrivilegesException | ItemWeightNotCorrectException e) {
                ControladorPresentacio.obreVistaError(e.getMessage());
                return;
            }
        }

        dispose();
    }
}
