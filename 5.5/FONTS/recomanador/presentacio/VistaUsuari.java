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

import src.recomanador.excepcions.*;

public class VistaUsuari extends JFrame {
    JPanel panel;
	
	JButton tornar_enrere;
	JLabel text_inicial;
	JLabel text_recs_vals;

	
    
    
    /*----- DADES -----*/
    String USER_ID;
    ArrayList<String> id_items;
    ArrayList<String> nom_items;
    ArrayList<Integer> option_idx;
    JComboBox<String> cb;
    String[] options = {"Sense valoració", "0.5", "1.0", 
								"1.5", "2.0", "2.5", "3.0",
								"3.5", "4.0", "4.5", "5.0"};
    
    /*----- FUNCIONS -----*/
    public VistaUsuari(VistaPrincipal vp, String usuari_id) {	
		USER_ID = usuari_id;
		
		inicialitza_vista();
		
		tornar_enrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vp.mostraDeUsuari();
				dispose();
			}
        });
	}
	
	public VistaUsuari(VistaTotalUsuari vtu, String usuari_id) {	
		USER_ID = usuari_id;
		
		inicialitza_vista();
		
		tornar_enrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vtu.mostra();
				dispose();
			}
        });
	}
	
	private int stringToIndex(String s)
	{
		if (s.equals("0.5")) return 1;
		else if (s.equals("1.0")) return 2;
		else if (s.equals("1.5")) return 3;
		else if (s.equals("2.0")) return 4;
		else if (s.equals("2.5")) return 5;
		else if (s.equals("3.0")) return 6;
		else if (s.equals("3.5")) return 7;
		else if (s.equals("4.0")) return 8;
		else if (s.equals("4.5")) return 9;
		else if (s.equals("5.0")) return 10;
		else return 0;
	}
	
	private void inicialitza_vista() {
		setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));
        
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
		VistaUsuari vu = this;
		
        panel = new JPanel();
        
		ArrayList<ArrayList<String>> all_recs =
			new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> all_vals =
			new ArrayList<ArrayList<String>>();
		
		id_items = new ArrayList<String>();
		nom_items = new ArrayList<String>();
		option_idx = new ArrayList<Integer>();
		
		try
		{
			all_recs = ControladorPresentacio.getRecomanacions(USER_ID);
			all_vals = ControladorPresentacio.getValoracions(USER_ID);
		}
		catch (Exception e)
		{
			new VistaError(e.getMessage());
		}
		
		
		try {
			for (int i = 0; i < all_vals.size(); ++i)
			{
				id_items.add(ControladorPresentacio.getItem(Integer.parseInt(all_vals.get(i).get(1))).get(Integer.parseInt(ControladorPresentacio.getPosItemId())).get(0));
				nom_items.add(ControladorPresentacio.getItem(Integer.parseInt(all_vals.get(i).get(1))).get(Integer.parseInt(ControladorPresentacio.getPosItemNom())).get(0));
				option_idx.add(stringToIndex(all_vals.get(i).get(2)));	
			}
			
			for (int i = 0; i < all_recs.size(); ++i)
			{
				id_items.add(ControladorPresentacio.getItem(Integer.parseInt(all_recs.get(i).get(1))).get(Integer.parseInt(ControladorPresentacio.getPosItemId())).get(0));
				nom_items.add(ControladorPresentacio.getItem(Integer.parseInt(all_recs.get(i).get(1))).get(Integer.parseInt(ControladorPresentacio.getPosItemNom())).get(0));
				option_idx.add(0);
			}
		} catch (Exception e) {
			new VistaError(e.getMessage());
		}
		
		int nb = id_items.size();
        
		tornar_enrere = new JButton("Tornar enrere");
		text_inicial = new JLabel("Informació Usuari amb id " + USER_ID);
		text_recs_vals = new JLabel("Items recomanats i valorats:");
		
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(4 + nb, 1));
        
		panel.add(tornar_enrere);
		panel.add(text_inicial);
		panel.add(text_recs_vals);
		
		JPanel header = new JPanel();
		header.setLayout(new GridLayout(1, 4));
		header.add(new JLabel("id"));
		header.add(new JLabel("nom"));
		header.add(new JLabel("valoració"));
		header.add(new JLabel(""));
		panel.add(header);
		
		for (int i = 0; i < nb; ++i)
		{
			JLabel idLab = new JLabel(id_items.get(i));
			JLabel nomLab = new JLabel(nom_items.get(i));
			JComboBox rate = new JComboBox(options);
			rate.setSelectedIndex(option_idx.get(i));
			
			JButton eliminar = new JButton("Eliminar");
			
			JPanel item_valorat = new JPanel();
			item_valorat.setLayout(new GridLayout(1, 4));
			
			item_valorat.add(idLab);
			item_valorat.add(nomLab);
			item_valorat.add(rate);
			item_valorat.add(eliminar);
			
			panel.add(item_valorat);
			
			String tempId = id_items.get(i);
			
			idLab.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						new VistaInformacioItem(vu, ControladorPresentacio.getItem(Integer.parseInt(tempId)));
						setVisible(false);
					}
					catch(ItemNotFoundException exce) {
						new VistaError(exce.getMessage());
					}
				}
			});
			
			idLab.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// only display a hand if the cursor is over the label
					final Rectangle cellBounds = idLab.getBounds();
					if (cellBounds != null) {
						idLab.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					idLab.getParent().repaint();
				}

				@Override
				public void mouseDragged(MouseEvent e) {							
				}
			});

			nomLab.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						new VistaInformacioItem(vu, ControladorPresentacio.getItem(Integer.parseInt(tempId)));
						setVisible(false);
					}
					catch(ItemNotFoundException exce) {
						new VistaError(exce.getMessage());
					}
				}
			});
			
			nomLab.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// only display a hand if the cursor is over the label
					final Rectangle cellBounds = nomLab.getBounds();
					if (cellBounds != null) {
						nomLab.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					nomLab.getParent().repaint();
				}

				@Override
				public void mouseDragged(MouseEvent e) {							
				}
			});

			rate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent  e) {
					String rate_value = rate.getSelectedItem().toString();
					if (rate_value.equals("Sense valoració"))
						ControladorPresentacio.eliminarValoracio(USER_ID, tempId);
					else ControladorPresentacio.valorar(tempId, USER_ID, rate_value);
				}
			});
			
			eliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ControladorPresentacio.eliminarRecomanacio(USER_ID, tempId);
					panel.remove(item_valorat);
					validate();
					repaint();
				}
			});
			
		}
		
		JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
		
        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informació Usuari");
        pack();
        setVisible(true);
				
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
