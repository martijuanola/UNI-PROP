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
	
	JSpinner ks;
	JSpinner qs;
	
    JButton tornar_enrere;
    
    /*----- DADES -----*/
    String[] algorismes =  {"Collaborative filtering", 
							"Content-based filtering",
							"Aproximació híbrida"};
	boolean spinnerChanged;
    boolean barChanged;
    
    /*----- FUNCIONS -----*/
    public VistaModificarAlgorisme(VistaPrincipal vp) {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();
        
		text_inicial = new JLabel("Paràmetres de l'algorisme");
		alg_lab = new JLabel("Algorisme seleccionat: ");
		k_param_lab = new JLabel("Paràmetre K:");
		q_param_lab = new JLabel("Nombre de recomanacions retornades:");
		
		alg = new JComboBox(algorismes);
		alg.setSelectedIndex(Integer.parseInt(ControladorPresentacio.getAlgorisme()));
		
		int nb = Math.max(Integer.parseInt(ControladorPresentacio.getQ()), Integer.parseInt(ControladorPresentacio.getK()));
		nb = Math.max(ControladorPresentacio.getAllItems().size(), nb);
		nb = Math.max(1, nb);
		
		k = new JSlider(JSlider.HORIZONTAL, 1, nb, 5);
		k.setPaintTicks(true);
		k.setPaintLabels(true);
		k.setMajorTickSpacing(nb/5);
		k.setValue(Integer.parseInt(ControladorPresentacio.getK()));
		
		ks = new JSpinner(new SpinnerNumberModel(Integer.parseInt(ControladorPresentacio.getK()), 1, nb, 1));
		
		q = new JSlider(JSlider.HORIZONTAL, 1, nb, 5);
		q.setPaintTicks(true);
		q.setPaintLabels(true);
		q.setMajorTickSpacing(nb/5);
		q.setValue(Integer.parseInt(ControladorPresentacio.getQ()));
		
		qs = new JSpinner(new SpinnerNumberModel(Integer.parseInt(ControladorPresentacio.getQ()), 1, nb, 1));
		
		tornar_enrere = new JButton("Enrere");
		
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(3, 3));     
		dataPanel.add(alg_lab);
		dataPanel.add(alg);
		dataPanel.add(new JLabel(""));
		dataPanel.add(k_param_lab);
		dataPanel.add(k);
		dataPanel.add(ks);
		dataPanel.add(q_param_lab);
		dataPanel.add(q);
		dataPanel.add(qs);
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(3, 1));
		
		panel.add(text_inicial);
		panel.add(dataPanel);
		panel.add(tornar_enrere);
		
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Modificació de paràmetres de l'algorisme");
        pack();
        setVisible(true);
        
        spinnerChanged = false;
		barChanged = false;
        
        ks.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!spinnerChanged)
				{
					spinnerChanged = true;
					
					ControladorPresentacio.setK(ks.getValue().toString());
					k.setValue(Integer.parseInt(ks.getValue().toString()));
					
					spinnerChanged = false;
				}
			}
		});
		
		k.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!barChanged)
				{
					barChanged = true;
					
					ControladorPresentacio.setK(Integer.toString(k.getValue()));
					ks.setValue(k.getValue());
					
					barChanged = false;
				}
			}
		});
        
        qs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!spinnerChanged)
				{
					spinnerChanged = true;
					
					ControladorPresentacio.setQ(qs.getValue().toString());
					q.setValue(Integer.parseInt(qs.getValue().toString()));
					
					spinnerChanged = false;
				}
			}
		});
		
		q.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!barChanged)
				{
					barChanged = true;
					
					ControladorPresentacio.setQ(Integer.toString(q.getValue()));
					qs.setValue(q.getValue());
					
					barChanged = false;
				}
			}
		});
        
        alg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent  e) {
				String algValue = Integer.toString(alg.getSelectedIndex());
				System.out.print(algValue);
				ControladorPresentacio.setAlgorisme(algValue);
			}
		});
		
		tornar_enrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vp.mostra();
				dispose();
			}
        });
	}
}
