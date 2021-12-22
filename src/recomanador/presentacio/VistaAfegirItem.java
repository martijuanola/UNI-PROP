package src.recomanador.presentacio;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.recomanador.Utils.StringOperations;
import src.recomanador.excepcions.ItemTypeNotValidException;

public class VistaAfegirItem extends JFrame {

    VistaItems vi;
    ArrayList<String> na; //nom atributs
    ArrayList<String> ta; //tipus atributs

    ArrayList<JTextField> atributsNous;

    JButton acceptar;
    JButton cancelar;
    JButton help;

    public VistaAfegirItem(ArrayList<String> tipusAtributs, ArrayList<String> nomAtributs, VistaItems inst) {
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

        cancelar = new JButton("Cancel·lar");
        //cancelar.setForeground(Color.WHITE);
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

        help = new JButton("Ajuda");

        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(new JFrame(), "Els formats acceptats són: \n" +
                                                            "- Identificador: nombre enter.\n" +
                                                            "- Nom: qualsevol.\n" +
                                                            "- Boolean: true / false.\n" + 
                                                            "- String: qualsevol.\n" +
                                                            "- Float: nombre enter, nombre real i amb exponent: 1.23e3.\n" +
                                                            "- Data: DD-MM-AAAA.\n");
            }
        });
        boto.add(help);

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
        setTitle("Afegir ítem");
        pack();
        setMinimumSize(new Dimension(Math.min(getBounds().getSize().width, 700), 200));
        setSize(new Dimension(Math.min(getBounds().getSize().width, 1500), 700));
        setVisible(true);
    }

    private void tornar() {
        vi.infoFi();
        dispose();
    }

    private void afegir() {
        ArrayList<ArrayList<String>> nouItem = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < atributsNous.size(); ++i) {
            try {
                ArrayList<String> aux = StringOperations.divideString(atributsNous.get(i).getText(), ';');

                for (int j = 0; j < aux.size(); ++j) {
                    if (!StringOperations.tipusCorrecte(aux.get(j), StringOperations.stringToType(ta.get(i)))) {
                        ControladorPresentacio.obreVistaError("L'atribut " + na.get(i) + " no accepta el format " + aux.get(j));
                        return;
                    }
                }
                nouItem.add(aux);
            }
            catch (ItemTypeNotValidException e) {
                ControladorPresentacio.obreVistaError(e.getMessage());
                return;
            }
        }
        vi.afegirFi(nouItem);
        dispose();
    }
}
