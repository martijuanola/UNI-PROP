package src.recomanador.presentacio;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		JPanel recs;			//Amb scrollbar
			JLabel[] header;	//Per id, nom, valoracio
			JLabel[] id_item;
			JLabel[] nom_item;
			JComboBox[] rate;	//Tindra no valorat i [0.5, 5] de 1/2 en 1/2
    
    public VistaPrincipal() {
        ControladorPresentacio cp = ControladorPresentacio.getInstance();
        
        setMinimumSize(new Dimension(550, 250));
        setSize(new Dimension(1000, 900));

        panel = new JPanel();
		
		cp.canviarFontUI (new javax.swing.plaf.FontUIResource("Calibri",Font.PLAIN,20));
		//this.setFont(this.getFont().deriveFont(Float(20.0f)));
        
        //LAYER E
        menu_esquerra = new JPanel();
        
			//LAYER E UP
			me_up = new JPanel();
			
			String lab_usr = null;
			if (cp.isAdmin()) lab_usr = "Admin";
			else lab_usr = cp.getId();
			
			nom_projecte = new JLabel("Projecte sobre el que es treballa: " + cp.getNomProjecte());
			usuari_sessio_actual = new JLabel("Usuari de la sessió actual: " + lab_usr);
			info_usuari = new JButton("Informació meva");
			info_total_usuaris = new JButton("Informació dels usuaris");
			info_items = new JButton("Informació dels ítems");
			modificar_algorisme = new JButton("Modificar algorisme");
			test_algorisme = new JButton("Testejar algorisme");
			
			//if (cp.isAdmin()) menu_esquerra.setLayout(new GridLayout(6, 1));//, 0, 10));
			//else menu_esquerra.setLayout(new GridLayout(4, 1));//, 0, 10));
			if (cp.isAdmin()) menu_esquerra.setLayout(new GridLayout(9, 1, 0, 10));
			else menu_esquerra.setLayout(new GridLayout(7, 1, 0, 10));
			
			menu_esquerra.add(nom_projecte);
			menu_esquerra.add(usuari_sessio_actual);
			if (cp.isAdmin()) menu_esquerra.add(info_total_usuaris);
			else menu_esquerra.add(info_usuari);
			menu_esquerra.add(info_items);
			if (cp.isAdmin()) {
				menu_esquerra.add(modificar_algorisme);
				menu_esquerra.add(test_algorisme);
			}
			
			//SEPARACIO
			//menu_esquerra.add(new Box.createVerticalStrut(30));
			
			//LAYER E DOWN
			//me_down = new JPanel();
			
			guardar = new JButton("Guardar");
			recuperar = new JButton("Recuperar dades");
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
			
			recomana = new JButton("RECOMANA");
			//recomana.setBackground(Color.RED);
			//recomana.setForeground(Color.BLACK);
			dreta.add(recomana);
			
			recs = new JPanel();
			recs.setLayout(new GridLayout(1, 3));	//1, 3
		
		//LAYER GLOBAL
		panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panel.setLayout(new GridLayout(1, 3));	//1, 3
        panel.add(menu_esquerra);
        //Afegir espai en blanc per separar separador
        panel.add(new JSeparator(SwingConstants.VERTICAL));
		//Afegir espai en blanc per separar separador
		panel.add(dreta);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema Recomanador");
        pack();
        setVisible(true);
	}
}
