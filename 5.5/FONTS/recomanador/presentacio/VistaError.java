package src.recomanador.presentacio;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VistaError extends JOptionPane {
    public VistaError(String error) {
        showMessageDialog(new JFrame(), error, "Error", ERROR_MESSAGE);
    }
}
