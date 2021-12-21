package src.recomanador.presentacio;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class VistaModificarAlgorisme extends JFrame {
    JPanel panel;
	
	JLabel text_inicial;
	
	JPanel dataPanel;
	
	JLabel alg_lab;
	JLabel k_param_lab;
	JLabel q_param_lab;
	
	JComboBox alg;
	JSlider k;
	JSlider q;
	
    JButton tornar_enrrere;
    
    /*----- DADES -----*/
    String[] algorismes =  {"Collaborative filtering", 
							"Content-based filtering",
							"Aproximació híbrida"};
    /*----- FUNCIONS -----*/
    public VistaModificarAlgorisme() {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();
        
		text_inicial = new JLabel("Paràmetres de l'algorisme");
		alg_lab = new JLabel("Algorisme seleccionat: ");
		k_param_lab = new JLabel("Paràmetre K:");
		q_param_lab = new JLabel("Paràmetre Q:");
		
		alg = new JComboBox(algorismes);
		
		k = new JSlider(JSlider.HORIZONTAL, 0, cp.getAllItems().size(), 5);
		k.setPaintTicks(true);
		k.setPaintLabels(true);
		k.setMajorTickSpacing(25);
		
		q = new JSlider(JSlider.HORIZONTAL, 0, cp.getAllItems().size(), 5);
		q.setPaintTicks(true);
		q.setPaintLabels(true);
		k.setMajorTickSpacing(25);
		
		tornar_enrrere = new JButton("Enrrere");
		
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(3, 2));     
		dataPanel.add(alg_lab);
		dataPanel.add(alg);
		dataPanel.add(k_param_lab);
		dataPanel.add(k);
		dataPanel.add(q_param_lab);
		dataPanel.add(q);
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(3, 1));
		
		panel.add(text_inicial);
		panel.add(dataPanel);
		panel.add(tornar_enrrere);
		
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Modificació de paràmetres de l'algorisme");
        pack();
        setVisible(true);
        
        
        /*
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
        });
         */
	}
}
