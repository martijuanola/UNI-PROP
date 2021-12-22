package src.recomanador.presentacio;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import src.recomanador.excepcions.*;

public class VistaPrincipal extends JFrame {
    JPanel panel;
	
	//Botons part esquerra
		JPanel menu_esquerra;
		//Botons part esquerra superior
			JPanel me_up;
			JLabel nom_projecte;	//Both
			JLabel usuari_sessio_actual;	//Both
			JButton info_usuari;		//Only user
			JButton info_total_usuaris;	//Only admin    
			
			//Compartit
			JButton info_items;
			
			//Admin
			JButton modificar_algorisme;
			JButton test_algorisme;
			
		//Botons part esquerra inferior
			JPanel me_down;
			
			//Compartit
			JButton guardar;
			JButton recuperar;	//Afegir pregunta de si esta segur
				/* Pop up finestra per assegurar-se
				 * Posa a 0 les recomanacions de la pantalla
				 * (es a dir, les guardades).
				 * Surt del projecte sense guardar
				 * Torna a carregar el projecte
				 */
			JButton logout;		
			
    
    //Separador al mig-esquerra
		//JSeparator divisorMenu;
	
	//Part de recomanacions en directe
		JPanel dreta;
		JButton recomana;
		JScrollPane recsScrollable;
		JPanel recs;			//Amb scrollbar
			ArrayList<JLabel> header;	//Per id, nom, valoracio
			ArrayList<JLabel> id_item;
			ArrayList<JLabel> nom_item;
			ArrayList<JComboBox> rate;	//Tindra no valorat i [0.5, 5] de 1/2 en 1/2
    
    /*----- DADES -----*/
	VistaPrincipal vp;
	ArrayList<ArrayList<String>> recomanacions;
	
    JComboBox<String> cb;
    String[] options = {"Sense valoració", "0.5", "1.0", 
								"1.5", "2.0", "2.5", "3.0",
								"3.5", "4.0", "4.5", "5.0"};
    
