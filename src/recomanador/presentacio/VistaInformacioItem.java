package src.recomanador.presentacio;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VistaInformacioItem extends JFrame {

    VistaAllItems vi;
    ArrayList<ArrayList<String>> it;
    ArrayList<String> na; //nom atributs
    ArrayList<String> ta; //tipus atributs


    JButton sortir;

    boolean allI; //Indica si ve de all items (true) o de vistausuari (false)

    public VistaInformacioItem(ArrayList<ArrayList<String>> item, ArrayList<String> tipusAtributs, ArrayList<String> nomAtributs, VistaAllItems inst) {
        vi = inst;
        it = item;

        allI = true;

        na = nomAtributs;
        ta = tipusAtributs;

        crearVistaInfoItem();
    }

    //Vista informacio item cridada per VistaUsuari
    public VistaInformacioItem(ArrayList<ArrayList<String>> item) {
        it = item;
        //afegir vista usuari

        allI = false;

        na = new ArrayList<String>();

        ArrayList<ArrayList<ArrayList<String>>> items = ControladorPresentacio.getAllItems();
        int nomPos = Integer.parseInt(ControladorPresentacio.getPosItemNom());
        for (int i = 0; i < items.size(); ++i) {
            na.add(items.get(i).get(nomPos).get(0));
        }

        ta = ControladorPresentacio.getTipusItems();

        crearVistaInfoItem();
    }

    private void crearVistaInfoItem() {
        setLayout(new BorderLayout());

        JPanel boto = new JPanel();
        boto.setLayout(new FlowLayout());
        boto.setPreferredSize(new Dimension(140, 150));
        sortir = new JButton("Tornar enrere");
        sortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                tornar();
            }
        });
        boto.add(sortir);

        JPanel nomTipus = new JPanel();
        nomTipus.setLayout(new GridLayout(na.size(), 2));

        for (int i = 0; i < na.size(); ++i) {
            JLabel tip = new JLabel(na.get(i));
            tip.setForeground(Color.GRAY);
            
            JPanel jp = new JPanel();
            jp.setLayout(new FlowLayout());
            jp.add(tip);
            if (i%2 == 0) jp.setBackground(Color.LIGHT_GRAY);
            nomTipus.add(jp);

            JLabel no = new JLabel(ta.get(i));
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
        setMinimumSize(new Dimension(getBounds().getSize().width, 200));
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
