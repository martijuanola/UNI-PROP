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

    ArrayList<String> ids;
    ArrayList<String> noms;
    
    JPanel panel;

    JButton exit;
    JButton afegir;
    JButton canviarPesos;

    VistaAllItems instancia;

    //vsn és la vistaSessioNova que ha creat aquesta vista
    public VistaAllItems(ArrayList<String> nomAtributs, ArrayList<String> tipusAtributs, VistaSessioNova vsn) {
        ids = new ArrayList<String>();
        noms = new ArrayList<String>();

        //TODO: esborrar aquesta cosa de prova
        for (int i = 0; i < 50; ++i) {
            ids.add(""+i);
            noms.add("nom"+i);
        }

        vs = vsn;
        SessioNova = true;
        na = nomAtributs;
        ta = tipusAtributs;
        crearVistaItems();
    }

    //Exactament igual que l'anterior però amb vistaPrincipal
    public VistaAllItems() {
        ArrayList<ArrayList<ArrayList<String>>> items = ControladorPresentacio.getAllItems();
        int idPos = Integer.parseInt(ControladorPresentacio.getPosItemId());
        int nomPos = Integer.parseInt(ControladorPresentacio.getPosItemNom());
        for (int i = 0; i < items.size(); ++i) {
            ids.add(items.get(i).get(idPos).get(0));
            noms.add(items.get(i).get(nomPos).get(0));
        }
        SessioNova = false;
        na = ControladorPresentacio.getHeaderItems();
        ta = ControladorPresentacio.getTipusItems();
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

        JPanel botons = new JPanel();
        botons.setLayout(new FlowLayout());

        canviarPesos = new JButton("Modificar Pesos");
        canviarPesos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("obre modificar pesos");
            }
        });
        botons.add(canviarPesos);
        
        afegir = new JButton("Afegir item");
        afegir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("obre afegir items");
            }
        });
        botons.add(afegir);

        exit = new JButton("Guardar i tornar");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispatchEvent(new WindowEvent(instancia, WindowEvent.WINDOW_CLOSING));
            }
        });
        botons.add(exit);


        JPanel sota = new JPanel();
        sota.setLayout(new GridLayout(ids.size()+1, 5)); //+1 per posar id i nom
        
        JPanel f1 = new JPanel();
        f1.setLayout(new FlowLayout());
        f1.add(new JLabel("Nom"));
        sota.add(f1);

        JPanel f2 = new JPanel();
        f2.setLayout(new FlowLayout());
        f2.add(new JLabel("Identificador"));
        sota.add(f2);

        sota.add(new JLabel(""));
        sota.add(new JLabel(""));
        sota.add(new JLabel(""));

        for (int i = 0; i < ids.size(); ++i) {
            JPanel f3 = new JPanel();
            f3.setLayout(new FlowLayout());
            f3.add(new JLabel(noms.get(i)));
            sota.add(f3);
    
            JPanel f4 = new JPanel();
            f4.setLayout(new FlowLayout());
            f4.add(new JLabel(ids.get(i)));
            sota.add(f4);

            JButton info = new JButton("Informació");
            JButton mod = new JButton("Modificar");
            JButton elim = new JButton("Eliminar");

            JPanel f5 = new JPanel();
            f5.setLayout(new FlowLayout());
            f5.add(info);
            sota.add(f5);

            JPanel f6 = new JPanel();
            f6.setLayout(new FlowLayout());
            f6.add(mod);
            sota.add(f6);

            JPanel f7 = new JPanel();
            f7.setLayout(new FlowLayout());
            f7.add(elim);
            sota.add(f7);
        }

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(botons, BorderLayout.PAGE_START);
        panel.add(sota, BorderLayout.CENTER);

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