    /*----- FUNCIONS -----*/
    public VistaPrincipal() {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        vp = this;
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));
        
		recomanacions = new ArrayList<ArrayList<String>>();
        
        panel = new JPanel();
        
        //LAYER E
        menu_esquerra = new JPanel();
        
			//LAYER E UP
			me_up = new JPanel();
			
			String lab_usr = null;
			if (ControladorPresentacio.isAdmin()) lab_usr = "Admin";
			else lab_usr = ControladorPresentacio.getId();
			
			nom_projecte = new JLabel("Projecte sobre el que es treballa: " + ControladorPresentacio.getNomProjecte());
			usuari_sessio_actual = new JLabel("Usuari de la sessió actual: " + lab_usr);
			info_usuari = new JButton("Les meves recomanacions");
			info_total_usuaris = new JButton("Informació dels usuaris");
			info_items = new JButton("Informació dels ítems");
			modificar_algorisme = new JButton("Modificar algorisme");
			test_algorisme = new JButton("Avaluar algorisme");
			
			//if (ControladorPresentacio.isAdmin()) menu_esquerra.setLayout(new GridLayout(6, 1));//, 0, 10));
			//else menu_esquerra.setLayout(new GridLayout(4, 1));//, 0, 10));
			if (ControladorPresentacio.isAdmin()) menu_esquerra.setLayout(new GridLayout(9, 1, 0, 10));
			else menu_esquerra.setLayout(new GridLayout(7, 1, 0, 10));
			
			menu_esquerra.add(nom_projecte);
			menu_esquerra.add(usuari_sessio_actual);
			if (ControladorPresentacio.isAdmin()) menu_esquerra.add(info_total_usuaris);
			else menu_esquerra.add(info_usuari);
			menu_esquerra.add(info_items);
			
			//SEPARACIO
			//menu_esquerra.add(new Box.createVerticalStrut(30));
			
			//LAYER E DOWN
			//me_down = new JPanel();
			
			guardar = new JButton("Guardar");
			recuperar = new JButton("Revertir canvis");
			logout = new JButton("Tancar sessió");
			
			//me_down.setLayout(new GridLayout(3, 1));//, 0, 10));
			
			menu_esquerra.add(guardar);
			menu_esquerra.add(recuperar);
			menu_esquerra.add(logout);
		
		//menu_esquerra.setLayout(new GridLayout(2, 1));	//o 9v7, 1
		//menu_esquerra.add(me_up);
		//menu_esquerra.add(me_down);
		
		//SEPARADOR VERTICAL
		
			//LAYER DRETA
			dreta = new JPanel();
			dreta.setLayout(new GridLayout(2, 1, 0, 10));
			
			
			recomana = new JButton("OBTENIR RECOMANACIONS");
			//recomana.setBackground(Color.RED);
			//recomana.setForeground(Color.BLACK);
			recs = new JPanel();
			recsScrollable = new JScrollPane(recs);
			recs.setAutoscrolls(true);
			
			if (ControladorPresentacio.isAdmin()) {
				dreta.add(modificar_algorisme);
				dreta.add(test_algorisme);
			}
			else
			{
				dreta.add(recomana);
				dreta.add(recsScrollable);
			}
		
		//LAYER GLOBAL
		panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(1, 2));
        
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		
		layout.setHorizontalGroup(
		   layout.createSequentialGroup()
			  .addComponent(menu_esquerra)
			  //.addComponent(sep)
			  .addComponent(dreta)
		);
		layout.setVerticalGroup(
		   layout.createSequentialGroup()
			  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				   .addComponent(menu_esquerra)
				   //.addComponent(sep)
				   .addComponent(dreta))
		);
        
        //panel.add(menu_esquerra);
        //panel.add(new JSeparator(SwingConstants.VERTICAL));
		//panel.add(dreta);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema Recomanador");
        pack();
        setVisible(true);
        
        recomana.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				
				recomanacions = ControladorPresentacio.executarAlgorisme();
				
				int nb = recomanacions.size();
				
				dreta.remove(recsScrollable);
				
				id_item = new ArrayList<JLabel>();
				nom_item = new ArrayList<JLabel>();
				rate = new ArrayList<JComboBox>();
							
				recs = new JPanel();
				recs.setLayout(new GridLayout(nb + 1, 3));
				
				header = new ArrayList<JLabel>();
				header.add(new JLabel("id"));
				header.add(new JLabel("nom"));
				header.add(new JLabel("valoració"));
				
				recs.add(header.get(0));
				recs.add(header.get(1));
				recs.add(header.get(2));
					
				for (int i = 0; i < nb; ++i)
				{
					id_item.add(new JLabel(recomanacions.get(i).get(0)));
					nom_item.add(new JLabel(recomanacions.get(i).get(1)));
					rate.add(new JComboBox(options));
					
					recs.add(id_item.get(id_item.size()-1));
					recs.add(nom_item.get(nom_item.size()-1));
					recs.add(rate.get(rate.size()-1));
				}
				
				recsScrollable = new JScrollPane(recs);
				recs.setAutoscrolls(true);
				dreta.add(recsScrollable);
				
				validate();
				repaint();
				
				for (int k = 0; k < id_item.size(); ++k)
				{
					String stringedId = id_item.get(k).getText();
					id_item.get(k).addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							try {
								new VistaInformacioItem(vp, ControladorPresentacio.getItem(Integer.parseInt(stringedId)));
								setVisible(false);
							}
							catch(ItemNotFoundException exce) {
								new VistaError(exce.getMessage());
							}
						}
					});

					final int nonChange = k;
					id_item.get(k).addMouseMotionListener(new MouseAdapter() {
						@Override
						public void mouseMoved(MouseEvent e) {
							// only display a hand if the cursor is over the label
							final Rectangle cellBounds = id_item.get(nonChange).getBounds();
							if (cellBounds != null) {
								id_item.get(nonChange).setCursor(new Cursor(Cursor.HAND_CURSOR));
							}

							id_item.get(nonChange).getParent().repaint();
						}

						@Override
						public void mouseDragged(MouseEvent e) {							
						}
					});
					
					nom_item.get(k).addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							try {
								new VistaInformacioItem(vp, ControladorPresentacio.getItem(Integer.parseInt(stringedId)));
								setVisible(false);
							}
							catch(ItemNotFoundException exce) {
								new VistaError(exce.getMessage());
							}
						}
					});

					nom_item.get(k).addMouseMotionListener(new MouseAdapter() {
						@Override
						public void mouseMoved(MouseEvent e) {
							// only display a hand if the cursor is over the label
							final Rectangle cellBounds = nom_item.get(nonChange).getBounds();
							if (cellBounds != null) {
								nom_item.get(nonChange).setCursor(new Cursor(Cursor.HAND_CURSOR));
							}

							nom_item.get(nonChange).getParent().repaint();
						}

						@Override
						public void mouseDragged(MouseEvent e) {							
						}
					});

					rate.get(k).addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent  e) {
							cb = (JComboBox<String>)e.getSource();
							String rate_value = cb.getSelectedItem().toString();
							if (rate_value.equals("Sense valoració")) 
								ControladorPresentacio.eliminarValoracio(ControladorPresentacio.getId(), stringedId);
							else ControladorPresentacio.valorar(stringedId, ControladorPresentacio.getId(), rate_value);
						}
					});
				}
				System.out.println("x" + recomanacions.size());
            }
        });
        
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if (VistaAdvertencia.Advertencia("Segur que vols tancar sessió? Es perdran tots els canvis no guardats.")) {
					ControladorPresentacio.logOut();
					ControladorPresentacio.obreVistaInicial();
					dispose();
				}
			}
        });
        
        info_usuari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new VistaUsuari(vp, ControladorPresentacio.getId());
			}
        });
        
        info_total_usuaris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new VistaTotalUsuari(vp);
			}
        });
        
        info_items.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new VistaItems(vp);
			}
        });
        
        modificar_algorisme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new VistaModificarAlgorisme(vp);
			}
        });
        
        test_algorisme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new VistaTestAlgorisme(vp);
			}
        });
        
        guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControladorPresentacio.guardar();
			}
        });
        
        recuperar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (VistaAdvertencia.Advertencia("Segur que vols revertir els canvis? Es perdràn tots els canvis no guardats.")) {
					try {
						String id_activa = null;
						boolean admin = false;
						String nomProj = ControladorPresentacio.getNomProjecte();
						if (!ControladorPresentacio.isAdmin()) id_activa = ControladorPresentacio.getId();
						else admin = true;
						ControladorPresentacio.logOut();
						ControladorPresentacio.carregarProjecte(nomProj);
						if (admin) ControladorPresentacio.setAdmin();
						else ControladorPresentacio.setUser(id_activa);
						
						dreta.remove(recsScrollable);
						validate();
						repaint();
						
					} catch(Exception err) {
						new VistaError(err.getMessage());
					}
				}
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
	
	public void mostraDeUsuari() {
		dreta.remove(recsScrollable);
		
		ArrayList<ArrayList<String>> all_recs =
			new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> all_vals =
			new ArrayList<ArrayList<String>>();
		
		try
		{
			all_recs = ControladorPresentacio.getRecomanacions(ControladorPresentacio.getId());
			all_vals = ControladorPresentacio.getValoracions(ControladorPresentacio.getId());
		}
		catch (Exception e)
		{
			new VistaError(e.getMessage());
		}
		
		int eliminats = 0;
		
		for (int i = 0; i < recomanacions.size(); ++i)
		{
			boolean found_rec = false;
			boolean found_val = false;
			for (int j = 0; j < all_recs.size(); ++j) {
				if (all_recs.get(j).get(0).equals(ControladorPresentacio.getId()) &&
					all_recs.get(j).get(1).equals(recomanacions.get(i).get(0))) 
					found_rec = true;
			}
			
			if (!found_rec) {
				for (int j = 0; j < all_vals.size(); ++j) {
					if (all_vals.get(j).get(0).equals(ControladorPresentacio.getId()) &&
						all_vals.get(j).get(1).equals(recomanacions.get(i).get(0)))
						{
							found_val = true;
						}
				}
				
			}
			
			if (!found_rec && !found_val) ++eliminats;
		}
			
		
		id_item = new ArrayList<JLabel>();
		nom_item = new ArrayList<JLabel>();
		rate = new ArrayList<JComboBox>();
		
		System.out.println(recomanacions.size());
					
		recs = new JPanel();
		recs.setLayout(new GridLayout(recomanacions.size() - eliminats + 1, 3));
		
		header = new ArrayList<JLabel>();
		header.add(new JLabel("id"));
		header.add(new JLabel("nom"));
		header.add(new JLabel("valoració"));
		
		recs.add(header.get(0));
		recs.add(header.get(1));
		recs.add(header.get(2));
		
		for (int i = 0; i < recomanacions.size(); ++i)
		{
			boolean found_rec = false;
			boolean found_val = false;
			String rated_val = "";
			
			for (int j = 0; j < all_recs.size(); ++j) {
				if (all_recs.get(j).get(0).equals(ControladorPresentacio.getId()) &&
					all_recs.get(j).get(1).equals(recomanacions.get(i).get(0))) 
					found_rec = true;
			}
			
			if (!found_rec) {
				for (int j = 0; j < all_vals.size(); ++j) {
					if (all_vals.get(j).get(0).equals(ControladorPresentacio.getId()) &&
						all_vals.get(j).get(1).equals(recomanacions.get(i).get(0)))
					{
						found_val = true;
						rated_val = all_vals.get(j).get(2);
					}
				}
				
			}
			
			if (found_rec) {
				id_item.add(new JLabel(recomanacions.get(i).get(0)));
				nom_item.add(new JLabel(recomanacions.get(i).get(1)));
				rate.add(new JComboBox(options));
				
				recs.add(id_item.get(id_item.size() - 1));
				recs.add(nom_item.get(nom_item.size() - 1));
				recs.add(rate.get(rate.size() - 1));
			}
			else if (found_val) {
				id_item.add(new JLabel(recomanacions.get(i).get(0)));
				nom_item.add(new JLabel(recomanacions.get(i).get(1)));
				rate.add(new JComboBox(options));
				rate.get(rate.size()-1).setSelectedIndex(stringToIndex(rated_val));
				
				recs.add(id_item.get(id_item.size() - 1));
				recs.add(nom_item.get(nom_item.size() - 1));
				recs.add(rate.get(rate.size() - 1));
			}
		}
		recsScrollable = new JScrollPane(recs);
		recs.setAutoscrolls(true);
		dreta.add(recsScrollable);
		
		
		
		for (int k = 0; k < id_item.size(); ++k)
		{
			String stringedId = id_item.get(k).getText();
			id_item.get(k).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						new VistaInformacioItem(vp, ControladorPresentacio.getItem(Integer.parseInt(stringedId)));
						setVisible(false);
					}
					catch(ItemNotFoundException exce) {
						new VistaError(exce.getMessage());
					}
				}
			});

			final int noChange = k;
			id_item.get(k).addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// only display a hand if the cursor is over the label
					final Rectangle cellBounds = id_item.get(noChange).getBounds();
					if (cellBounds != null) {
						id_item.get(noChange).setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					id_item.get(noChange).getParent().repaint();
				}

				@Override
				public void mouseDragged(MouseEvent e) {							
				}
			});
		
			nom_item.get(k).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						new VistaInformacioItem(vp, ControladorPresentacio.getItem(Integer.parseInt(stringedId)));
						setVisible(false);
					}
					catch(ItemNotFoundException exce) {
						new VistaError(exce.getMessage());
					}
				}
			});

			nom_item.get(k).addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					// only display a hand if the cursor is over the label
					final Rectangle cellBounds = nom_item.get(noChange).getBounds();
					if (cellBounds != null) {
						nom_item.get(noChange).setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					nom_item.get(noChange).getParent().repaint();
				}

				@Override
				public void mouseDragged(MouseEvent e) {							
				}
			});

			rate.get(k).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent  e) {
					cb = (JComboBox<String>)e.getSource();
					String rate_value = cb.getSelectedItem().toString();
					if (rate_value.equals("Sense valoració")) 
						ControladorPresentacio.eliminarValoracio(ControladorPresentacio.getId(), stringedId);
					else ControladorPresentacio.valorar(stringedId, ControladorPresentacio.getId(), rate_value);
				}
			});
		}
				
		setVisible(true);
		validate();
		repaint();
	}
}
