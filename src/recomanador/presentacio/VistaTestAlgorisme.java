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

public class VistaTestAlgorisme extends JFrame {
    JPanel panel;
	
	//Esquerra
	JPanel esquerra;
		
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
		
		JButton pesos;
		JButton tornar_enrrere;
	//Dreta
	JPanel dreta;
		
		JButton testejar;
		
		JPanel resultsPanel;
		JLabel DCG;
		JLabel NDCG;
	
	
    
    
    /*----- DADES -----*/
    String[] algorismes =  {"Collaborative filtering", 
							"Content-based filtering",
							"Aproximació híbrida"};
	boolean spinnerChanged;
    boolean barChanged;
    
    int original_alg;
    int original_k;
    int original_q;
    
    /*----- FUNCIONS -----*/
    public VistaTestAlgorisme(VistaPrincipal vp) {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();
        
        original_alg = Integer.parseInt(cp.getAlgorisme());
        original_k = Integer.parseInt(cp.getK());
        original_q = Integer.parseInt(cp.getQ());
        
        //Part ESQUERRA
			text_inicial = new JLabel("NOTA: els canvis realitzars en aquest " +
					"apartat són temporals. Es revertiran en sortir.");
			alg_lab = new JLabel("Algorisme seleccionat: ");
			k_param_lab = new JLabel("Paràmetre K:");
			q_param_lab = new JLabel("Paràmetre Q:");
			
			alg = new JComboBox(algorismes);
			alg.setSelectedIndex(original_alg);
			
			int nb = Math.max(original_q, original_k);
			nb = Math.max(cp.getAllItems().size(), nb);
			nb = Math.max(1, nb);
			
			k = new JSlider(JSlider.HORIZONTAL, 1, nb, 5);
			k.setPaintTicks(true);
			k.setPaintLabels(true);
			k.setMajorTickSpacing(nb/5);
			k.setValue(original_k);
			
			ks = new JSpinner(new SpinnerNumberModel(original_k, 1, nb, 1));
			
			q = new JSlider(JSlider.HORIZONTAL, 1, nb, 5);
			q.setPaintTicks(true);
			q.setPaintLabels(true);
			q.setMajorTickSpacing(nb/5);
			q.setValue(original_q);
			
			qs = new JSpinner(new SpinnerNumberModel(original_q, 1, nb, 1));
			
			tornar_enrrere = new JButton("Enrrere");
			
			
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
			
			pesos = new JButton("Modificar Pesos");
			
			esquerra = new JPanel();
			esquerra.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
			esquerra.setLayout(new GridLayout(4, 1));
			
			esquerra.add(text_inicial);
			esquerra.add(dataPanel);
			esquerra.add(tornar_enrrere);
		
		//Part DRETA
			dreta = new JPanel();
			dreta.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
			dreta.setLayout(new GridLayout(2, 1));
			
			
		//Part GLOBAL
		panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(1, 2));
        
        panel.add(esquerra);
        panel.add(dreta);
        
        
        
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Avaluació de l'algorisme.");
        pack();
        setVisible(true);
        
        spinnerChanged = false;
		barChanged = false;
        
        ks.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!spinnerChanged)
				{
					spinnerChanged = true;
					
					cp.setK(ks.getValue().toString());
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
					
					cp.setK(Integer.toString(k.getValue()));
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
					
					cp.setQ(qs.getValue().toString());
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
					
					cp.setQ(Integer.toString(q.getValue()));
					qs.setValue(q.getValue());
					
					barChanged = false;
				}
			}
		});
        
        alg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent  e) {
				String algValue = Integer.toString(alg.getSelectedIndex());
				System.out.print(algValue);
				cp.setAlgorisme(algValue);
			}
		});
		
		tornar_enrrere.addActionListener(new ActionListener() {
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
