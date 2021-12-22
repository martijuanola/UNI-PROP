package src.recomanador.presentacio;

import javax.swing.*;

public class VistaAdvertencia extends JFrame {
    JButton accept;

    static String[] options = {"Acceptar", "Cancel·lar"};

    public static boolean Advertencia(String advertencia) {
        return crearVistaAdvertencia(advertencia);

    }

    private static boolean crearVistaAdvertencia(String advertencia) {
        int x = JOptionPane.showOptionDialog(new JFrame(), advertencia, "Advertencia", JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        return x == 0;
    }
}
