package src.recomanador.presentacio;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class VistaAfegirItem extends JFrame {

    VistaAllItems vi;
    ArrayList<String> na; //nom atributs
    ArrayList<String> ta; //tipus atributs

    ArrayList<JTextField> atributsNous;

    JButton acceptar;
    JButton cancelar;

    public VistaAfegirItem(ArrayList<String> tipusAtributs, ArrayList<String> nomAtributs, VistaAllItems inst) {
        vi = inst;

        na = nomAtributs;
        ta = tipusAtributs;

        crearVistaInfoItem();
    }

    private void crearVistaInfoItem() {
        setLayout(new BorderLayout());

        JPanel boto = new JPanel();
        boto.setLayout(new FlowLayout());
        boto.setPreferredSize(new Dimension(140, 150));

        acceptar = new JButton("Afegir item");
        //acceptar.setForeground(Color.WHITE);
        acceptar.setBackground(Color.GREEN);
        acceptar.setContentAreaFilled(false);
        acceptar.setOpaque(true);
        acceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                afegir();
            }
        });
        boto.add(acceptar);

        cancelar = new JButton("Cancelar");
        cancelar.setForeground(Color.WHITE);
        cancelar.setBackground(Color.RED);
        cancelar.setContentAreaFilled(false);
        cancelar.setOpaque(true);
        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                tornar();
            }
        });
        boto.add(cancelar);

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
        atributs.setLayout(new GridLayout(na.size(), 1));
        atributsNous = new ArrayList<JTextField>();

        for (int i = 0; i < na.size(); ++i) {
            JPanel flow = new JPanel();
            flow.setLayout(new FlowLayout());

            JTextField text = new JTextField("afegir atribut");

            text.getDocument().addDocumentListener(new DocumentListener() {
            
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
                    text.getParent().revalidate();
                }
            });

            flow.add(text);
            atributsNous.add(text);

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
        vi.infoFi();
        dispose();
    }

    private void afegir() {
        ArrayList<String> nouItem = new ArrayList<String>();
        for (int i = 0; i < atributsNous.size(); ++i) {
            nouItem.add(atributsNous.get(i).getText());
        }
        vi.afegirFi(nouItem);
        dispose();
    }
}
