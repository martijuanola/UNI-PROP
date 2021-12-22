package src.recomanador.presentacio;

import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import src.recomanador.excepcions.ItemNotFoundException;
import src.recomanador.excepcions.PrivilegesException;

public class VistaItems extends JFrame {
    boolean SessioNova;
    boolean admin;

    VistaSessioNova vs;
    ArrayList<String> na; //Noms atributs
    ArrayList<String> ta; //Tipus atributs
    ArrayList<ArrayList<ArrayList<String>>> items; //Items només si vé de SessioNova

    int idPos;
    int nomPos;

    ArrayList<String> pesos;
    
    JPanel panel;
    JPanel sota;
    GridLayout sotaLayout;

    JButton exit;
    JButton afegir;
    JButton canviarPesos;

    ArrayList<JLabel> ids;
    ArrayList<JLabel> noms;

    VistaItems instancia;

    //vsn és la vistaSessioNova que ha creat aquesta vista
    public VistaItems(ArrayList<String> nomAtributs, ArrayList<String> tipusAtributs, VistaSessioNova vsn) {
        idPos = 0;
        nomPos = 1;
        admin = true;

        pesos = new ArrayList<String>();
        for (int i = 0; i < nomAtributs.size(); ++i) {
            pesos.add("100.0");
        }

        items = new ArrayList<ArrayList<ArrayList<String>>>();

        vs = vsn;
        SessioNova = true;
        na = nomAtributs;
        ta = tipusAtributs;
        crearVistaItems();
        
        exit.addActionListener(new ActionListener() { //Es tanca la finestra com si es fes click a la "X" i s'activa la funció de dalt
            public void actionPerformed(ActionEvent e)
            {
                ControladorPresentacio.guardar();
                vs.ItemsAcabats();
                dispose();
            }
        });
    }

    //Exactament igual que l'anterior però amb vistaPrincipal
    public VistaItems(VistaPrincipal vp) {
        items = ControladorPresentacio.getAllItems();
        idPos = Integer.parseInt(ControladorPresentacio.getPosItemId());
        nomPos = Integer.parseInt(ControladorPresentacio.getPosItemNom());

        admin = ControladorPresentacio.isAdmin();
        if (admin) {
            try {
                pesos = ControladorPresentacio.getPesos();
            } catch (PrivilegesException e) {
                System.out.println("Si no tens privilegis d'admin, és impossible arribar aqui");
            }
        }
        SessioNova = false;
        na = ControladorPresentacio.getHeaderItems();
        ta = ControladorPresentacio.getTipusItems();
        crearVistaItems();
        
        exit.addActionListener(new ActionListener() { //Es tanca la finestra com si es fes click a la "X" i s'activa la funció de dalt
            public void actionPerformed(ActionEvent e)
            {
                if (admin) ControladorPresentacio.guardar();
                vp.mostra();
                dispose();
            }
        });
    }

