package src.recomanador.presentacio;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VistaInformacioItem extends JFrame {

    VistaItems vi;
    ArrayList<ArrayList<String>> it;
    ArrayList<String> na; //nom atributs
    ArrayList<String> ta; //tipus atributs


    JButton sortir;

    boolean allI; //Indica si ve de all items (true) o de vistausuari (false)

    public VistaInformacioItem(ArrayList<ArrayList<String>> item, ArrayList<String> tipusAtributs, ArrayList<String> nomAtributs, VistaItems inst) {
        vi = inst;
        it = item;

        allI = true;

        na = nomAtributs;
        ta = tipusAtributs;

        crearVistaInfoItem();

        sortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                tornar();
            }
        });

    }

    //Vista informacio item cridada per VistaPrincipal
    public VistaInformacioItem(VistaPrincipal vp, ArrayList<ArrayList<String>> item) {
        it = item;
        //afegir vista usuari

        allI = false;

        na = ControladorPresentacio.getHeaderItems();

        ta = ControladorPresentacio.getTipusItems();

        crearVistaInfoItem();

        sortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                vp.mostra();
                dispose();
            }
        });
    }
    
    //Vista informacio item cridada per VistaUsuari
    public VistaInformacioItem(VistaUsuari vu, ArrayList<ArrayList<String>> item) {
        it = item;
        //afegir vista usuari

        allI = false;

        na = ControladorPresentacio.getHeaderItems();

        ta = ControladorPresentacio.getTipusItems();

        crearVistaInfoItem();

        sortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                vu.mostra();
                dispose();
            }
        });
    }

    private void crearVistaInfoItem() {
        setLayout(new BorderLayout());

        JPanel boto = new JPanel();
        boto.setLayout(new FlowLayout());
        boto.setPreferredSize(new Dimension(140, 150));
        sortir = new JButton("Tornar enrere");
        
        boto.add(sortir);

        JPanel nomTipus = new JPanel();
        nomTipus.setLayout(new GridLayout(na.size(), 2));

        for (int i = 0; i < na.size(); ++i) {
            JLabel tip = new JLabel(ta.get(i));
            tip.setForeground(Color.GRAY);
            
            JPanel jp = new JPanel();
            jp.setLayout(new FlowLayout());
            jp.add(tip);
            if (i%2 == 0) jp.setBackground(Color.LIGHT_GRAY);
            nomTipus.add(jp);

            JLabel no = new JLabel(na.get(i));
            no.setForeground(Color.BLACK);

            JPanel l = new JPanel();
            l.setLayout(new FlowLayout());
            l.add(no);
            if (i%2 == 0) l.setBackground(Color.LIGHT_GRAY);
            nomTipus.add(l);
        }

        JPanel tot = new JPanel();
        tot.setLayout(new BorderLayout());
        tot.add(nomTipus, BorderLayout.LINE_START);


        JPanel atributs = new JPanel();
        atributs.setLayout(new GridLayout(it.size(), 1));

        for (int i = 0; i < it.size(); ++i) {
            JPanel flow = new JPanel();
            flow.setLayout(new FlowLayout());

            for (int j = 0; j < it.get(i).size(); ++j) {
                flow.add(new JLabel(it.get(i).get(j)));
            }
            if (i%2 == 0) flow.setBackground(Color.LIGHT_GRAY);
            atributs.add(flow);
        }
        tot.add(atributs, BorderLayout.CENTER);

        add(boto, BorderLayout.LINE_END);

        JScrollPane scrollFrame = new JScrollPane(tot);
        tot.setAutoscrolls(true);
        add(scrollFrame, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informació de l'ítem");
        pack();
        setMinimumSize(new Dimension(Math.min(getBounds().getSize().width, 700), 200));
        setSize(new Dimension(Math.min(getBounds().getSize().width, 1500), 700));
        setVisible(true);
    }

    private void tornar() {
        if (allI) {
            vi.infoFi();
        }
        else {
            System.out.println("Sortir i tornar a VistaUsuari !!FALTA IMPLEMENTAR!!");
        }
        dispose();
    }
}
