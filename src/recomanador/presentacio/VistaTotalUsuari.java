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

public class VistaTotalUsuari extends JFrame {
    JPanel panel;
	
	JButton tornar_enrere;
	JLabel text_inicial;
	JLabel text_recs_vals;

    /*----- DADES -----*/
    ArrayList<String> id_usuaris;
    
    /*----- FUNCIONS -----*/
    public VistaTotalUsuari(VistaPrincipal vp) {
		setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));
				
		inicialitza_vista();
		
		tornar_enrere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vp.mostra();
				dispose();
			}
        });
	}
	
	public void mostra() {
		setVisible(true);
	}
	
	private void inicialitza_vista() {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
		VistaTotalUsuari vtu = this;
		
        panel = new JPanel();
        
		id_usuaris = new ArrayList<String>();
		
		try
		{
			id_usuaris = ControladorPresentacio.getAllUsers();
		}
		catch (Exception e)
		{
			new VistaError(e.getMessage());
		}
		
		int nb = id_usuaris.size();
        
		tornar_enrere = new JButton("Tornar enrere");
		text_inicial = new JLabel("Informació de tots els usuaris. Fes click sobre d'un per veure'l i modificar-lo.");
		text_recs_vals = new JLabel("Identificadors d'usuari:");
		
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(3 + nb, 1));
        
		panel.add(tornar_enrere);
		panel.add(text_inicial);
		panel.add(text_recs_vals);
				
		for (int i = 0; i < nb; ++i)
		{
			String usrId = id_usuaris.get(i);
			
			JLabel idLab = new JLabel(usrId);
			JButton eliminar = new JButton("Eliminar");
			
			JPanel usuari_ensenyat = new JPanel();
			usuari_ensenyat.setLayout(new GridLayout(1, 2));
			
			usuari_ensenyat.add(idLab);
			usuari_ensenyat.add(eliminar);
			
			panel.add(usuari_ensenyat);
			
			idLab.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					setVisible(false);
					new VistaUsuari(vtu, usrId);
				}
			});
						
			eliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try
					{
						ControladorPresentacio.eliminarUsuari(usrId);
						panel.remove(usuari_ensenyat);
						validate();
						repaint();
					} catch(Exception err) {
						new VistaError(err.getMessage());
					}
				}
			});
			
		}
		
		JScrollPane scrollFrame = new JScrollPane(panel);
        panel.setAutoscrolls(true);
        add(scrollFrame);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informació Usuari");
        pack();
        setVisible(true);
	}
}
