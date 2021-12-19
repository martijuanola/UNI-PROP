package src.recomanador.presentacio;

import java.util.ArrayList;

import src.recomanador.domini.ControladorDomini;
import src.recomanador.excepcions.AlreadyLogedInException;
import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.FileNotFoundException;
import src.recomanador.excepcions.FileNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;

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
	
	public static ArrayList<ArrayList<String>> executarAlgorisme()
	{
		//ArrayList<ArrayList<String>> old = 
		//	domini.getAllRecomanacionsUsuari(domini.getActiveUserId());
		
		//domini.createRecomanacions();
		//ArrayList<ArrayList<String>> young = 
		//	domini.getAllRecomanacionsUsuari(domini.getActiveUserId());
		
		//La diferència de conjunts serà els que es mostrarà
		return new ArrayList<ArrayList<String>>();
	}
	
	public static String getId() {
		//return domini.getId()
		return "32";
	}
	
	public static String getNomProjecte() {
		//return domini.getId()
		return "Un gran projecte";
	}
	
	public static ArrayList<String> getIdRecomanacions() {
		ArrayList<String> n = new ArrayList<String>();
		n.add("1234");
		n.add("1232354");
		n.add("42");
		n.add("3141592");
		n.add("3141592");
		return n;
	}
	
	public static ArrayList<String> getNomRecomanacions() {
		ArrayList<String> n = new ArrayList<String>();
		n.add("abc");
		n.add("abcbced");
		n.add("clau de l'univers");
		n.add("clau de l'univers");
		n.add("pi");
		return n;
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

    public static void carregarProjecte(String proj) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {
        domini.loadSession(proj);
    }

    public static void logInUser(int id) throws AlreadyLogedInException {
        domini.login(id);
    }

    public static void logInAdmin() throws AlreadyLogedInException {
        domini.loginAdmin();
    }

    public static void carregarProjecteNou(String nom, String itemsFile, String ratingsFile) throws FolderNotValidException, FileNotValidException, FileNotFoundException {
        domini.createSession(nom, itemsFile, ratingsFile);
    }

    public static ArrayList<String> getProjectesDisponibles() {
        //TODO:OMPLIR
        return new ArrayList<String>();
    }

    public static void obreVistaSessioNova(String text) {
        //TODO:OMPLIR
        new VistaError("OBRE SESSIO NOVA");
    }

    public static void obreVistaEscollirAtributs() {
        //TODO:OMPLIR
        new VistaError("OBRE SESSIO NOVA");
    }

    public static void main(String[] args) {
        //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        instancia = new ControladorPresentacio();
        //obreVistaInicial();
        obreVistaPrincipal();
    }

    /* -----------------------------------------------------------------
     * ---Funció treta d'internet, per a canviar la font i el tamany ---
     * -----------------------------------------------------------------
     */
    ///*
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
    //*/
}
