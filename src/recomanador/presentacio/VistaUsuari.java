package src.recomanador.presentacio;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import javax.swing.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.lang.Math;

public class VistaUsuari extends JFrame {
    JPanel panel;
	
	JLabel text_inicial;
	
	JPanel dataPanel;
	
	
    JButton tornar_enrere;
    
    /*----- DADES -----*/
    
    
    /*----- FUNCIONS -----*/
    public VistaUsuari(VistaPrincipal vp) {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();
        
		text_inicial = new JLabel("Paràmetres de l'algorisme");
		
		
		panel.add(text_inicial);
		panel.add(tornar_enrere);
		
		JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
		
        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informació Usuari " + "id");
        pack();
        setVisible(true);
		
		tornar_enrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vp.mostra();
				dispose();				
				//new VistaError("Falta tornar enrrere");
			}
        });
				
        /*
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
        });
         */
	}
}
