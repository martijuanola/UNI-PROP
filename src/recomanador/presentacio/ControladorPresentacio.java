package src.recomanador.presentacio;

import java.util.ArrayList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		try
		{
			ArrayList<ArrayList<String>> old = 
				domini.getAllRecomanacionsUsuari(domini.getActiveUserId());
			
			domini.createRecomanacions();
			ArrayList<ArrayList<String>> young = 
				domini.getAllRecomanacionsUsuari(domini.getActiveUserId());
			
			ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
			
			for (int i = 0; i < young.size(); ++i)
			{
				ArrayList<String> one = young.get(i);
				boolean found = false;
				
				for (int j = 0; j < old.size(); ++j)
					if (one.get(1) == old.get(j).get(1)) found = true;
				
				if (!found)
				{
					temp.add(new ArrayList<String>());
					temp.get(temp.size()-1).add(one.get(1));
				}
			}
			
			for (int i = 0; i < temp.size(); ++i)
			{
				ArrayList<ArrayList<String>> item =
					domini.getItem(Integer.parseInt(temp.get(i).get(0)));
				String nom = item.get(Integer.parseInt(domini.getPosNomItem())).get(0);
				temp.get(i).add(nom);
			}
			
			//La diferència de conjunts serà els que es mostrarà
			return temp;
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return null;
		}
	}
	
	public static String getId() {
		try {		
			return domini.getActiveUserId();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "ERROR";
		}
	}
	
	public static String getNomProjecte() {
		try {		
			return domini.getNomProjecte();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "ERROR";
		}
	}
		
	public static boolean isAdmin() {
		/*
		try {		
			return domini.isAdmin();
		} catch(Exception e) {
			new VistaError(e.getMessage());
			return "ERROR";
		}
		//*/
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
