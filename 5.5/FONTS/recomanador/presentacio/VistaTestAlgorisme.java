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
		JButton tornar_enrere;
	//Dreta
	JPanel dreta;
		
		JButton testejar;
		
		//JPanel resultsPanel;
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
    ArrayList<String> original_pesos;
    
    /*----- FUNCIONS -----*/
    public VistaTestAlgorisme(VistaPrincipal vp) {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        VistaTestAlgorisme vta = this;
        
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();
        
        original_alg = Integer.parseInt(ControladorPresentacio.getAlgorisme());
        original_k = Integer.parseInt(ControladorPresentacio.getK());
        try
		{
			original_pesos = ControladorPresentacio.getPesos();
		}
		catch (Exception e)
		{
			new VistaError(e.getMessage());
			original_pesos = null;
		}
        //Part ESQUERRA
			text_inicial = new JLabel("NOTA: els canvis realitzars en aquest " +
					"apartat són temporals. Es revertiran en sortir.");
			alg_lab = new JLabel("Algorisme seleccionat: ");
			k_param_lab = new JLabel("Paràmetre K:");
			
			alg = new JComboBox(algorismes);
			alg.setSelectedIndex(original_alg);
			
			int nb = Math.max(1, original_k);
			nb = Math.max(ControladorPresentacio.getAllItems().size(), nb);
			
			k = new JSlider(JSlider.HORIZONTAL, 1, nb, 5);
			k.setPaintTicks(true);
			k.setPaintLabels(true);
			k.setMajorTickSpacing(nb/5);
			k.setValue(original_k);
			
			ks = new JSpinner(new SpinnerNumberModel(original_k, 1, nb, 1));
						
			tornar_enrere = new JButton("Enrere");
			
			dataPanel = new JPanel();
			dataPanel.setLayout(new GridLayout(2, 3));     
			dataPanel.add(alg_lab);
			dataPanel.add(alg);
			dataPanel.add(new JLabel(""));
			dataPanel.add(k_param_lab);
			dataPanel.add(k);
			dataPanel.add(ks);
			
			pesos = new JButton("Modificar Pesos");
			
			esquerra = new JPanel();
			esquerra.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
			esquerra.setLayout(new GridLayout(4, 1));
			
			esquerra.add(text_inicial);
			esquerra.add(dataPanel);
			esquerra.add(pesos);
			esquerra.add(tornar_enrere);
								
		//Part DRETA
			dreta = new JPanel();
			dreta.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
			dreta.setLayout(new GridLayout(3, 1));
			
			testejar = new JButton("AVALUA");
			DCG = new JLabel("DCG = ?");
			NDCG = new JLabel("NDCG = ?");
			DCG.setFont(new Font(DCG.getFont().getName(), Font.PLAIN, 20));
			NDCG.setFont(new Font(NDCG.getFont().getName(), Font.PLAIN, 20));
			
			dreta.add(testejar);
			dreta.add(DCG);
			dreta.add(NDCG);
			
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
                
        alg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent  e) {
				String algValue = Integer.toString(alg.getSelectedIndex());
				System.out.print(algValue);
				ControladorPresentacio.setAlgorisme(algValue);
			}
		});
		
		tornar_enrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControladorPresentacio.setAlgorisme(Integer.toString(original_alg));
				ControladorPresentacio.setK(Integer.toString(original_k));
				try
				{
					ControladorPresentacio.setPesos(original_pesos);
				}
				catch (Exception error)
				{
					new VistaError(error.getMessage());
				}
				vp.mostra();
				dispose();				
				//new VistaError("Falta tornar enrrere");
			}
        });
		
		testejar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> res = ControladorPresentacio.executarTestAlgorisme();
				if (res != null)
				{
					DCG.setText("DCG = " + res.get(0));
					NDCG.setText("NDCG = " + res.get(1) + "%");
				}
				validate();
				repaint();
			}
        });
        
        pesos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new VistaCanviarPesos(vta);
			}
        });
					
        /*
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
        });
         */
	}
	
	public void mostra() {
		setVisible(true);
	}
}
