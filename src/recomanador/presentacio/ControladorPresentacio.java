package src.recomanador.presentacio;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import src.recomanador.domini.ControladorDomini;

public class ControladorPresentacio {
    private static ControladorDomini domini;
    private static ControladorPresentacio instancia = null;

    private ControladorPresentacio() {
        domini = ControladorDomini.getInstance();
    }

    public static ControladorPresentacio getInstance() {
        if (instancia == null) {
            instancia = new ControladorPresentacio();
        }
        return instancia;
    }
	
	public static String getId() {
		//return domini.getId()
		return "32";
	}
	
	public static String getNomProjecte() {
		//return domini.getId()
		return "Un gran projecte";
	}
	
	public static boolean isAdmin() {
		//return domini.isAdmin()
		return true;
	}
	
    public static void obreVistaPrincipal() {
        new VistaPrincipal();
    }

    public static void obreVistaInicial() {
        new VistaInicial();
    }

    public static void main(String[] args) {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        instancia = new ControladorPresentacio();
        //obreVistaInicial();
        obreVistaPrincipal();
    }
    
    public static void canviarFontUI (javax.swing.plaf.FontUIResource f){
		//Informació extreta de:
		//https://stackoverflow.com/questions/7434845/setting-the-default-font-of-swing-program
		
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if (value instanceof javax.swing.plaf.FontUIResource)
			UIManager.put(key, f);
		}
    } 
}