    private void crearVistaItems() {
        instancia = this;
        /*
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { //Quan es tanca la finestra es crida aquesta funció
                vs.ItemsAcabats();
                dispose();
            }
        });
        */

        JPanel botons = new JPanel();
        botons.setLayout(new FlowLayout());

        if (admin) {
            canviarPesos = new JButton("Modificar Pesos");
            canviarPesos.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    new VistaCanviarPesos(pesos, na, instancia);
                    setVisible(false);
                }
            });
            botons.add(canviarPesos);

            JLabel espai1 = new JLabel("");
            espai1.setPreferredSize(new Dimension(40, 1));
            botons.add(espai1);
            
            afegir = new JButton("Afegir item");
            afegir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    new VistaAfegirItem(ta, na, instancia);
                    setVisible(false);
                }
            });
            botons.add(afegir);

            JLabel espai2 = new JLabel("");
            espai2.setPreferredSize(new Dimension(40, 1));
            botons.add(espai2);
        }

        if (SessioNova) {
            exit = new JButton("Crear Projecte");
        }
        else if (admin) {
            exit = new JButton("Guardar i tornar");
        }
        else {
            exit = new JButton("Tornar");
        }
        
		botons.add(exit);

        sota = new JPanel();
        /*
        if (admin) {
            sotaLayout = new GridLayout(items.size()+1, 5); //tipus id (info modificar eliminar)
            sota.setLayout(sotaLayout);
        }
        else {
            sotaLayout = new GridLayout(items.size()+1, 3); //-2 per treure modificar i eliminar
            sota.setLayout(sotaLayout);
        }
        */
        sotaLayout = new GridLayout(items.size()+1, 3); //-2 per treure modificar i eliminar
        sota.setLayout(sotaLayout);
         
        JPanel f1 = new JPanel();
        f1.setLayout(new FlowLayout());
        f1.add(new JLabel("Identificador"));
        sota.add(f1);

        JPanel f2 = new JPanel();
        f2.setLayout(new FlowLayout());
        f2.add(new JLabel("Nom"));
        sota.add(f2);

        sota.add(new JLabel(""));
        /*
        if (admin) {
            sota.add(new JLabel(""));
            sota.add(new JLabel(""));
        }*/

        ids = new ArrayList<JLabel>();
        noms = new ArrayList<JLabel>();

        for (int i = 0; i < items.size(); ++i) {
            afegirI(i);
        }
        revalidate();

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(botons, BorderLayout.PAGE_START);
        panel.add(sota, BorderLayout.CENTER);

        JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tots els items");
        pack();
        setMinimumSize(new Dimension(Math.min(getBounds().getSize().width, 700), 200));
        setSize(new Dimension(Math.min(getBounds().getSize().width, 1500), 700));
        setVisible(true);
    }

    public void pesosFi (ArrayList<String> p) {
        pesos = p;
        setVisible(true);
    }

    public void infoFi() {
        setVisible(true);
    }

    public void afegirFi(ArrayList<ArrayList<String>> nouItem) {
        if (ControladorPresentacio.addItem(nouItem)) {
            items.add(nouItem);
            sotaLayout.setRows(sotaLayout.getRows() + 1);
            afegirI(items.size()-1);
            revalidate();
            int a = getBounds().getSize().height;
            pack();
            setMinimumSize(new Dimension(getBounds().getSize().width, a));
        }
        setVisible(true);
    }

    public void modificarFi(ArrayList<ArrayList<String>> nouItem, int pos) {
        items.set(pos, nouItem);
        ids.get(pos).setText(nouItem.get(idPos).get(0));
        noms.get(pos).setText(nouItem.get(nomPos).get(0));
        ControladorPresentacio.editarItem(nouItem);
        revalidate();
        pack();
        int a = getBounds().getSize().height;
        setMinimumSize(new Dimension(getBounds().getSize().width, a));
        setVisible(true);
    }

    private void afegirI(int i) {
        JPanel f3 = new JPanel();
        f3.setLayout(new FlowLayout());
        JLabel id1 = new JLabel(items.get(i).get(idPos).get(0));
        ids.add(id1);
        f3.add(id1);
        sota.add(f3);

        JPanel f4 = new JPanel();
        f4.setLayout(new FlowLayout());
        JLabel nom1 = new JLabel(items.get(i).get(nomPos).get(0));
        noms.add(nom1);
        f4.add(nom1);
        sota.add(f4);

        JButton info = new JButton("Informació");

        final int nonChanger = i;
        info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<ArrayList<String>> item;
                try {
                    item = ControladorPresentacio.getItem(Integer.parseInt(items.get(nonChanger).get(idPos).get(0)));
                }
                catch (NumberFormatException | ItemNotFoundException e1) {
                    ControladorPresentacio.obreVistaError(e1.getMessage());
                    return;
                }

                new VistaInformacioItem(item, ta, na, instancia);
                setVisible(false);
            }
        });

        JPanel f5 = new JPanel();
        f5.setLayout(new FlowLayout());
        f5.add(info);

        if (admin) {
            JButton mod = new JButton("Modificar");
            mod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new VistaModificarItem(ta, na, i, items.get(i), instancia);
                    setVisible(false);
                }
            });


            f5.add(mod);

            JButton elim = new JButton("Eliminar");

            elim.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (VistaAdvertencia.Advertencia("Segur que vols eliminar l'item?")) {
                        sota.remove(f3); //id
                        ids.remove(id1);

                        sota.remove(f4); //nom
                        noms.remove(nom1);

                        sota.remove(f5); //info mod elim

                        sotaLayout.setRows(sotaLayout.getRows() - 1);
                        ControladorPresentacio.eliminarItem(id1.getText());
                        revalidate();
                    }
                }
            });      

            //elim.setForeground(Color.WHITE);
            elim.setBackground(Color.RED);
            elim.setContentAreaFilled(false);
            elim.setOpaque(true);

            f5.add(elim);
        }
        sota.add(f5);
    }
}
